package eu.stelliant.sinapps.tool;

import com.fasterxml.jackson.datatype.jsr310.deser.InstantDeserializer;
import java.time.Instant;
import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.util.Locale;

public class SinappsDateTimeDeserializer extends InstantDeserializer<OffsetDateTime> {

  public static final DateTimeFormatter ISO_SINAPPS_DATE_TIME;
  private static final long serialVersionUID = 1L;

  static {
    ISO_SINAPPS_DATE_TIME = new DateTimeFormatterBuilder()
        .parseCaseInsensitive()
        .append(DateTimeFormatter.ISO_LOCAL_DATE_TIME)
        .appendOffset("+HHMMss", "+0000")
        .toFormatter(Locale.getDefault(Locale.Category.FORMAT));
  }

  public SinappsDateTimeDeserializer() {
    // most parameters are the same used by InstantDeserializer
    super(OffsetDateTime.class,
          SinappsDateTimeDeserializer.ISO_SINAPPS_DATE_TIME,
          OffsetDateTime::from,
          a ->
              OffsetDateTime.ofInstant(Instant.ofEpochMilli(a.value), a.zoneId)
        ,
          a -> OffsetDateTime
              .ofInstant(Instant.ofEpochSecond(a.integer, (long) a.fraction), a.zoneId)
        ,
          null,
          false);
  }
}
