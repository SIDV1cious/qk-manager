package com.itheima.utils;

public class UserHolder {

    private static final ThreadLocal<Integer> CURRENT_USER = new ThreadLocal<>();

    /**
     * 存储用户信息到当前线程
     *
     * @param userId
     */
    public static void setUserId(Integer userId) {
        CURRENT_USER.set(userId);
    }

    /**
     * 获取线程中的用户信息
     * @return
     */
    public static Integer getUserId() {
        return CURRENT_USER.get();
    }

    /**
     * 清空线程中的用户信息
     */
    public static void removeUserId(){
        CURRENT_USER.remove();
    }

}