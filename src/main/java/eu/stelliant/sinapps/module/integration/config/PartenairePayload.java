package eu.stelliant.sinapps.module.integration.config;

import eu.stelliant.sinapps.module.api.config.ApiProperties;
import io.swagger.sinapps.api.client.transverse.model.InlineResponse200;
import io.swagger.sinapps.api.client.transverse.model.LinksInner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.integration.annotation.Transformer;
import org.springframework.integration.support.MessageBuilder;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Component;

@Component
public class PartenairePayload {

  @Autowired
  ApiProperties properties;

  @Transformer
  public Message<?> get(Message<InlineResponse200> msg) {

    String partenairePath = msg.getPayload().getLinks().stream()
        .filter(linksInner -> "partenaire".equals(linksInner.getRel()))
        .map(LinksInner::getHref)
        .findFirst()
        .orElse("");

    return MessageBuilder.withPayload("")
        .copyHeaders(msg.getHeaders())
        .setHeader("url", properties.getApi().getBaseUrl() + partenairePath)
        .build();
  }
}
