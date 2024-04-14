package com.wick.module.auth.service.impl;

import cn.hutool.captcha.CaptchaUtil;
import cn.hutool.captcha.GifCaptcha;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.StrUtil;
import com.wick.module.auth.service.IAuthService;
import com.wick.module.system.model.dto.AuthUserLoginRespDTO;
import com.wick.module.system.model.dto.CaptchaImageRespDTO;
import com.wick.module.system.model.vo.AuthUserLoginReqVO;
import com.wick.common.core.constant.CaptchaConstants;
import com.wick.common.core.constant.GlobalCacheConstants;
import com.wick.common.core.constant.GlobalSystemConstants;
import com.wick.common.core.enums.ResultCodeSystem;
import com.wick.common.core.exception.ServiceException;
import com.wick.common.redis.service.RedisService;
import com.wick.common.security.util.JwtUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.concurrent.TimeUnit;

/**
 * @author ZhangZiHeng
 * @date 2024-04-08
 */
@Service
public class AuthServiceImpl implements IAuthService {

    @Value("${captcha.enable:true}")
    private Boolean enable;

    @Resource
    private RedisService redisService;

    @Resource
    private AuthenticationManager authenticationManager;

    @Resource
    private JwtUtils jwtUtils;

    @Override
    public CaptchaImageRespDTO getCaptchaImage() {
        /* Step-1：判断是否开启验证码 */
        if (!Boolean.TRUE.equals(enable)) {
            return CaptchaImageRespDTO.builder().enable(enable).build();
        }

        /* Step-2: 生成验证码信息 */
        GifCaptcha captcha =
                CaptchaUtil.createGifCaptcha(CaptchaConstants.CAPTCHA_WIDTH, CaptchaConstants.CAPTCHA_HEIGHT, CaptchaConstants.CAPTCHA_COUNT);

        /* Step-3: 将验证码存入redis */
        String captchaKey = IdUtil.fastSimpleUUID();
        String redisKey = GlobalCacheConstants.getCaptchaCodeKey(captchaKey);
        redisService.setCacheObject(redisKey, captcha.getCode(), CaptchaConstants.CAPTCHA_TIME_OUT, TimeUnit.SECONDS);

        /* Step-4: 返回结果集 */
        return CaptchaImageRespDTO.getInstance(enable, captchaKey, captcha.getImageBase64Data());
    }

    @Override
    public AuthUserLoginRespDTO login(AuthUserLoginReqVO reqVO) {
        /* Step-1: 验证验证码信息 */
        this.validateCaptcha(reqVO.getCaptchaKey(), reqVO.getCaptchaCode());

        /* Step-2: 认证、授权 */
        Authentication token = new UsernamePasswordAuthenticationToken(reqVO.getUsername(), reqVO.getPassword());
        Authentication authenticate = authenticationManager.authenticate(token);
        String accessToken = jwtUtils.generateToken(authenticate);
        return AuthUserLoginRespDTO.builder().accessToken(accessToken).tokenType(GlobalSystemConstants.TOKEN_TYPE_BEARER).build();
    }

    private void validateCaptcha(String captchaKey, String captchaCode) {
        // 校验验证码Key是否存在
        String redisKey = GlobalCacheConstants.getCaptchaCodeKey(captchaKey);
        String verifyCode = redisService.getCacheObject(redisKey);
        if (StrUtil.isBlankIfStr(verifyCode)) {
            throw ServiceException.getInstance(ResultCodeSystem.AUTH_CAPTCHA_CODE_ERROR);
        }
        // 验证码Code不能为空 || 验证码Code是否正确
        if (StrUtil.isBlankIfStr(captchaCode) || !captchaCode.equalsIgnoreCase(verifyCode)) {
            redisService.deleteObject(redisKey);
            throw ServiceException.getInstance(ResultCodeSystem.AUTH_CAPTCHA_CODE_ERROR);
        }
        // 验证成功之后删除验证码
        redisService.deleteObject(redisKey);


    }

}
