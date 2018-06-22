package eu.stelliant.sinapps.module.integration.transformer;

import com.darva.sinapps.api.client.expertise.model.Body;
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

    Body body = new Body();
    body.setLogin(properties.getApi().getLogin().getUsername());
    body.setPassword(properties.getApi().getLogin().getPassword());

    return MessageBuilder.withPayload(body)
        .setHeader(HttpHeaders.CONTENT_TYPE, "application/json")
        .setHeader("url", properties.getApi().getHost() + properties.getApi().getLogin().getMapping())
        .setHeader("expected-response-type", com.darva.sinapps.api.client.expertise.model.RessourceUser.class)
        .build();
  }
}
