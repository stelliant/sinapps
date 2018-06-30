package eu.stelliant.sinapps.module.integration.transformer;

import com.darva.sinapps.api.client.expertise.model.RessourcePartenaire;
import com.darva.sinapps.api.client.expertise.model.RessourceUser;
import com.darva.sinapps.api.client.expertise.model.RessourceUserLinks;
import eu.stelliant.sinapps.module.api.config.ApiProperties;
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
  public Message<?> get(Message<RessourceUser> msg) {

    String partenairePath = msg.getPayload().getLinks().stream()
        .filter(link -> RessourceUserLinks.RelEnum.PARTENAIRE == link.getRel())
        .map(RessourceUserLinks::getHref)
        .findFirst()
        .orElse("");

    return MessageBuilder.withPayload("")
        .copyHeaders(msg.getHeaders())
        .setHeader("url", properties.getApi().getHost() + partenairePath)
        .setHeader("expected-response-type", RessourcePartenaire.class)
        .build();
  }
}
