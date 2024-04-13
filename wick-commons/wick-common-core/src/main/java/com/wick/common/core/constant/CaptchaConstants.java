package com.wick.common.core.constant;

/**
 * 验证码参数信息
 *
 * @author ZhangZiHeng
 * @date 2024-03-27
 */
public interface CaptchaConstants {

    /**
     * 验证码宽
     */
    Integer CAPTCHA_WIDTH = 120;

    /**
     * 验证码高
     */
    Integer CAPTCHA_HEIGHT = 40;

    /**
     * 验证码位数
     */
    Integer CAPTCHA_COUNT = 4;

    /**
     * 验证码超时时间
     */
    Long CAPTCHA_TIME_OUT = 600L;

    /**
     * 验证码key
     */
    String CAPTCHA_KEY = "captchaKey";

    /**
     * 验证码
     */
    String CAPTCHA_CODE = "captchaCode";

}
