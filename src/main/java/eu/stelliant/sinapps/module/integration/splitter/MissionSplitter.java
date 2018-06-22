package eu.stelliant.sinapps.module.integration.splitter;

import com.darva.sinapps.api.client.expertise.model.InlineResponse2001;
import com.darva.sinapps.api.client.expertise.model.RessourceAbstractMissions;
import java.util.List;
import org.springframework.stereotype.Component;

@Component
public class MissionSplitter {

  public List<RessourceAbstractMissions> split(InlineResponse2001 msgs) {
    return msgs.getItems();
  }
}
