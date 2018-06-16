package eu.stelliant.sinapps.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import eu.stelliant.sinapps.tool.SinappsDateTimeDeserializer;
import java.time.ZonedDateTime;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Configuration;

@Configuration
public class JacksonConfig implements ApplicationListener<ApplicationReadyEvent> {

  private final Log log = LogFactory.getLog(JacksonConfig.class);

  @Autowired
  private ObjectMapper objectMapper;

  @Override
  public void onApplicationEvent(ApplicationReadyEvent applicationReadyEvent) {
    SimpleModule module = new SimpleModule();
    module.addDeserializer(ZonedDateTime.class, new SinappsDateTimeDeserializer());
    objectMapper.registerModule(module);
  }

}
