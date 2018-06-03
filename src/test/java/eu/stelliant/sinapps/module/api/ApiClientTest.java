package eu.stelliant.sinapps.module.api;

import io.swagger.sinapps.api.client.ApiClient;
import io.swagger.sinapps.api.client.transverse.api.ConnexionDconnexionApi;
import io.swagger.sinapps.api.client.transverse.model.Body;
import io.swagger.sinapps.api.client.transverse.model.InlineResponse200;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

public class ApiClientTest extends TestApiSetup {

  private static final Logger log = LoggerFactory.getLogger(ApiClientTest.class);

  @Autowired
  private ApiClient api;

  @Autowired
  private ConnexionDconnexionApi authentificationApi;

  @Test
  public void login() {

    try {

      api.setBasePath(apiProperties.getApi().getBaseUrl() + "/core");

      Body body = new Body();
      body.setLogin(apiProperties.getApi().getLogin());
      body.setPassword(apiProperties.getApi().getPassword());

      InlineResponse200 inlineResponse200 = authentificationApi.apiLoginPost(body);
      log.info("Retour API sinapps {}", inlineResponse200);

      // TODO WARN - pas d'accès à HttpHeaders pour récupérer le cookie PLAY_SESSION

    } catch (Exception e) {
      log.error("####", e);
    }
  }
}
