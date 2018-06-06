package eu.stelliant.sinapps.module.api;

import com.darva.sinapps.api.client.transverse.model.Body;
import com.darva.sinapps.api.client.transverse.model.InlineResponse200;
import com.darva.sinapps.api.client.transverse.model.InlineResponse2001;
import com.darva.sinapps.api.client.transverse.model.InlineResponse2002;
import com.darva.sinapps.api.client.transverse.model.LinksInner;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

public class ApiDiscoveryTest extends TestApiSetup {

  private static final Logger log = LoggerFactory.getLogger(ApiDiscoveryTest.class);

  @Test
  public void login() {

    RestTemplate restTemplate;
    HttpHeaders requestHeaders;

    try {

      // Set the Content-Type header
      requestHeaders = new HttpHeaders();
      requestHeaders.setContentType(new MediaType("application", "json"));

      Body body = new Body();
      body.setLogin(apiProperties.getApi().getLogin());
      body.setPassword(apiProperties.getApi().getPassword());
      HttpEntity<Body> bodyEntity = new HttpEntity<>(body, requestHeaders);

      restTemplate = getRestTemplate();
      ResponseEntity<InlineResponse200> inlineResponse200 = restTemplate
          .exchange(
              apiProperties.getApi().getBaseUrl() + apiProperties.getApi().getLoginPath(),
              HttpMethod.POST,
              bodyEntity,
              InlineResponse200.class);
      String PLAY_SESSION = inlineResponse200.getHeaders().getFirst(HttpHeaders.SET_COOKIE);

      // Set the Content-Type header
      requestHeaders.set(HttpHeaders.COOKIE, PLAY_SESSION);
      HttpEntity<String> requestEntity = new HttpEntity<>(requestHeaders);

      // Make the HTTP POST request, marshaling the request to JSON, and the response to a String
      String partenairePath = inlineResponse200.getBody().getLinks().stream()
          .filter(linksInner -> "partenaire".equals(linksInner.getRel()))
          .map(LinksInner::getHref)
          .findFirst()
          .orElse("");
      restTemplate = getRestTemplate();
      ResponseEntity<InlineResponse2001> inlineResponse2001 = restTemplate
          .exchange(apiProperties.getApi().getBaseUrl() + partenairePath,
                    HttpMethod.GET,
                    requestEntity,
                    InlineResponse2001.class);

      // Make the HTTP POST request, marshaling the request to JSON, and the response to a String
      String missionsPath = inlineResponse2001.getBody().getLinks().stream()
          .filter(linksInner -> "abstractMissions".equals(linksInner.getRel()))
          .map(LinksInner::getHref)
          .findFirst()
          .orElse("");
      restTemplate = getRestTemplate();
      ResponseEntity<InlineResponse2002> listeRessources = restTemplate
          .exchange(apiProperties.getApi().getBaseUrl() + missionsPath,
                    HttpMethod.GET,
                    requestEntity,
                    InlineResponse2002.class);

    } catch (Exception e) {
      log.error("####", e);
    }
  }
}
