package com.petition.platform.utils;

import java.util.Random;

public class PasswordGenerator {

    private static final String capitalCaseLetters = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    private static final String lowerCaseLetters = "abcdefghijklmnopqrstuvwxyz";
    private static final String specialCharacters = "!@#$";
    private static final String numbers = "1234567890";
    private static final String combinedChars = capitalCaseLetters + lowerCaseLetters + specialCharacters + numbers;

    public static String generatePassword(int length) {
        Random random = new Random(System.currentTimeMillis());
        char[] password = new char[length];
        password[0] = lowerCaseLetters.charAt(random.nextInt(lowerCaseLetters.length()));
        password[1] = capitalCaseLetters.charAt(random.nextInt(capitalCaseLetters.length()));
        password[2] = specialCharacters.charAt(random.nextInt(specialCharacters.length()));
        password[3] = numbers.charAt(random.nextInt(numbers.length()));
        for(int i = 4; i< length ; i++) {
            password[i] = combinedChars.charAt(random.nextInt(combinedChars.length()));
        }
        return new String(password);
    }
}
