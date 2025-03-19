package com.wick.boot.module.auth.service;


import com.wick.boot.module.system.model.dto.AuthUserLoginRespDTO;
import com.wick.boot.module.system.model.dto.CaptchaImageRespDTO;
import com.wick.boot.module.system.model.vo.AuthUserLoginReqVO;

/**
 * 后台管理 - 认证中心
 *
 * @author Wickson
 * @date 2024-04-08
 */
public interface IAuthService {

    /**
     * 获取验证码信息
     *
     * @return CaptchaImageRespDTO
     */
    CaptchaImageRespDTO getCaptchaImage();

    /**
     * 用户登录信息
     *
     * @param reqVO 登录参数信息
     * @return AuthUserLoginRespDTO
     */
    AuthUserLoginRespDTO login(AuthUserLoginReqVO reqVO);

    /**
     * 刷新token
     *
     * @param refreshToken 刷新token
     * @return
     */
    AuthUserLoginRespDTO refreshToken(String refreshToken);

    /**
     * 注销用户
     *
     * @param token token
     */
    void logout(String token);

}
