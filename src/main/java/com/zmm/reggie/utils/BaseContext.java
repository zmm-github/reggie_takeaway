package com.zmm.reggie.utils;

/**
 * 基于ThreadLocal封装的工具类，用于保存和获取当前登录员工的id
 * 作用范围为某一个线程之内
 */
public class BaseContext {
    private static ThreadLocal<Long> threadLocal = new ThreadLocal<>();

    public static void setCurrentId(Long id) {
        threadLocal.set(id);
    }

    public static Long getCurrentId() {
        return threadLocal.get();
    }
}
