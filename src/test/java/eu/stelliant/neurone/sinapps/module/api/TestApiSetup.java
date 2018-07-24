package eu.stelliant.neurone.sinapps.module.api;

import eu.stelliant.neurone.sinapps.module.api.config.ApiProperties;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

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

}
