package eu.stelliant.sinapps.module.integration.config;

import eu.stelliant.sinapps.module.api.config.ApiProperties;
import io.swagger.sinapps.api.client.transverse.model.InlineResponse2001;
import io.swagger.sinapps.api.client.transverse.model.LinksInner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.integration.annotation.Transformer;
import org.springframework.integration.support.MessageBuilder;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Component;

@Component
public class MissionsPayload {

  @Autowired
  ApiProperties properties;

  @Transformer
  public Message<?> get(Message<InlineResponse2001> msg) {

    String missionsPath = msg.getPayload().getLinks().stream()
        .filter(linksInner -> "abstractMissions".equals(linksInner.getRel()))
        .map(LinksInner::getHref)
        .findFirst()
        .orElse("");

    return MessageBuilder.withPayload("")
        .copyHeaders(msg.getHeaders())
        .setHeader("url", properties.getApi().getBaseUrl() + missionsPath)
        .build();
  }
}
