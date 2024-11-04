package com.zmm.reggie.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @program: reggie
 * @description: 返回结果类
 * @author: zmm
 * @create: 2024-09-23 17:30
 * @version:1.0
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResultVo<T> {
    private Integer code;
    private String msg;
    private T data;

    public static <T> ResultVo<T> success() {
        return new ResultVo<>(20000, "success", null);
    }
    /* 以下对success方法进行重载 */
    public static <T> ResultVo<T> success(T data) {
        return new ResultVo<>(20000, "success", data);
    }

    public static <T> ResultVo<T> success(String msg, T data) {
        return new ResultVo<>(20000, msg, data);
    }

    public static <T> ResultVo<T> success(String msg) {
        return new ResultVo<>(20000, msg, null);
    }

    public static <T> ResultVo<T> fail() {
        return new ResultVo<>(20001, "fail", null);
    }
    /* 以下对fail方法进行重载 */
    public static <T> ResultVo<T> fail(String msg) {
        return new ResultVo<>(20001, msg, null);
    }

    public static <T> ResultVo<T> fail(Integer code, String msg) {
        return new ResultVo<>(code, msg, null);
    }

    public static <T> ResultVo<T> fail(Integer code) {
        return new ResultVo<>(code, "fail", null);
    }
}
