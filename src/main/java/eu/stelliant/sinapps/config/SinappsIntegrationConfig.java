package eu.stelliant.sinapps.config;

import io.swagger.sinapps.client.ApiClient;
import eu.stelliant.sinapps.client.transverse.api.ConnexionDconnexionApi;
import eu.stelliant.sinapps.client.transverse.api.GnralitsSurLesConsultationsDeRessourcesApi;
import eu.stelliant.sinapps.client.transverse.api.PartenairesApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SinappsIntegrationConfig {

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