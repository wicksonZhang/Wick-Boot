package com.wick.common.core.constant;

public interface GlobalCacheConstants {

    /**
     * 验证码 redis key
     */
    String CAPTCHA_CODE_KEY = "CAPTCHA_CODES:%s";

    /**
     * 登录Token
     */
    String LOGIN_ACCESS_TOKEN = "LOGIN_ACCESS_TOKE:%s";

    /**
     * 角色-权限 key
     */
    String ROLE_PERMS_KEY = "ROLE_PERMS:%s";

    /**
     * 获取验证码Code
     *
     * @param key key
     * @return String
     */
    static String getCaptchaCodeKey(String key) {
        return String.format(GlobalCacheConstants.CAPTCHA_CODE_KEY, key);
    }

    /**
     * 获取登录Token
     *
     * @param key key
     * @return String
     */
    static String getLoginAccessToken(String key) {
        return String.format(GlobalCacheConstants.LOGIN_ACCESS_TOKEN, key);
    }


    /**
     * 获取角色权限索引 Key
     *
     * @param key key
     * @return String
     */
    static String getRolePermsKey(String key) {
        return String.format(GlobalCacheConstants.ROLE_PERMS_KEY, key);
    }
}
