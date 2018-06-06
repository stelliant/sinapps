package eu.stelliant.sinapps.module.api.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties({ApiProperties.class})
public class ApiConfig {

  @Autowired
  ApiProperties properties;

  public ApiProperties getProperties() {
    return properties;
  }

}