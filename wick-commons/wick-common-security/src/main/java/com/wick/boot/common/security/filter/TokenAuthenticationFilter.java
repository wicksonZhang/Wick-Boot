package com.wick.boot.common.security.filter;

import cn.hutool.core.convert.Convert;
import cn.hutool.core.util.StrUtil;
import com.wick.boot.common.core.constant.GlobalCacheConstants;
import com.wick.boot.common.redis.service.RedisService;
import com.wick.boot.module.system.model.dto.LoginUserInfoDTO;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.annotation.Resource;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Token 校验过滤器
 */
public class TokenAuthenticationFilter extends OncePerRequestFilter {

    // 系统 Token 类型
    private static final String TOKEN_TYPE_BEARER = "Bearer ";

    @Resource
    private RedisService redisService;

    @Override
    @SuppressWarnings("NullableProblems")
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        String token = request.getHeader(HttpHeaders.AUTHORIZATION);
        /* Step-1: 解析 token */
        if (StrUtil.isNotBlank(token)) {
            // 替换掉 Token type
            if (token.startsWith(TOKEN_TYPE_BEARER)) {
                token = token.replace(TOKEN_TYPE_BEARER, "").trim();
            }
            // 获取 Token, 校验 Token 是否正确
            String accessToken = GlobalCacheConstants.getLoginAccessToken(token);
            LoginUserInfoDTO loginUserInfoDTO = redisService.getCacheObject(accessToken);
            if (loginUserInfoDTO != null) {
                /* Step-2: 获取用户信息， 存入 SecurityContextHolder  */
                Authentication authentication = this.getAuthentication(loginUserInfoDTO, request);
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        }
        filterChain.doFilter(request, response);
    }

    private Authentication getAuthentication(LoginUserInfoDTO loginUserInfoDTO, HttpServletRequest request) {
        // 角色信息
        Set<SimpleGrantedAuthority> authorities = loginUserInfoDTO.getRoles()
                .stream()
                .map(authority -> new SimpleGrantedAuthority(Convert.toStr(authority)))
                .collect(Collectors.toSet());
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(loginUserInfoDTO, "",
                authorities);
        authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
        return authenticationToken;
    }


}