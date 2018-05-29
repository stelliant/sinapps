package eu.stelliant.sinapps.api;

import io.swagger.sinapps.client.ApiClient;
import eu.stelliant.sinapps.client.transverse.api.ConnexionDconnexionApi;
import eu.stelliant.sinapps.client.transverse.model.Body;
import eu.stelliant.sinapps.client.transverse.model.InlineResponse200;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

public class ApiClientTest extends TestApiSetup {

  private static final Logger log = LoggerFactory.getLogger(ApiClientTest.class);

  @Autowired
  private ApiClient api;

  @Autowired
  private ConnexionDconnexionApi authentificationApi;

  @Test
  public void login() {

    try {

      api.setBasePath(baseUrl+"/core");

      Body body = new Body();
      body.setLogin(login);
      body.setPassword(password);

      InlineResponse200 inlineResponse200 = authentificationApi.apiLoginPost(body);
      log.info("Retour API sinapps {}", inlineResponse200);
/*

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
*/

    } catch (Exception e) {
      log.error("####", e);
    }
  }
}
