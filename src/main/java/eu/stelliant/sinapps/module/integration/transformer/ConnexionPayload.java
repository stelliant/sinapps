package eu.stelliant.sinapps.module.integration.transformer;

import eu.stelliant.sinapps.module.api.config.ApiProperties;
import com.darva.sinapps.api.client.transverse.model.Body;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.integration.annotation.Transformer;
import org.springframework.integration.support.MessageBuilder;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Component;

@Component
public class ConnexionPayload {

  @Autowired
  ApiProperties properties;

  @Transformer
  public Message<?> get(Message<String> msg) {

    Body body = new Body();
    body.setLogin(properties.getApi().getLogin());
    body.setPassword(properties.getApi().getPassword());

    return MessageBuilder.withPayload(body)
        .setHeader(HttpHeaders.CONTENT_TYPE, "application/json")
        .setHeader("url", properties.getApi().getBaseUrl() + properties.getApi().getLoginPath())
        .setHeader("response", "com.darva.sinapps.api.client.transverse.model.InlineResponse200")
        .build();
  }
}
