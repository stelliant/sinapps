package eu.stelliant.neurone.sinapps.module.integration.transformer;

import com.darva.sinapps.api.client.expertise.model.LinksPartenaireInner;
import com.darva.sinapps.api.client.expertise.model.LinksPartenaireInner.RelEnum;
import com.darva.sinapps.api.client.expertise.model.ListeAbstractMissions;
import com.darva.sinapps.api.client.expertise.model.RessourcePartenaire;
import eu.stelliant.neurone.sinapps.module.api.config.ApiProperties;
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
  public Message<?> get(Message<RessourcePartenaire> msg) {

    String missionsPath = msg.getPayload().getLinks().stream()
        .filter(link -> RelEnum.LINKED_EVENTS == link.getRel())
        .map(LinksPartenaireInner::getHref)
        .findFirst()
        .orElse("");

    return MessageBuilder.withPayload("")
        .copyHeaders(msg.getHeaders())
        .setHeader("url", properties.getApi().getHost() + missionsPath)
        .setHeader("expected-response-type", ListeAbstractMissions.class)
        .build();
  }
}
