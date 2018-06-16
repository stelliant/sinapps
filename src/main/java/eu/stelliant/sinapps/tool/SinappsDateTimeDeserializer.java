package eu.stelliant.sinapps.tool;

import com.fasterxml.jackson.datatype.jsr310.deser.InstantDeserializer;
import java.time.Instant;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.util.Locale;

public class SinappsDateTimeDeserializer extends InstantDeserializer<ZonedDateTime> {

  private static final long serialVersionUID = 1L;

  public static final DateTimeFormatter ISO_SINAPPS_DATE_TIME;

  static {
    ISO_SINAPPS_DATE_TIME = new DateTimeFormatterBuilder()
        .parseCaseInsensitive()
        .append(DateTimeFormatter.ISO_LOCAL_DATE_TIME)
        .appendOffset("+HHMMss", "+0000")
        .optionalStart()
        .appendLiteral('[')
        .parseCaseSensitive()
        .appendZoneRegionId()
        .appendLiteral(']')
        .toFormatter(Locale.getDefault(Locale.Category.FORMAT));
  }

  public SinappsDateTimeDeserializer() {
    // most parameters are the same used by InstantDeserializer
    super(ZonedDateTime.class,
          SinappsDateTimeDeserializer.ISO_SINAPPS_DATE_TIME,
          ZonedDateTime::from,
          a ->
              ZonedDateTime.ofInstant(Instant.ofEpochMilli(a.value), a.zoneId)
        ,
          a -> ZonedDateTime
              .ofInstant(Instant.ofEpochSecond(a.integer, (long) a.fraction), a.zoneId)
        ,
          null,
          false);
  }
}
