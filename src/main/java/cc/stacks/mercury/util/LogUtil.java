package cc.stacks.mercury.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@SuppressWarnings("unused")
public class LogUtil {

    private LogUtil() {
        throw new IllegalStateException("Utility class");
    }

    public static void info(String message) {
        info(0, message);
    }

    public static void warn(String message) {
        warn(0, message);
    }

    public static void info(int code, String message) {
        out("info", (code == 0 ? "" : "[" + code + "] ") + message);
    }

    public static void warn(int code, String message) {
        out("warn", (code == 0 ? "" : "[" + code + "] ") + message);
    }

    public static void error(int code, String message) {
        out("error", (code == 0 ? "" : "[" + code + "] ") + message);
    }

    public static void unexpected(int code, String action, Exception err) {
        out("error","["+code+"]"+ getContent(action,err));
    }

    public static String getContent(String action, Exception err){
        StringBuilder builder = new StringBuilder();
        builder.append(action).append(": ").append(err.getMessage());
        StackTraceElement[] stackTrace = err.getStackTrace();
        if (stackTrace.length > 0)
            builder.append(" -> ").append(stackTrace[0].getFileName()).append(":").append(stackTrace[0].getLineNumber());
        return builder.toString();
    }

    private static void out(String type, String message) {
        StackTraceElement[] stackTrace = new Exception().getStackTrace();
        Logger logger = LoggerFactory.getLogger(stackTrace[2].getClassName());
        switch (type) {
            case "info" -> logger.info(message);
            case "warn" -> logger.warn(message);
            case "error" -> logger.error(message);
        }
    }

}