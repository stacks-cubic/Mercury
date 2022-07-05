package cc.stacks.mercury.util;

import org.apache.commons.codec.binary.Base32;
import org.springframework.web.util.UriUtils;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.time.Instant;
import java.util.Random;

@SuppressWarnings("unused")
public class TOTPUtil {

    private TOTPUtil() {
        throw new IllegalStateException("Utility class");
    }

    // 生成密钥
    public static String getKey() {
        return new Base32().encodeAsString(getRandomBytes());
    }

    public static String getUrl(String name, String code) {
        return "otpauth://totp/" + UriUtils.encode("Mercury(" + name + ")", "utf-8") + "?secret=" + code;
    }

    // 校验代码
    public static boolean valid(String secret, String code) {
        long currentBucket = Math.floorDiv(Instant.now().getEpochSecond(), 30);
        boolean success = false;
        for (int i = -1; i <= 1; i++) success = checkCode(secret, currentBucket + i, code) || success;
        return success;
    }

    private static byte[] getRandomBytes() {
        byte[] bytes = new byte[(160) / 8];
        new SecureRandom().nextBytes(bytes);
        return bytes;
    }

    private static boolean checkCode(String secret, long counter, String code) {
        try {
            String actualCode = generate(secret, counter);
            return timeSafeStringComparison(actualCode, code);
        } catch (Exception e) {
            return false;
        }
    }

    private static String generate(String key, long counter) {
        try {
            byte[] hash = generateHash(key, counter);
            return getDigitsFromHash(hash);
        } catch (Exception e) {
            return null;
        }
    }

    private static byte[] generateHash(String key, long counter) throws InvalidKeyException, NoSuchAlgorithmException {
        byte[] data = new byte[8];
        long value = counter;
        for (int i = 8; i-- > 0; value >>>= 8) {
            data[i] = (byte) value;
        }
        Base32 codec = new Base32();
        byte[] decodedKey = codec.decode(key);
        SecretKeySpec signKey = new SecretKeySpec(decodedKey, "HmacSHA1");
        Mac mac = Mac.getInstance("HmacSHA1");
        mac.init(signKey);
        return mac.doFinal(data);
    }

    private static String getDigitsFromHash(byte[] hash) {
        int offset = hash[hash.length - 1] & 0xF;
        long truncatedHash = 0;
        for (int i = 0; i < 4; ++i) {
            truncatedHash <<= 8;
            truncatedHash |= (hash[offset + i] & 0xFF);
        }
        truncatedHash &= 0x7FFFFFFF;
        truncatedHash %= Math.pow(10, 6);
        return String.format("%0" + 6 + "d", truncatedHash);
    }

    private static boolean timeSafeStringComparison(String a, String b) {
        byte[] aBytes = a.getBytes();
        byte[] bBytes = b.getBytes();

        if (aBytes.length != bBytes.length) {
            return false;
        }

        int result = 0;
        for (int i = 0; i < aBytes.length; i++) {
            result |= aBytes[i] ^ bBytes[i];
        }

        return result == 0;
    }

    private static String generateCode() {
        Random random = new SecureRandom();
        final StringBuilder code = new StringBuilder(16 + (16 / 4) - 1);
        final char[] characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789".toCharArray();
        for (int i = 0; i < 16; i++) {
            code.append(characters[random.nextInt(characters.length)]);
            if ((i + 1) % 4 == 0 && (i + 1) != 16)
                code.append("-");
        }
        return code.toString();
    }

}