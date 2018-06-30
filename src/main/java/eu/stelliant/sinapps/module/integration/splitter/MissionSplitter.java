package eu.stelliant.sinapps.module.integration.splitter;

import com.darva.sinapps.api.client.expertise.model.RessourceAbstractMissions;
import com.darva.sinapps.api.client.expertise.model.RessourceListeAbstractMissions;
import java.util.List;
import org.springframework.stereotype.Component;

@Component
public class MissionSplitter {

  public List<RessourceAbstractMissions> split(RessourceListeAbstractMissions msgs) {
    return msgs.getItems();
  }
}
