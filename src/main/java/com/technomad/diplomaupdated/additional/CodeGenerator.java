package com.technomad.diplomaupdated.additional;

import java.util.Random;

public class CodeGenerator {

    public static String generateRandomString(int length) {
        Random random = new Random();
        StringBuilder stringBuilder = new StringBuilder(length);
        for (int i = 0; i < length; i++) {
            stringBuilder.append(random.nextInt(10)); // Generates a random digit (0-9)
        }
        return stringBuilder.toString();
    }

    public static String generate(){
        return generateRandomString(4);
    }
}