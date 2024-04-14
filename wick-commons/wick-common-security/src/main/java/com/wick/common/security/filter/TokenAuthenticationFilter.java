package com.wick.common.security.filter;

import cn.hutool.core.convert.Convert;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import com.wick.common.core.constant.GlobalCacheConstants;
import com.wick.common.core.constant.GlobalSystemConstants;
import com.wick.common.core.enums.ResultCodeSystem;
import com.wick.common.redis.service.RedisService;
import com.wick.module.system.model.dto.LoginUserInfoDTO;
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
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Token 校验过滤器
 */
public class TokenAuthenticationFilter extends OncePerRequestFilter {

    @Resource
    private RedisService redisService;

    @Override
    @SuppressWarnings("NullableProblems")
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        String token = request.getHeader(HttpHeaders.AUTHORIZATION);
        // 验证 Token 是否正确
        if (StrUtil.isNotBlank(token)) {
            /* Step-1: 解析 token */
            // 替换掉 Token type
            String bearer = GlobalSystemConstants.TOKEN_TYPE_BEARER;
            if (token.startsWith(bearer)) {
                token = token.replace(bearer, "").trim();
            }
            String accessToken = GlobalCacheConstants.getLoginAccessToken(token);
            LoginUserInfoDTO loginUserInfoDTO = redisService.getCacheObject(accessToken);
            if (loginUserInfoDTO == null) {
//                ResponseUtils.writeErrMsg(response, ResultCodeSystem.AUTH_TOKEN_INVALID);
                // 设置响应的内容类型和字符编码
                response.setContentType("application/json");
                ServletOutputStream outputStream = response.getOutputStream();
                // 生成相应的失败结果，并转换成 JSON 格式写入到响应中
                outputStream.write(JSONUtil.toJsonStr(ResultCodeSystem.AUTH_TOKEN_INVALID).getBytes(StandardCharsets.UTF_8));
                outputStream.flush();
                outputStream.close();
            } else {
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