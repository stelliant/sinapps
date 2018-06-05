package eu.stelliant.sinapps.module.integration.config;

import io.swagger.sinapps.api.client.transverse.model.ListeRessources;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Component;

@Component
public class MissionsResponseSvc {

  @ServiceActivator
  public String get(Message<ListeRessources> msg) {

    return msg.getPayload().toString();
  }
}
