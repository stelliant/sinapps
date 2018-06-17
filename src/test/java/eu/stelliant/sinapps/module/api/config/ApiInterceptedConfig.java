package eu.stelliant.sinapps.module.api.config;

import eu.stelliant.sinapps.tool.ApiClientHttpRequestInterceptor;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;
import org.springframework.http.client.BufferingClientHttpRequestFactory;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.web.client.RestTemplate;

@Configuration
@Profile("test")
public class ApiInterceptedConfig extends ApiConfig {

  @Autowired
  public ApiInterceptedConfig(ApiProperties properties) {
    super(properties);
  }

  @Bean
  @Primary
  public RestTemplate restTemplate(RestTemplateBuilder builder) throws Exception {

    RestTemplate restTemplate = getRestTemplate(builder);
    restTemplate
        .setRequestFactory(new BufferingClientHttpRequestFactory(restTemplate.getRequestFactory()));

    List<ClientHttpRequestInterceptor> currentInterceptors = restTemplate.getInterceptors();
    if (currentInterceptors == null) {
      currentInterceptors = new ArrayList<>();
    }
    ClientHttpRequestInterceptor interceptor = new ApiClientHttpRequestInterceptor();
    currentInterceptors.add(interceptor);
    restTemplate.setInterceptors(currentInterceptors);

    return restTemplate;
  }

}
