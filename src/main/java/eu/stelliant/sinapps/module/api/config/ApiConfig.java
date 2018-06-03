package eu.stelliant.sinapps.module.api.config;

import io.swagger.sinapps.api.client.ApiClient;
import io.swagger.sinapps.api.client.transverse.api.ConnexionDconnexionApi;
import io.swagger.sinapps.api.client.transverse.api.GnralitsSurLesConsultationsDeRessourcesApi;
import io.swagger.sinapps.api.client.transverse.api.PartenairesApi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties({ApiProperties.class})
public class ApiConfig {

  @Autowired
  ApiProperties properties;

  public ApiProperties getProperties() {
    return properties;
  }

  @Bean
  public ConnexionDconnexionApi authentificationApi() {
    return new ConnexionDconnexionApi(apiClient());
  }

  @Bean
  public GnralitsSurLesConsultationsDeRessourcesApi ressourcesApi() {
    return new GnralitsSurLesConsultationsDeRessourcesApi(apiClient());
  }

  @Bean
  public PartenairesApi partenairesApi() {
    return new PartenairesApi(apiClient());
  }

  @Bean
  public ApiClient apiClient() {
    return new ApiClient();
  }
}