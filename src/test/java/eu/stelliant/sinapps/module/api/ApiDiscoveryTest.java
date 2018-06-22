package eu.stelliant.sinapps.module.api;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

import com.darva.sinapps.api.client.expertise.model.InlineResponse2001;
import com.darva.sinapps.api.client.expertise.model.RessourceAbstractMissions;
import com.darva.sinapps.api.client.expertise.model.RessourceMission;
import com.darva.sinapps.api.client.expertise.model.RessourceUser;
import com.darva.sinapps.api.client.expertise.model.RessourceUserLinks;
import com.darva.sinapps.api.client.transverse.model.Body;
import com.darva.sinapps.api.client.transverse.model.LinksPartenaireInner;
import com.darva.sinapps.api.client.transverse.model.RessourcePartenaire;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

public class ApiDiscoveryTest extends TestApiSetup {

  private static final Logger log = LoggerFactory.getLogger(ApiDiscoveryTest.class);

  @Autowired
  RestTemplate restTemplate;

  @Test
  public void should_not_fail() {

    // Given

    // When
    Throwable exception = catchThrowable(() -> {
      // Set the Content-Type header
      HttpHeaders requestHeaders = new HttpHeaders();
      requestHeaders.setContentType(new MediaType("application", "json"));

      Body body = new Body();
      body.setLogin(apiProperties.getApi().getLogin().getUsername());
      body.setPassword(apiProperties.getApi().getLogin().getPassword());
      HttpEntity<Body> bodyEntity = new HttpEntity<>(body, requestHeaders);

      ResponseEntity<RessourceUser> ressourceUser = restTemplate
          .exchange(
              apiProperties.getApi().getHost() + apiProperties.getApi().getLogin().getMapping(),
              HttpMethod.POST,
              bodyEntity,
              RessourceUser.class);
      String PLAY_SESSION = ressourceUser.getHeaders().getFirst(HttpHeaders.SET_COOKIE);

      // Set the Content-Type header
      requestHeaders.set(HttpHeaders.COOKIE, PLAY_SESSION);
      HttpEntity<String> requestEntity = new HttpEntity<>(requestHeaders);

      // Make the HTTP POST request, marshaling the request to JSON, and the response to a String
      String partenairePath = ressourceUser.getBody().getLinks().stream()
          .filter(link -> RessourceUserLinks.RelEnum.PARTENAIRE == link.getRel())
          .map(RessourceUserLinks::getHref)
          .findFirst()
          .orElse("");
      ResponseEntity<RessourcePartenaire> ressourcePartenaire = restTemplate
          .exchange(apiProperties.getApi().getHost() + partenairePath,
                    HttpMethod.GET,
                    requestEntity,
                    RessourcePartenaire.class);

      // Make the HTTP POST request, marshaling the request to JSON, and the response to a String
      String missionsPath = ressourcePartenaire.getBody().getLinks().stream()
          .filter(link -> LinksPartenaireInner.RelEnum.ABSTRACTMISSIONS == link.getRel())
          .map(LinksPartenaireInner::getHref)
          .findFirst()
          .orElse("");
      ResponseEntity<InlineResponse2001> listeRessources = restTemplate
          .exchange(apiProperties.getApi().getHost() + missionsPath,
                    HttpMethod.GET,
                    requestEntity,
                    InlineResponse2001.class);

      for (RessourceAbstractMissions items :
          listeRessources.getBody().getItems()) {
        ResponseEntity<RessourceMission> responseEntity = restTemplate
            .exchange(apiProperties.getApi().getHost() + items.getHref(),
                      HttpMethod.GET,
                      requestEntity,
                      RessourceMission.class);
      }
    });

    // Then
    assertThat(exception).isNull();
  }
}
