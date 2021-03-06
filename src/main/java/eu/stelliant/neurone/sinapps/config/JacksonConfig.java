package eu.stelliant.neurone.sinapps.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import eu.stelliant.neurone.sinapps.tool.SinappsDateTimeDeserializer;
import java.time.OffsetDateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Configuration;

@Configuration
public class JacksonConfig implements ApplicationListener<ApplicationReadyEvent> {

  private final ObjectMapper objectMapper;

  @Autowired
  public JacksonConfig(ObjectMapper objectMapper) {
    this.objectMapper = objectMapper;
  }

  @Override
  public void onApplicationEvent(ApplicationReadyEvent applicationReadyEvent) {
    SimpleModule module = new SimpleModule();
    module.addDeserializer(OffsetDateTime.class, new SinappsDateTimeDeserializer());
    objectMapper.registerModule(module);
  }

}
