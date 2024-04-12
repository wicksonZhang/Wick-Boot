package cn.wickson.security.system.filter;

import cn.hutool.core.util.StrUtil;
import cn.wickson.security.commons.constant.GlobalCacheConstants;
import cn.wickson.security.commons.result.ResponseUtils;
import cn.wickson.security.system.constants.CaptchaConstants;
import cn.wickson.security.system.enums.ResultCodeSystem;
import cn.wickson.security.system.plugin.redis.RedisService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.annotation.Resource;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 验证码过滤器
 */
public class CaptchaAuthenticationFilter extends OncePerRequestFilter {

    @Resource
    private RedisService redisService;

    @Value("${captcha.enable:true}")
    private Boolean enable;

    @Override
    @SuppressWarnings("NullableProblems")
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws IOException, ServletException {
        /* Step-1: 拦截 /login 请求，并且开启了验证码的情况 */
        if (request.getRequestURI().contains("/login") && Boolean.TRUE.equals(enable)) {
            // 获取验证码参数，进行验证
            String captchaKey = request.getParameter(CaptchaConstants.CAPTCHA_KEY);
            String captchaCode = request.getParameter(CaptchaConstants.CAPTCHA_CODE);
            // 校验验证码Key是否存在
            String redisKey = GlobalCacheConstants.getCaptchaCodeKey(captchaKey);
            String verifyCode = redisService.getCacheObject(redisKey);
            if (StrUtil.isBlankIfStr(verifyCode)) {
                ResponseUtils.writeErrMsg(response, ResultCodeSystem.AUTH_CAPTCHA_CODE_ERROR);
            }
            // 验证码Code不能为空 || 验证码Code是否正确
            if (StrUtil.isBlankIfStr(captchaCode) || !captchaCode.equalsIgnoreCase(verifyCode)) {
                redisService.deleteObject(redisKey);
                ResponseUtils.writeErrMsg(response, ResultCodeSystem.AUTH_CAPTCHA_CODE_ERROR);
            }
            // 验证成功之后删除验证码
            redisService.deleteObject(redisKey);
        }

        /* Step-2: 如果非 /login 直接放行 */
        filterChain.doFilter(request, response);
    }


}
