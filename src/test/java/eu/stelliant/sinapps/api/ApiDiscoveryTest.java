package eu.stelliant.sinapps.api;

import eu.stelliant.sinapps.api.transverse.Body;
import eu.stelliant.sinapps.api.transverse.InlineResponse200;
import eu.stelliant.sinapps.api.transverse.InlineResponse2001;
import eu.stelliant.sinapps.api.transverse.LinksInner;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

public class ApiDiscoveryTest extends TestApiSetup {

  private static final Logger log = LoggerFactory.getLogger(ApiDiscoveryTest.class);

  @Test
  public void login() {

    RestTemplate restTemplate;
    HttpHeaders requestHeaders;

    try {

      // Create a new RestTemplate instance
      restTemplate = new RestTemplate();
      // Add the Jackson and String message converters
      restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
      restTemplate.getMessageConverters().add(new StringHttpMessageConverter());

      // Set the Content-Type header
      requestHeaders = new HttpHeaders();
      requestHeaders.setContentType(new MediaType("application", "json"));

      Body body = new Body();
      body.setLogin(login);
      body.setPassword(password);
      HttpEntity<Body> bodyEntity = new HttpEntity<>(body, requestHeaders);

      // Make the HTTP POST request, marshaling the request to JSON, and the response to a String
      ResponseEntity<InlineResponse200> inlineResponse200 = restTemplate
          .exchange(baseUrl + loginPath,
                    HttpMethod.POST,
                    bodyEntity,
                    InlineResponse200.class);
      log.info("Retour API sinapps {}", inlineResponse200);

      String PLAY_SESSION = inlineResponse200.getHeaders().getFirst(HttpHeaders.SET_COOKIE);

      restTemplate = new RestTemplate();

      // Set the Content-Type header
      requestHeaders.set(HttpHeaders.COOKIE, PLAY_SESSION);
      HttpEntity<String> requestEntity = new HttpEntity<>(requestHeaders);

      // Make the HTTP POST request, marshaling the request to JSON, and the response to a String
      String partenairePath = inlineResponse200.getBody().getLinks().stream()
          .filter(linksInner -> "partenaire".equals(linksInner.getRel()))
          .map(LinksInner::getHref)
          .findFirst()
          .orElse("");
      ResponseEntity<InlineResponse2001> inlineResponse2001 = restTemplate
          .exchange(baseUrl + partenairePath,
                    HttpMethod.GET,
                    requestEntity,
                    InlineResponse2001.class);
      log.info("Retour API sinapps {}", inlineResponse2001);

    } catch (Exception e) {
      log.error("####", e);
    }
  }
}
