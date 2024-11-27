package com.wick.boot.common.core.constant;

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
     * 字典数据
     */
    String DICT_CODE = "DICT_CODE:%s";

    /**
     * OKX市场行情
     */
    String OKX_MARKET_TICKERS = "OKX:MARKET_TICKERS:%s";

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

    /**
     * 获取字典索引 Key
     *
     * @param key key
     * @return String
     */
    static String getDictCodeKey(String key) {
        return String.format(GlobalCacheConstants.DICT_CODE, key);
    }

    /**
     * 获取OKX市场行情 Key
     *
     * @param key key
     * @return String
     */
    static String getOkxMarketTickers(String key) {
        return String.format(GlobalCacheConstants.OKX_MARKET_TICKERS, key);
    }
}
