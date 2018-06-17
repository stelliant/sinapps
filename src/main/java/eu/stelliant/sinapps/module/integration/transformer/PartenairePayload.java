package eu.stelliant.sinapps.module.integration.transformer;

import eu.stelliant.sinapps.module.api.config.ApiProperties;
import com.darva.sinapps.api.client.transverse.model.InlineResponse200;
import com.darva.sinapps.api.client.transverse.model.LinksInner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.integration.annotation.Transformer;
import org.springframework.integration.support.MessageBuilder;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Component;

@Component
public class PartenairePayload {

  private final ApiProperties properties;

  @Autowired
  public PartenairePayload(ApiProperties properties) {
    this.properties = properties;
  }

  @Transformer
  public Message<?> get(Message<InlineResponse200> msg) {

    String partenairePath = msg.getPayload().getLinks().stream()
        .filter(linksInner -> "partenaire".equals(linksInner.getRel()))
        .map(LinksInner::getHref)
        .findFirst()
        .orElse("");

    return MessageBuilder.withPayload("")
        .copyHeaders(msg.getHeaders())
        .setHeader("url", properties.getApi().getHost() + partenairePath)
        .setHeader("expected-response-type", com.darva.sinapps.api.client.transverse.model.InlineResponse2001.class)
        .build();
  }
}
