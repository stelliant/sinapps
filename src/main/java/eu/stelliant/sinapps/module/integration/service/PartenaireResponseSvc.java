package eu.stelliant.sinapps.module.integration.service;

import com.darva.sinapps.api.client.transverse.model.InlineResponse2001;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Component;

@Component
public class PartenaireResponseSvc {

  @ServiceActivator
  public String get(Message<InlineResponse2001> msg) {

    return msg.getPayload().toString();
  }
}
