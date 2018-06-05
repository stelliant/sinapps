package eu.stelliant.sinapps.module.integration.config;

import io.swagger.sinapps.api.client.transverse.model.InlineResponse200;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Component;

@Component
public class ConnexionResponseSvc {

  @ServiceActivator
  public String get(Message<InlineResponse200> msg) {

    return msg.getPayload().toString();
  }
}
