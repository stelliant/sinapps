package eu.stelliant.sinapps.module.api;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

import com.darva.sinapps.api.client.transverse.model.DataTypes;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import com.fasterxml.jackson.databind.exc.MismatchedInputException;
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

  @Test
  public void date_should_throw_jackson_invalidFormatException() {

    // Given
    String jsonDataTypes = "{\"dateType\": \"2018-05-17T10:57:47+0200\"}";

    // When
    Throwable exception = catchThrowable(() -> mapper.readValue(jsonDataTypes, DataTypes.class));

    // Then
    assertThat(exception).isInstanceOf(InvalidFormatException.class)
        .hasMessageContaining("could not be parsed at index 19");
  }

  @Test
  public void date_should_be_parsed() throws IOException {

    // Given
    String jsonDataTypes = "{\"dateType\": \"2018-05-17T10:57:47+02:00\"}";

    // When
    DataTypes dataTypes = mapper.readValue(jsonDataTypes, DataTypes.class);

    // Then
    assertThat(dataTypes.getDateType().getOffset().toString()).isEqualTo("+02:00");
  }

  @Test
  public void array_should_throw_jackson_MismatchedInputException() {

    // Given
    String jsonDataTypes = "{\"arrayType\": {"
        + " \"/assureurId\": {"
        + "   \"type\": {"
        + "     \"array\": {"
        + "       \"type\": \"BSONObjectID\""
        + "     }"
        + "   },"
        + "   \"optional\": true"
        + " },"
        + " \"/prestataireId\": {"
        + "   \"type\": {"
        + "     \"array\": {"
        + "       \"type\": \"BSONObjectID\""
        + "     }"
        + "   },"
        + "   \"optional\": true"
        + " }"
        + "}";

    // When
    Throwable exception = catchThrowable(() -> mapper.readValue(jsonDataTypes, DataTypes.class));

    // Then
    assertThat(exception).isInstanceOf(MismatchedInputException.class)
        .hasMessageContaining("Cannot deserialize instance of `java.util.ArrayList`");
  }

  @Test
  public void array_should_be_parsed() throws IOException {

    // Given
    String jsonDataTypes = "{\"arrayType\": ["
        + " {"
        + "   \"/assureurId\": {"
        + "     \"type\": {"
        + "       \"array\": {"
        + "         \"type\": \"BSONObjectID\""
        + "       }"
        + "     },"
        + "     \"optional\": true"
        + "   }"
        + " }, {"
        + "   \"/prestataireId\": {"
        + "     \"type\": {"
        + "       \"array\": {"
        + "         \"type\": \"BSONObjectID\""
        + "       }"
        + "     },"
        + "     \"optional\": true"
        + "   }"
        + " }]"
        + "}";

    // When
    DataTypes dataTypes = mapper.readValue(jsonDataTypes, DataTypes.class);

    // Then
    assertThat(dataTypes.getArrayType().size()).isEqualTo(2);
  }
}
