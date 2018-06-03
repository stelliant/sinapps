package eu.stelliant.sinapps.module.api.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "sinapps", ignoreUnknownFields = false)
public class ApiProperties {

  private Api api;

  public Api getApi() {
    return api;
  }

  public void setApi(Api api) {
    this.api = api;
  }

  public static class Api {

    private String baseUrl;
    private String loginPath;
    private String login;
    private String password;

    public String getBaseUrl() {
      return baseUrl;
    }

    public void setBaseUrl(String baseUrl) {
      this.baseUrl = baseUrl;
    }

    public String getLoginPath() {
      return loginPath;
    }

    public void setLoginPath(String loginPath) {
      this.loginPath = loginPath;
    }

    public String getLogin() {
      return login;
    }

    public void setLogin(String login) {
      this.login = login;
    }

    public String getPassword() {
      return password;
    }

    public void setPassword(String password) {
      this.password = password;
    }
  }
}
