package eu.stelliant.sinapps.module.api;

import eu.stelliant.sinapps.module.api.config.ApiProperties;
import eu.stelliant.sinapps.tool.ApiClientHttpRequestInterceptor;
import java.util.ArrayList;
import java.util.List;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.client.BufferingClientHttpRequestFactory;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.RestTemplate;

@Ignore
@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("test")
public class TestApiSetup {

  @Autowired
  ApiProperties apiProperties;

  /**
   * To be used for initialization of some data needed for most of the tests
   */
  @Before
  public void initApi() {

  }

  protected RestTemplate getRestTemplate() {
    // Create a new RestTemplate instance
    RestTemplate restTemplate = new RestTemplate();
    restTemplate.setRequestFactory(new BufferingClientHttpRequestFactory(restTemplate.getRequestFactory()));

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
