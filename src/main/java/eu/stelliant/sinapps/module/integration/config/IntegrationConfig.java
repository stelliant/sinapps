package eu.stelliant.sinapps.module.integration.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;

@Configuration
@ImportResource("classpath:integration/api-connexion.xml")
public class IntegrationConfig {

}