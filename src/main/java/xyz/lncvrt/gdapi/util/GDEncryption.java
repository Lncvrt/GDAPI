package xyz.lncvrt.gdapi.util;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.util.Base64;
import java.util.List;
import java.util.Random;

@SuppressWarnings("unused")
public class GDEncryption {
    public static String generateChk(List<Object> values, String key, String salt) {
        values.add(salt);
        StringBuilder sb = new StringBuilder();
        for (Object val : values) sb.append(val.toString());
        String string = sb.toString();

        String hashed = sha1(string);
        String xored = xorCipher(hashed, key);
        return Base64.getUrlEncoder().encodeToString(xored.getBytes());
    }

    private static String sha1(String input) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-1");
            byte[] result = md.digest(input.getBytes());
            StringBuilder sb = new StringBuilder();
            for (byte b : result) sb.append(String.format("%02x", b));
            return sb.toString();
        } catch (Exception e) { throw new RuntimeException(e); }
    }

    private static String xorCipher(String input, String key) {
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < input.length(); i++) {
            byte b = (byte) input.charAt(i);
            byte k = (byte) key.charAt(i % key.length());
            result.append((char) (b ^ k));
        }
        return result.toString();
    }

    public static String generateGJP2(String password, String salt) {
        String input = password + salt;
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-1");
            byte[] digest = md.digest(input.getBytes(StandardCharsets.UTF_8));
            StringBuilder hex = new StringBuilder();
            for (byte b : digest) hex.append(String.format("%02x", b));
            return hex.toString();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static String generateGJP2(String password) {
        return generateGJP2(password, "mI29fmAnxgTs");
    }

    public static String generateUdid(int start, int end) {
        Random rand = new Random();
        return "S15" + rand.nextInt(end - start + 1) + start +
                rand.nextInt(end - start + 1) + start +
                rand.nextInt(end - start + 1) + start +
                rand.nextInt(end - start + 1) + start;
    }

    public static String generateUdid() {
        return generateUdid(100_000, 100_000_000);
    }
}
