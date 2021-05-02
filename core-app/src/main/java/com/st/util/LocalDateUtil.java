package com.st.util;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

public class LocalDateUtil {
    public final static DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss:SSS", Locale.ENGLISH);

    public static LocalDateTime convertStringToTimeStamp(String timeStamp) {
        return LocalDateTime.parse(timeStamp.trim(), DATE_TIME_FORMATTER);
    }
}
