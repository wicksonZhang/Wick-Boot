package com.wick.common.security.handler;

import cn.hutool.json.JSONUtil;
import com.wick.common.core.constant.GlobalResultCodeConstants;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

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
        // 设置响应的内容类型和字符编码
        response.setContentType("application/json");
        ServletOutputStream outputStream = response.getOutputStream();
        // 生成相应的失败结果，并转换成 JSON 格式写入到响应中
        outputStream.write(JSONUtil.toJsonStr(GlobalResultCodeConstants.ACCESS_UNAUTHORIZED_CODE).getBytes(StandardCharsets.UTF_8));
        outputStream.flush();
        outputStream.close();
    }

}
