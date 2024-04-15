package com.wick.boot.common.security.handler;

import com.wick.boot.common.core.constant.GlobalResultCodeConstants;
import com.wick.boot.common.security.util.ResponseUtils;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 用于处理访问被拒绝的情况，实现了 Spring Security 的 AccessDeniedHandler 接口。
 */
@Component
public class MyAccessDeniedHandler implements AccessDeniedHandler {

    /**
     * 当访问被拒绝时，此方法将被调用，用于处理访问被拒绝的情况。
     *
     * @param request               http 请求
     * @param response              http 响应
     * @param accessDeniedException AccessDeniedException 对象，表示访问被拒绝的异常
     * @throws IOException IO 错误
     */
    @Override
    public void handle(HttpServletRequest request,
                       HttpServletResponse response,
                       AccessDeniedException accessDeniedException) throws IOException {
        ResponseUtils.writeErrMsg(response, GlobalResultCodeConstants.ACCESS_UNAUTHORIZED_CODE);
    }

}
