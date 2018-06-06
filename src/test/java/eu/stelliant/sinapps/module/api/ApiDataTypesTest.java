package eu.stelliant.sinapps.module.api;

import com.darva.sinapps.api.client.transverse.model.DataTypes;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import java.io.IOException;
import org.junit.BeforeClass;
import org.junit.Test;

public class ApiDataTypesTest {

  private static final ObjectMapper mapper = new ObjectMapper();

  @BeforeClass
  public static void init() {
    mapper.findAndRegisterModules();
    mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
    mapper.disable(DeserializationFeature.ADJUST_DATES_TO_CONTEXT_TIME_ZONE);
  }

  @Test(expected = InvalidFormatException.class)
  public void should_throw_jackson_invalidFormatException() throws IOException {

    // Given
    String jsonDataTypes = "{\"dateType\": \"2018-05-17T10:57:47+0200\"}";

    // When
    DataTypes dataTypes = mapper.readValue(jsonDataTypes, DataTypes.class);

    // Then @see expected

  }

  @Test
  public void should_be_parsed() throws IOException {

    // Given
    String jsonDataTypes = "{\"dateType\": \"2018-05-17T10:57:47+02:00\"}";

    // When
    DataTypes dataTypes = mapper.readValue(jsonDataTypes, DataTypes.class);

    // Then
    //ZoneOffset zoneOffset = new ZoneOffset();
  }
}
