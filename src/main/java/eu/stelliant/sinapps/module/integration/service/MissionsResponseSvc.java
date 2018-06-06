package eu.stelliant.sinapps.module.integration.service;

import com.darva.sinapps.api.client.transverse.model.InlineResponse2002;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Component;

@Component
public class MissionsResponseSvc {

  @ServiceActivator
  public String get(Message<InlineResponse2002> msg) {

    return msg.getPayload().toString();
  }
}
