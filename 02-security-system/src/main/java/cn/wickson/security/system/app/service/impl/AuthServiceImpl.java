package cn.wickson.security.system.app.service.impl;

import cn.hutool.captcha.CaptchaUtil;
import cn.hutool.captcha.GifCaptcha;
import cn.hutool.core.util.IdUtil;
import cn.wickson.security.system.app.service.AbstractAuthAppService;
import cn.wickson.security.system.app.service.IAuthService;
import cn.wickson.security.system.constants.CaptchaConstants;
import cn.wickson.security.system.model.dto.AuthUserLoginRespDTO;
import cn.wickson.security.system.model.dto.CaptchaImageRespDTO;
import cn.wickson.security.system.model.entity.SystemUser;
import cn.wickson.security.system.model.vo.AuthUserLoginReqVO;
import cn.wickson.security.system.security.util.JwtUtils;
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
public class AuthServiceImpl extends AbstractAuthAppService implements IAuthService {

    @Resource
    private AuthenticationManager authenticationManager;

    @Resource
    private JwtUtils jwtUtils;

    @Override
    public CaptchaImageRespDTO getCaptchaImage() {
        /* Step-1：判断是否开启验证码 */
        if (!Boolean.TRUE.equals(super.enable)) {
            return CaptchaImageRespDTO.builder().enable(super.enable).build();
        }

        /* Step-2: 生成验证码信息 */
        GifCaptcha captcha =
                CaptchaUtil.createGifCaptcha(CaptchaConstants.CAPTCHA_WIDTH, CaptchaConstants.CAPTCHA_HEIGHT, CaptchaConstants.CAPTCHA_COUNT);

        /* Step-3: 将验证码存入redis */
        String captchaKey = IdUtil.fastSimpleUUID();
        String redisKey = super.getCaptchaCodeKey(captchaKey);
        redisService.setCacheObject(redisKey, captcha.getCode(), CaptchaConstants.CAPTCHA_TIME_OUT, TimeUnit.SECONDS);

        /* Step-4: 返回结果集 */
        return CaptchaImageRespDTO.getInstance(enable, captchaKey, captcha.getImageBase64Data());
    }

    @Override
    public AuthUserLoginRespDTO login(AuthUserLoginReqVO reqVO) {
        /* Step-1: 参数验证 */
        // 验证验证码
        // this.validCaptcha(reqVO.getCaptchaKey(), reqVO.getCaptchaCode());

        // 验证用户信息
//        SystemUser systemUser = userService.getUserByName(reqVO.getUsername());
//        this.validUserInfo(systemUser, reqVO.getPassword(), reqVO.getCaptchaKey());

        /* Step2: Security 认证 */
        Authentication token = new UsernamePasswordAuthenticationToken(reqVO.getUsername(), reqVO.getPassword());
        Authentication authentication = authenticationManager.authenticate(token);
        String accessToken = jwtUtils.generateToken(authentication);
        return AuthUserLoginRespDTO.builder().accessToken(accessToken).tokenType("Bearer").build();
    }

}
