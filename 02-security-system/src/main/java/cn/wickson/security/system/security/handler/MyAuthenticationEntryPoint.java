package cn.wickson.security.system.security.handler;

import cn.wickson.security.commons.result.ResponseUtils;
import cn.wickson.security.system.enums.ResultCodeSystem;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 认证异常处理
 */
@Slf4j
@Component
public class MyAuthenticationEntryPoint implements AuthenticationEntryPoint {

    /**
     * 当未经授权的请求到达时，此方法将被调用，用于处理未经授权的请求。
     *
     * @param request       HTTP 请求
     * @param response      HTTP 响应
     * @param authException AuthenticationException 对象，表示身份验证异常
     * @throws IOException IO异常
     */
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException {
        if (authException instanceof BadCredentialsException) {
            // 用户名或密码错误
            ResponseUtils.writeErrMsg(response, ResultCodeSystem.AUTH_USER_PASSWORD_ERROR);
        } else if (authException instanceof DisabledException) {
            // 当前账号已被停用
            ResponseUtils.writeErrMsg(response, ResultCodeSystem.AUTH_USER_STATUS_DISABLE);
        } else {
            // 未认证或者token过期
            ResponseUtils.writeErrMsg(response, ResultCodeSystem.AUTH_TOKEN_INVALID);
        }
    }

}