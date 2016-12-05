package core;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.Locale;
import java.util.TimeZone;

@SuppressWarnings("WeakerAccess")
public class DateTimeUtil {
    public static String getDateTimeString(Instant time, TimeZone tz, Locale locale){
        ZonedDateTime zdt = ZonedDateTime.ofInstant(time, tz.toZoneId());
        DateTimeFormatter dtf = DateTimeFormatter.ofLocalizedDateTime(
                FormatStyle.MEDIUM).withLocale(locale);
        return zdt.format(dtf);
    }

    public static String getDateString(LocalDate date, TimeZone tz,  Locale locale){
        DateTimeFormatter dtf = DateTimeFormatter.ofLocalizedDate(
                FormatStyle.LONG).withZone(tz.toZoneId()).withLocale(locale);
        return date.format(dtf);
    }
}
