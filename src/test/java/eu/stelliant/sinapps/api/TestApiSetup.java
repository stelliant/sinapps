package eu.stelliant.sinapps.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import eu.stelliant.sinapps.SinappsApplication;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {SinappsApplication.class})
@ActiveProfiles("test")
public class TestApiSetup {

  @Autowired
  protected ObjectMapper objectMapper;

  @Value("${sinappsApi.baseUrl}")
  protected String baseUrl;
  @Value("${sinappsApi.loginPath}")
  protected String loginPath;
  @Value("${sinappsApi.login}")
  protected String login;
  @Value("${sinappsApi.password}")
  protected String password;

  /**
   * To be used for initialization of some data needed for most of the tests
   */
  @Before
  public void initApi() {

  }

}
