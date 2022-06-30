package cc.stacks.mercury.util;

import org.apache.commons.codec.digest.DigestUtils;

import javax.servlet.http.HttpServletRequest;
import java.net.InetAddress;

@SuppressWarnings("unused")
public class SecurityUtil {

    /**
     * MD5摘要
     *
     * @param text 原始文本
     * @return 摘要码
     */
    public static String digestMD5(String text) {
        return DigestUtils.md5Hex(text);
    }

    /**
     * MD5摘要
     *
     * @param textByte 原始字节
     * @return 摘要码
     */
    public static String digestMD5(byte[] textByte) {
        return DigestUtils.md5Hex(textByte);
    }

    /**
     * 提取IP地址
     *
     * @param request 请求
     * @return IP地址
     */
    public static String extractIP(HttpServletRequest request) {
        try {
            String unknown = "unknown";
            String ipAddress;
            ipAddress = request.getHeader("x-forwarded-for");
            if (ipAddress == null || ipAddress.length() == 0 || unknown.equalsIgnoreCase(ipAddress))
                ipAddress = request.getHeader("Proxy-Client-IP");
            if (ipAddress == null || ipAddress.length() == 0 || unknown.equalsIgnoreCase(ipAddress))
                ipAddress = request.getHeader("WL-Proxy-Client-IP");
            if (ipAddress == null || ipAddress.length() == 0 || unknown.equalsIgnoreCase(ipAddress)) {
                ipAddress = request.getRemoteAddr();
                if ("127.0.0.1".equals(ipAddress)) {
                    InetAddress inet = InetAddress.getLocalHost();
                    ipAddress = inet.getHostAddress();
                }
            }
            if (ipAddress != null && ipAddress.length() > 15 && ipAddress.contains(","))
                ipAddress = ipAddress.substring(0, ipAddress.indexOf(","));
            return ipAddress;
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * 判断是否内网
     * @param ip IP地址
     * @return 是否内网
     */
    public static boolean isLocalHost(String ip) {
        try {
            if(ip==null) return false;
            if(ip.split("\\.").length!=4) return false;

            String classA = "10.";
            if (ip.startsWith(classA)) return true;

            String classB = "172.";
            if (ip.startsWith(classB)) {
                String code = ip.substring(4);
                int num = Integer.parseInt(code.substring(0,code.indexOf(".")));
                if (num >= 16 && num <= 31) return true;
            }

            String classC = "192.168.";
            return ip.startsWith(classC);
        } catch (Exception e) {
            return false;
        }
    }

}