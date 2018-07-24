package eu.stelliant.neurone.sinapps.module.api;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

import com.darva.sinapps.api.client.expertise.model.LinksPartenaireInner;
import com.darva.sinapps.api.client.expertise.model.LinksUserInner;
import com.darva.sinapps.api.client.expertise.model.ListeAbstractMissions;
import com.darva.sinapps.api.client.expertise.model.RequeteConnexion;
import com.darva.sinapps.api.client.expertise.model.RessourceAbstractMissions;
import com.darva.sinapps.api.client.expertise.model.RessourceMission;
import com.darva.sinapps.api.client.expertise.model.RessourcePartenaire;
import com.darva.sinapps.api.client.expertise.model.RessourceUser;
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
  RestTemplate restTemplateSinapps;

  @Test
  public void should_not_fail() {

    // Given

    // When
    Throwable exception = catchThrowable(() -> {
      // Set the Content-Type header
      HttpHeaders requestHeaders = new HttpHeaders();
      requestHeaders.setContentType(new MediaType("application", "json"));

      RequeteConnexion requeteConnexion = new RequeteConnexion();
      requeteConnexion.setLogin(apiProperties.getApi().getLogin().getUsername());
      requeteConnexion.setPassword(apiProperties.getApi().getLogin().getPassword());
      HttpEntity<RequeteConnexion> bodyEntity = new HttpEntity<>(requeteConnexion, requestHeaders);

      ResponseEntity<RessourceUser> ressourceUser = restTemplateSinapps
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
          .filter(link -> LinksUserInner.RelEnum.PARTENAIRE == link.getRel())
          .map(LinksUserInner::getHref)
          .findFirst()
          .orElse("");
      ResponseEntity<RessourcePartenaire> ressourcePartenaire = restTemplateSinapps
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
      ResponseEntity<ListeAbstractMissions> listeRessources = restTemplateSinapps
          .exchange(apiProperties.getApi().getHost() + missionsPath,
                    HttpMethod.GET,
                    requestEntity,
                    ListeAbstractMissions.class);

      for (RessourceAbstractMissions items :
          listeRessources.getBody().getItems()) {
        ResponseEntity<RessourceMission> responseEntity = restTemplateSinapps
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
