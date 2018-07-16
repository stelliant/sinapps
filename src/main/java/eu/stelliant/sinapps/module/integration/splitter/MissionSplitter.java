package eu.stelliant.sinapps.module.integration.splitter;

import com.darva.sinapps.api.client.expertise.model.RessourceAbstractMissions;
import com.darva.sinapps.api.client.expertise.model.ListeAbstractMissions;
import java.util.List;
import org.springframework.stereotype.Component;

@Component
public class MissionSplitter {

  public List<RessourceAbstractMissions> split(ListeAbstractMissions msgs) {
    return msgs.getItems();
  }
}
