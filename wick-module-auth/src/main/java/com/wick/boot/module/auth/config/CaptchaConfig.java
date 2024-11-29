package com.wick.boot.module.auth.config;

import cn.hutool.captcha.generator.CodeGenerator;
import cn.hutool.captcha.generator.MathGenerator;
import com.wick.boot.module.auth.constant.CaptchaConstants;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.awt.*;

/**
 * 验证码自动装配配置
 *
 * @author haoxr
 * @since 2023/11/24
 */
@Configuration
public class CaptchaConfig {

    /**
     * 验证码文字生成器
     *
     * @return CodeGenerator
     */
    @Bean
    public CodeGenerator codeGenerator() {
        return new MathGenerator(CaptchaConstants.CAPTCHA_CODE_LENGTH);
    }

    /**
     * 验证码字体
     */
    @Bean
    public Font captchaFont() {
        String fontName = CaptchaConstants.CAPTCHA_FONT_NAME;
        Integer fontSize = CaptchaConstants.CAPTCHA_FONT_SIZE;
        Integer fontWight = CaptchaConstants.CAPTCHA_FONT_WEIGHT;
        return new Font(fontName, fontWight, fontSize);
    }


}
