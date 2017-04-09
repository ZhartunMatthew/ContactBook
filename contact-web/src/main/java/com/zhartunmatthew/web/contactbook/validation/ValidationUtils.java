package com.zhartunmatthew.web.contactbook.validation;

import org.apache.commons.lang3.StringUtils;

public class ValidationUtils {
    private static final String DIGITS = "0123456789";
    private static final String ALPHABET_EN = "abcdefghijklmnopqrstuvwxyz";
    private static final String ALPHABET_RU = "абвгдеёжзийклмнопрстуфхцчшщъыьэюя";

    public static boolean isNumber(String inputValue) {
        return StringUtils.containsOnly(inputValue, DIGITS);
    }

    public static boolean hasOnlyChars(String inputValue, String chars) {
        return StringUtils.containsOnly(inputValue.toLowerCase(), ALPHABET_EN + ALPHABET_RU + chars);
    }

    public static boolean checkLength(String inputValue, int max) {
        return StringUtils.length(inputValue) < max;
    }
}
