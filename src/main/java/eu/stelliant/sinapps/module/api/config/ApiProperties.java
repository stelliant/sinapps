package eu.stelliant.sinapps.module.api.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "sinapps")
@Getter
@Setter
public class ApiProperties {

  private Api api;

  @Getter
  @Setter
  public static class Api {

    private String host;
    private Ssl ssl;
    private Login login;

    @Getter
    @Setter
    public static class Ssl {

      private String truststore;
      private String storepass;
    }

    @Getter
    @Setter
    public static class Login {

      private String mapping;
      private String username;
      private String password;
    }
  }
}
