package eu.stelliant.sinapps.module.integration.splitter;

import com.darva.sinapps.api.client.transverse.model.InlineResponse2002;
import com.darva.sinapps.api.client.transverse.model.InlineResponse2002Items;
import java.util.List;
import org.springframework.stereotype.Component;

@Component
public class MissionSplitter {

  public List<InlineResponse2002Items> split(InlineResponse2002 inlineResponse2002) {
    return inlineResponse2002.getItems();
  }
}
