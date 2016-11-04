package edu.hutech.shippermanager.utils;

import android.util.Base64;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

/**
 * Created by hienl on 11/4/2016.
 */

public class CryptUtils {
    private CryptUtils(){}
    /**
     * Get MD5 value for a string
     *
     * @param input
     *     the input
     * @return the string
     */
    public static String md5(String input) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] messageDigest = md.digest(input.getBytes());
            BigInteger number = new BigInteger(1, messageDigest);
            String md5 = number.toString(16);

            while (md5.length() < 32) {
                md5 = "0" + md5;
            }

            return md5;
        } catch (NoSuchAlgorithmException e) {
            return null;
        }
    }

    /**
     * Encode message using Base64 encoding
     *
     * @param message
     *     the message
     * @return the string
     */
    public static String encodedBase64(String message) {
        return Base64.encodeToString(message.getBytes(), Base64.DEFAULT);
    }

    /**
     * Decode encoded message using Base64
     *
     * @param message
     *     the message
     * @return the string
     */
    public static String decodedBase64(String message) {
        return Arrays.toString(Base64.decode(message, Base64.DEFAULT));
    }
}
