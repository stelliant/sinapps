package eu.stelliant.sinapps.module.integration.transformer;

import com.darva.sinapps.api.client.expertise.model.RessourceAbstractMissions;
import eu.stelliant.sinapps.module.api.config.ApiProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.integration.annotation.Transformer;
import org.springframework.integration.file.FileHeaders;
import org.springframework.integration.support.MessageBuilder;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Component;

@Component
public class MissionPayload {

  private final ApiProperties properties;

  @Autowired
  public MissionPayload(ApiProperties properties) {
    this.properties = properties;
  }

  @Transformer
  public Message<?> get(Message<RessourceAbstractMissions> msg) {

    String missionPath = msg.getPayload().getHref();

    return MessageBuilder.withPayload("")
        .copyHeaders(msg.getHeaders())
        .setHeader("url", properties.getApi().getHost() + missionPath)
        .setHeader("expected-response-type", com.darva.sinapps.api.client.expertise.model.RessourceMission.class)
        .setHeader(FileHeaders.FILENAME, msg.getPayload().getProperties().getId())
        .build();
  }
}
