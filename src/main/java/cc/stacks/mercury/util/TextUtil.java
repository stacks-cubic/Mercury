package cc.stacks.mercury.util;

import java.security.SecureRandom;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 文本工具
 *
 * @author SkayZhang <skai-zhang@hotmail.com>
 * @date 2021-12-28 09:58
 */
@SuppressWarnings("unused")
public class TextUtil {

    public static final String DATE_TIME = "yyyy-MM-dd HH:mm:ss";

    private TextUtil() {
        throw new IllegalStateException("Utility class");
    }

    // 是空字符串
    public static boolean isNull(String txt) {
        return txt == null || txt.trim().equals("");
    }

    public static boolean isNull(Object obj) {
        return obj == null || String.valueOf(obj).trim().equals("") || String.valueOf(obj).trim().equals("0");
    }

    public static boolean isNull(Integer txt) {
        return txt == null || txt == 0;
    }

    public static boolean isNull(Long txt) {
        return txt == null || txt == 0;
    }

    // 是否全数字
    public static boolean isAllNumber(String txt) {
        return Pattern.matches("^\\d+$", txt);
    }

    // 是否全中文
    public static boolean isAllChinese(String txt) {
        return Pattern.matches("^[\\u4e00-\\u9fa5]+$", txt);
    }

    // 是否全英文
    public static boolean isAllEnglish(String txt) {
        return Pattern.matches("^[A-Za-z]+$", txt);
    }

    // 校验姓名
    public static boolean checkName(String txt) {
        if (txt.length() < 2 || txt.length() > 8) return false;
        return isAllChinese(txt);
    }

    // 校验手机号码
    public static boolean checkPhone(String txt) {
        return Pattern.matches("^1(3\\d|4[5,7]|5\\d|6[2,5-7]|7\\d|8\\d|9[1,8-9])\\d{8}$", txt);
    }

    // 校验url
    public static boolean checkUrl(String txt) {
        return Pattern.matches("^((https|http)?://)\\S+", txt);
    }

    // 生成随机数
    public static String getRandomNumber(int length) {
        try {
            StringBuilder builder = new StringBuilder();
            String data = "1234567890";
            int len = data.length();
            SecureRandom random = SecureRandom.getInstance("SHA1PRNG");
            for (int i = 0; i < length; i++)
                builder.append(data.charAt((int) Math.round(random.nextDouble() * (len - 1))));
            return builder.toString();
        } catch (Exception e) {
            return null;
        }
    }

    // 生成随机字符串
    public static String getRandomString(int length) {
        try {
            StringBuilder builder = new StringBuilder();
            String str = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
            SecureRandom random = SecureRandom.getInstance("SHA1PRNG");
            for (int i = 0; i < length; i++) {
                int number = random.nextInt(62);
                builder.append(str.charAt(number));
            }
            return builder.toString();
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * 驼峰转下划线
     *
     * @param txt 待转换的字符串
     * @return 转换后的字符串
     */
    public static String lineToHump(String txt) {
        Matcher matcher = Pattern.compile("[A-Z]").matcher(txt);
        StringBuilder builder = new StringBuilder();
        while (matcher.find()) {
            matcher.appendReplacement(builder, "_" + matcher.group(0).toLowerCase());
        }
        matcher.appendTail(builder);
        if ("_".equals(builder.substring(0, 1))) return builder.substring(1, builder.length());
        return builder.toString();
    }

    // 手机号码脱敏
    public static String mobileEncrypt(String mobile) {
        if (isNull(mobile) || (mobile.length() != 11)) return mobile;
        return mobile.replaceAll("(\\d{3})\\d{4}(\\d{4})", "$1****$2");
    }

    // 手机号码脱敏
    public static String nameEncrypt(String name) {
        if(name.length()==2) return encrypt(name,1,2);
        else if(name.length() > 2) return encrypt(name,1,name.length() - 1);
        return "*";
    }

    // 自定义脱敏
    public static String encrypt(String text, int start, int end) {
        StringBuilder textBuilder = new StringBuilder();
        textBuilder.append(text, 0, start);
        textBuilder.append(buildStar(end - start));
        if (text.length() < end) textBuilder.append(buildStar(text.length() - end));
        else textBuilder.append(text, end, text.length());
        return textBuilder.toString();
    }

    // 生成星号字符
    public static String buildStar(int length) {
        if (length <= 0) {
            return "";
        }
        char[] arr = new char[length];
        for (int i = 0; i < length; i++) {
            arr[i] = '*';
        }
        return new String(arr);
    }

    // 解析日期
    public static String buildDate(Object date, String pattern) {
        try {
            long datetime = Long.parseLong(String.valueOf(date));
            DateTimeFormatter formDay = DateTimeFormatter.ofPattern(pattern);
            return buildDate(LocalDate.ofEpochDay(datetime / (datetime > 100000 ? 86400000 : 1)), pattern);
        } catch (Exception e) {
            return "Date Error";
        }
    }

    public static String buildDate(LocalDate date, String pattern) {
        try {
            DateTimeFormatter formDay = DateTimeFormatter.ofPattern(pattern);
            return formDay.format(date);
        } catch (Exception e) {
            return "Date Error";
        }
    }

    // 解析时间
    public static String buildTime(Object time, String pattern) {
        try {
            return buildTime(LocalDateTime.ofEpochSecond(Long.parseLong(String.valueOf(time)) / 1000, 0, ZoneOffset.ofHours(8)), pattern);
        } catch (Exception e) {
            return "Datetime Error";
        }
    }

    /**
     * 解析文件大小
     *
     * @param obj 大小字符
     * @return 文件大小
     */
    public static String analysisSize(Object obj) {
        long size = Long.parseLong(String.valueOf(obj));
        if (size < 1024) return size + "B";
        size = size / 1024;
        if (size < 1024) return size + "KB";
        size = size / 1024;
        if (size < 1024) return size + "MB";
        size = size / 1024;
        if (size < 1024) return size + "GB";
        return (size / 1024) + " TB";
    }

    public static String buildTime(LocalDateTime time, String pattern) {
        try {
            DateTimeFormatter formDay = DateTimeFormatter.ofPattern(pattern);
            return formDay.format(time);
        } catch (Exception e) {
            return "Datetime Error";
        }
    }

}