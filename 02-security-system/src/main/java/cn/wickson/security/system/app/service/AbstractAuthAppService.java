package cn.wickson.security.system.app.service;

import cn.hutool.core.util.ObjUtil;
import cn.hutool.core.util.StrUtil;
import cn.wickson.security.commons.constant.GlobalCacheConstants;
import cn.wickson.security.commons.enums.CommonStatusEnum;
import cn.wickson.security.commons.exception.ServiceException;
import cn.wickson.security.system.plugin.redis.RedisService;
import cn.wickson.security.system.enums.ResultCodeSystem;
import cn.wickson.security.system.model.entity.SystemUser;
import org.springframework.beans.factory.annotation.Value;

import javax.annotation.Resource;

/**
 * @author ZhangZiHeng
 * @date 2024-04-08
 */
public abstract class AbstractAuthAppService {

//    @Value("${captcha.enable:true}")
//    protected Boolean enable;
//
//    @Resource
//    protected RedisService redisService;
//
//    @Resource
//    protected ISystemUserService userService;
//
//    /**
//     * 校验验证码信息
//     *
//     * @param captchaKey  验证码Key
//     * @param captchaCode 验证码
//     */
//    protected void validCaptcha(String captchaKey, String captchaCode) {
//        // 如果没有开启验证码则不进行校验
//        if (!Boolean.TRUE.equals(enable)) {
//            return;
//        }
//
//        String captchaCodeKey = getCaptchaCodeKey(captchaKey);
//        String verifyCode = redisService.getCacheObject(captchaCodeKey);
//        // 验证验证码是否存在
//        if (StrUtil.isBlankIfStr(verifyCode)) {
//            throw ServiceException.getInstance(ResultCodeSystem.AUTH_CAPTCHA_CODE_ERROR);
//        }
//
//        // 验证验证码是否正确
//        if (!captchaCode.equals(verifyCode)) {
//            redisService.deleteObject(captchaCodeKey);
//            throw ServiceException.getInstance(ResultCodeSystem.AUTH_CAPTCHA_CODE_ERROR);
//        }
//    }
//
//    /**
//     * 验证用户信息
//     *
//     * @param systemUser 用户信息
//     * @param password   密码
//     */
//    protected void validUserInfo(SystemUser systemUser, String password, String captchaKey) {
//        // 当前用户是否存在
//        if (ObjUtil.isNull(systemUser)) {
//            throw ServiceException.getInstance(ResultCodeSystem.AUTH_USER_NOT_FOUND);
//        }
//        // 当前用户密码是否正确
//        if (!password.equals(systemUser.getPassword())) {
//            throw ServiceException.getInstance(ResultCodeSystem.AUTH_USER_PASSWORD_ERROR);
//        }
//        // 是否被禁用
//        if (CommonStatusEnum.DISABLE.getValue().equals(systemUser.getStatus())) {
//            throw ServiceException.getInstance(ResultCodeSystem.AUTH_USER_STATUS_DISABLE);
//        }
//        // 删除token
//        redisService.deleteObject(getCaptchaCodeKey(captchaKey));
//    }
//
//    /**
//     * 获取验证码索引 Key
//     *
//     * @param key key
//     * @return String
//     */
//    protected String getCaptchaCodeKey(String key) {
//        return String.format(GlobalCacheConstants.CAPTCHA_CODE_KEY, key);
//    }
}
