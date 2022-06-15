package cc.stacks.mercury.util;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * 数据中转
 *
 * @author SkayZhang <skai-zhang@hotmail.com>
 * @date 2021-12-27 10:28
 */
@Getter
@Setter
@SuppressWarnings("unused")
public class Transit<T> implements Serializable {

    // 状态
    private boolean state;
    // 代码
    private Integer code;
    // 信息
    private String message;
    // 数据
    private T data;
    // 响应时间
    private long time;

    public Transit() {
    }

    Transit(boolean state, Integer code, String message, T data) {
        this.state = state;
        this.code = code;
        this.message = message;
        this.data = data;
        this.time = System.currentTimeMillis();
    }

    public static <T> Transit<T> auto(boolean state) {
        return new Transit<>(state, null, null, null);
    }

    public static <T> Transit<T> success() {
        return new Transit<>(true, null, null, null);
    }

    public static <T> Transit<T> failure() {
        return new Transit<>(false, null, null, null);
    }

    // 信息中转
    public static <T> Transit<T> success(String message) {
        return new Transit<>(true, null, message, null);
    }

    public static <T> Transit<T> failure(String message) {
        return new Transit<>(false, null, message, null);
    }

    // 数据中转
    public static <T> Transit<T> success(T data) {
        return new Transit<>(true, null, null, data);
    }

    public static <T> Transit<T> failure(int code) {
        return new Transit<>(false, code, null, null);
    }

    public static <T> Transit<T> failure(int code,String message) {
        return new Transit<>(false, code, message, null);
    }

    public static <T> Transit<T> failure(int code,T data) {
        return new Transit<>(false, code, null, data);
    }

    // 信息、数据中转
    public static <T> Transit<T> success(String message, T data) {
        return new Transit<>(true, null, message, data);
    }

    public static <T> Transit<T> failure(String message, T data) {
        return new Transit<>(false, null, message, data);
    }

    // 信息、代码中转
    public static <T> Transit<T> success(Integer code, String message) {
        return new Transit<>(true, code, message, null);
    }

    public static <T> Transit<T> failure(Integer code, String message) {
        return new Transit<>(false, code, message, null);
    }

    // 信息、代码、数据中转
    public static <T> Transit<T> success(Integer code, String message, T data) {
        return new Transit<>(true, code, message, data);
    }

    public static <T> Transit<T> failure(Integer code, String message, T data) {
        return new Transit<>(false, code, message, data);
    }

}