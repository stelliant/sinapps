package eu.stelliant.sinapps.module.api.config;

import javax.net.ssl.SSLContext;
import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.ssl.SSLContextBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.util.ResourceUtils;
import org.springframework.web.client.RestTemplate;

@Configuration
@EnableConfigurationProperties({ApiProperties.class})
@Profile("!test")
public class ApiConfig {

  @Autowired
  ApiProperties properties;

  @Bean
  public RestTemplate restTemplate(RestTemplateBuilder builder) throws Exception {

    return getRestTemplate(builder);
  }

  protected RestTemplate getRestTemplate(RestTemplateBuilder builder) throws Exception {

    SSLContext sslContext = new SSLContextBuilder()
        .create()
        .loadTrustMaterial(
            ResourceUtils.getURL(properties.getApi().getTruststore()),
            properties.getApi().getStorepass().toCharArray()
        ).build();

    HttpClient client = HttpClients.custom()
        .setSSLContext(sslContext)
        .build();

    return builder
        .requestFactory(() -> new HttpComponentsClientHttpRequestFactory(client))
        .build();
  }

}