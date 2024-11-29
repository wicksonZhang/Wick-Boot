package com.wick.boot.module.auth.constant;

/**
 * 验证码参数信息
 *
 * @author Wickson
 * @date 2024-03-27
 */
public interface CaptchaConstants {

    /**
     * 验证码宽度
     */
    Integer CAPTCHA_WIDTH = 120;

    /**
     * 验证码高度
     */
    Integer CAPTCHA_HEIGHT = 40;

    /**
     * 验证码字符长度
     */
    Integer CAPTCHA_CODE_LENGTH = 1;

    /**
     * 验证码干扰元素个数
     */
    Integer CAPTCHA_INTERFERE_COUNT = 2;

    /**
     * 文本透明度
     */
    Float CAPTCHA_TEXT_ALPHA = 0.8f;

    /**
     * 字体名称
     */
    String CAPTCHA_FONT_NAME = "SansSerif";

    /**
     * 字体样式
     */
    Integer CAPTCHA_FONT_WEIGHT = 1;

    /**
     * 字体大小
     */
    Integer CAPTCHA_FONT_SIZE = 24;

    /**
     * 验证码超时时间
     */
    Long CAPTCHA_EXPIRE_SECONDS = 120L;

}
