package eu.stelliant.sinapps;

import eu.stelliant.sinapps.config.SinappsIntegrationConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

@SpringBootApplication
@Import(SinappsIntegrationConfig.class)
public class SinappsApplication {

  public static void main(String[] args) {
    SpringApplication.run(SinappsApplication.class, args);
  }
}
