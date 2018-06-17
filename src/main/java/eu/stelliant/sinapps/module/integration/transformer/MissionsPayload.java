package eu.stelliant.sinapps.module.integration.transformer;

import eu.stelliant.sinapps.module.api.config.ApiProperties;
import com.darva.sinapps.api.client.transverse.model.InlineResponse2001;
import com.darva.sinapps.api.client.transverse.model.LinksInner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.integration.annotation.Transformer;
import org.springframework.integration.support.MessageBuilder;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Component;

@Component
public class MissionsPayload {

  private final ApiProperties properties;

  @Autowired
  public MissionsPayload(ApiProperties properties) {
    this.properties = properties;
  }

  @Transformer
  public Message<?> get(Message<InlineResponse2001> msg) {

    String missionsPath = msg.getPayload().getLinks().stream()
        .filter(linksInner -> "abstractMissions".equals(linksInner.getRel()))
        .map(LinksInner::getHref)
        .findFirst()
        .orElse("");

    return MessageBuilder.withPayload("")
        .copyHeaders(msg.getHeaders())
        .setHeader("url", properties.getApi().getHost() + missionsPath)
        .setHeader("expected-response-type", com.darva.sinapps.api.client.transverse.model.InlineResponse2002.class)
        .build();
  }
}
