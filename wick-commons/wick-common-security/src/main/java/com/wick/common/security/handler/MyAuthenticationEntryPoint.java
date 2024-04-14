package com.wick.common.security.handler;

import cn.hutool.json.JSONUtil;
import com.wick.common.core.constant.GlobalResultCodeConstants;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

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
        // 设置响应的内容类型和字符编码
        response.setContentType("application/json");
        ServletOutputStream outputStream = response.getOutputStream();
        // 生成相应的失败结果，并转换成 JSON 格式写入到响应中
        outputStream.write(JSONUtil.toJsonStr(GlobalResultCodeConstants.UNAUTHORIZED).getBytes(StandardCharsets.UTF_8));
        outputStream.flush();
        outputStream.close();
    }

}