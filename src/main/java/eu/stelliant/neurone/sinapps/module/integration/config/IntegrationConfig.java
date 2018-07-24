package eu.stelliant.neurone.sinapps.module.integration.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;
import org.springframework.context.annotation.Profile;

@Configuration
@ImportResource("classpath:sinapps/integration/api-connexion.xml")
@Profile("!test")
public class IntegrationConfig {

}