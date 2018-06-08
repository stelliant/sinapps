package eu.stelliant.sinapps.module.integration.transformer;

import com.darva.sinapps.api.client.transverse.model.InlineResponse2002Items;
import eu.stelliant.sinapps.module.api.config.ApiProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.integration.annotation.Transformer;
import org.springframework.integration.support.MessageBuilder;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Component;

@Component
public class MissionPayload {

  @Autowired
  ApiProperties properties;

  @Transformer
  public Message<?> get(Message<InlineResponse2002Items> msg) {

    String missionPath = msg.getPayload().getHref();

    return MessageBuilder.withPayload("")
        .copyHeaders(msg.getHeaders())
        .setHeader("url", properties.getApi().getBaseUrl() + missionPath)
        .setHeader("response", "com.darva.sinapps.api.client.expertise.model.InlineResponse2001")
        .build();
  }
}
