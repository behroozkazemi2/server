package com.behrouz.server.utils;

import java.util.Random;

/**
 * Created by Hapi
 * Package com.behrouz.server.utils
 * Project Koala Server
 * 09 September 2018 13:16
 **/
public class StringGenerator {

    private static String generate(int tokenLength, String characters) {
        StringBuilder salt = new StringBuilder();
        Random rnd = new Random();
        while (salt.length() < tokenLength) {
            int index = (int) (rnd.nextFloat() * characters.length());
            salt.append(characters.charAt(index));
        }
        return salt.toString();
    }

    private static String generate(int tokenLength) {
        return generate(tokenLength, "abcdefghijklmnopqrstuvwxyz1234567890");
    }

    public static String generateDigit(int tokenLength) {
        return generate(tokenLength, "1234567890");
    }

    public static String generatePassword() {
        return generate(8);
    }


    public static String generateToken(int len) {
        return generate(len);
    }

    public static String generateToken() {
        return generateToken(30);
    }

    public static String generateSalt() {
        return generate(64);
    }

}
