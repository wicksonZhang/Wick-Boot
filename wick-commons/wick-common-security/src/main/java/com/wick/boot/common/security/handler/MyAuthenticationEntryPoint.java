package com.wick.boot.common.security.handler;

import com.wick.boot.common.core.constant.GlobalResultCodeConstants;
import com.wick.boot.common.security.util.ResponseUtils;
import lombok.extern.slf4j.Slf4j;
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
        ResponseUtils.writeErrMsg(response, GlobalResultCodeConstants.UNAUTHORIZED);
    }

}