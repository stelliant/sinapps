package eu.stelliant.sinapps.module.integration.config;

import eu.stelliant.sinapps.module.api.config.ApiProperties;
import io.swagger.sinapps.api.client.transverse.model.Body;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.integration.annotation.Transformer;
import org.springframework.integration.support.MessageBuilder;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Component;

@Component
public class ConnexionTransformer {

  @Autowired
  ApiProperties properties;

  @Transformer
  public Message<?> createRequestMessage(Message<String> msg) {

    Body body = new Body();
    body.setLogin(properties.getApi().getLogin());
    body.setPassword(properties.getApi().getPassword());

    return MessageBuilder.withPayload(body)
        .setHeader("Content-Type", "application/json")
        .build();
  }
}
