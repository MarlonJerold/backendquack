package com.quackfinances.quackfinances.utils;


import java.time.format.DateTimeFormatter;
import java.util.Locale;

public class DateTimeUtils {
    public static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss", Locale.getDefault());

}