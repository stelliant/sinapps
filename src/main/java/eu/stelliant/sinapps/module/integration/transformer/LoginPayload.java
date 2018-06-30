package eu.stelliant.sinapps.module.integration.transformer;

import com.darva.sinapps.api.client.expertise.model.RequeteConnexion;
import com.darva.sinapps.api.client.expertise.model.RessourceUser;
import eu.stelliant.sinapps.module.api.config.ApiProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.integration.annotation.Transformer;
import org.springframework.integration.support.MessageBuilder;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Component;

@Component
public class LoginPayload {

  private final ApiProperties properties;

  @Autowired
  public LoginPayload(ApiProperties properties) {
    this.properties = properties;
  }

  @Transformer
  public Message<?> get(Message<String> msg) {

    RequeteConnexion requeteConnexion = new RequeteConnexion();
    requeteConnexion.setLogin(properties.getApi().getLogin().getUsername());
    requeteConnexion.setPassword(properties.getApi().getLogin().getPassword());

    return MessageBuilder.withPayload(requeteConnexion)
        .setHeader(HttpHeaders.CONTENT_TYPE, "application/json")
        .setHeader("url", properties.getApi().getHost() + properties.getApi().getLogin().getMapping())
        .setHeader("expected-response-type", RessourceUser.class)
        .build();
  }
}
