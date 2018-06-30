package eu.stelliant.sinapps.module.integration.transformer;

import com.darva.sinapps.api.client.expertise.model.LinksPartenaireInner;
import com.darva.sinapps.api.client.expertise.model.RessourceListeAbstractMissions;
import com.darva.sinapps.api.client.expertise.model.RessourcePartenaire;
import eu.stelliant.sinapps.module.api.config.ApiProperties;
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
        .filter(link -> LinksPartenaireInner.RelEnum.ABSTRACTMISSIONS == link.getRel())
        .map(LinksPartenaireInner::getHref)
        .findFirst()
        .orElse("");

    return MessageBuilder.withPayload("")
        .copyHeaders(msg.getHeaders())
        .setHeader("url", properties.getApi().getHost() + missionsPath)
        .setHeader("expected-response-type", RessourceListeAbstractMissions.class)
        .build();
  }
}
