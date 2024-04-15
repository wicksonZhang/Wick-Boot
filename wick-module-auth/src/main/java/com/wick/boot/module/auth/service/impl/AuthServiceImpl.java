package com.wick.boot.module.auth.service.impl;

import cn.hutool.captcha.CaptchaUtil;
import cn.hutool.captcha.GifCaptcha;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.ObjUtil;
import cn.hutool.core.util.StrUtil;
import com.wick.boot.module.auth.constant.CaptchaConstants;
import com.wick.boot.module.auth.enums.ErrorCodeAuth;
import com.wick.boot.module.auth.service.IAuthService;
import com.wick.boot.common.core.constant.GlobalCacheConstants;
import com.wick.boot.common.core.constant.GlobalConstants;
import com.wick.boot.common.core.enums.CommonStatusEnum;
import com.wick.boot.common.core.exception.ServiceException;
import com.wick.boot.common.redis.service.RedisService;
import com.wick.boot.module.system.api.ApiSystemUser;
import com.wick.boot.module.system.model.dto.AuthUserLoginRespDTO;
import com.wick.boot.module.system.model.dto.CaptchaImageRespDTO;
import com.wick.boot.module.system.model.dto.LoginUserInfoDTO;
import com.wick.boot.module.system.model.vo.AuthUserLoginReqVO;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.concurrent.TimeUnit;

/**
 * @author ZhangZiHeng
 * @date 2024-04-08
 */
@Service
public class AuthServiceImpl implements IAuthService {

    @Value("${captcha.enable:false}")
    private Boolean enable;

    @Resource
    private RedisService redisService;

    @Resource
    private ApiSystemUser systemUser;

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

        /* Step-2: 验证用户名和密码 */
        LoginUserInfoDTO userInfoDTO = systemUser.getUserByName(reqVO.getUsername());
        this.validUserInfo(userInfoDTO, reqVO.getPassword());

        /* Step-3: 存入Redis，创建Token */
        String accessTokenKey = IdUtil.fastSimpleUUID();
        String accessToken = GlobalCacheConstants.getLoginAccessToken(accessTokenKey);
        redisService.setCacheObject(accessToken, userInfoDTO, GlobalConstants.EXPIRATION_TIME, TimeUnit.SECONDS);

        return AuthUserLoginRespDTO.builder()
                .accessToken(accessTokenKey)
                .build();
    }

    /**
     * 校验验证码
     *
     * @param captchaKey  验证码Key
     * @param captchaCode 验证码Code
     */
    private void validateCaptcha(String captchaKey, String captchaCode) {
        /* Step-1：判断是否开启验证码 */
        if (!Boolean.TRUE.equals(enable)) {
            return;
        }
        // 校验验证码Key是否存在
        String redisKey = GlobalCacheConstants.getCaptchaCodeKey(captchaKey);
        String verifyCode = redisService.getCacheObject(redisKey);
        if (StrUtil.isBlankIfStr(verifyCode)) {
            throw ServiceException.getInstance(ErrorCodeAuth.AUTH_CAPTCHA_CODE_ERROR);
        }
        // 验证码Code不能为空 || 验证码Code是否正确
        if (StrUtil.isBlankIfStr(captchaCode) || !captchaCode.equalsIgnoreCase(verifyCode)) {
            redisService.deleteObject(redisKey);
            throw ServiceException.getInstance(ErrorCodeAuth.AUTH_CAPTCHA_CODE_ERROR);
        }
        // 验证成功之后删除验证码
        redisService.deleteObject(redisKey);
    }

    /**
     * 验证用户信息
     *
     * @param userInfoDTO 用户信息
     * @param password    密码
     */
    protected void validUserInfo(LoginUserInfoDTO userInfoDTO, String password) {
        // 当前用户是否存在
        if (ObjUtil.isNull(userInfoDTO)) {
            throw ServiceException.getInstance(ErrorCodeAuth.AUTH_USER_PASSWORD_ERROR);
        }
        // 当前用户密码是否正确
        if (!systemUser.isPasswordMatch(password, userInfoDTO.getPassword())) {
            throw ServiceException.getInstance(ErrorCodeAuth.AUTH_USER_PASSWORD_ERROR);
        }
        // 是否被禁用
        if (CommonStatusEnum.DISABLE.getValue().equals(userInfoDTO.getStatus())) {
            throw ServiceException.getInstance(ErrorCodeAuth.AUTH_USER_STATUS_DISABLE);
        }
    }

}
