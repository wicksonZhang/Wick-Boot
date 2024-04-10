package cn.wickson.security.system.filter;

import cn.hutool.core.util.StrUtil;
import cn.wickson.security.system.security.util.JwtUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Token 校验过滤器
 */
public class TokenAuthenticationFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        String token = request.getParameter(HttpHeaders.AUTHORIZATION);
        // 验证 Token 是否正确
        if (StrUtil.isNotBlank(token)) {
            /* Step-1: 解析 token */
            JwtUtils.parseToken(token);
        }
        filterChain.doFilter(request, response);
    }

}