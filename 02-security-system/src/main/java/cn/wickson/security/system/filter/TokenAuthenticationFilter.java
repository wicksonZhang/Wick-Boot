package cn.wickson.security.system.filter;

import cn.hutool.core.convert.Convert;
import cn.hutool.core.util.StrUtil;
import cn.hutool.jwt.JWTPayload;
import cn.wickson.security.commons.result.ResponseUtils;
import cn.wickson.security.system.constants.JWTClaimConstants;
import cn.wickson.security.system.enums.ResultCodeSystem;
import cn.wickson.security.system.security.model.SystemUserDetails;
import cn.wickson.security.system.security.util.JwtUtils;
import com.google.common.collect.Sets;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;
import java.util.Set;

/**
 * Token 校验过滤器
 */
public class TokenAuthenticationFilter extends OncePerRequestFilter {

    /**
     * JWT的工具类
     */
    @Autowired
    private JwtUtils jwtUtils;

    @Override
    @SuppressWarnings("NullableProblems")
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        String token = request.getHeader(HttpHeaders.AUTHORIZATION);
        // 验证 Token 是否正确
        if (StrUtil.isNotBlank(token)) {
            /* Step-1: 解析 token */
            // 如果 jwtUtils.parseToken 解析不出来会直接报错
            Map<String, Object> payload = jwtUtils.parseToken(token);
            if (payload == null) {
                ResponseUtils.writeErrMsg(response, ResultCodeSystem.AUTH_TOKEN_INVALID);
            } else {
                /* Step-2: 获取用户信息， 存入 SecurityContextHolder  */
                Authentication authentication = this.getAuthentication(payload);
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        }
        filterChain.doFilter(request, response);
    }

    private Authentication getAuthentication(Map<String, Object> payload) {
        SystemUserDetails userDetails = new SystemUserDetails();
        // 用户ID
        userDetails.setUserId(Convert.toLong(payload.get(JWTClaimConstants.USER_ID)));
        // 部门ID
        userDetails.setDeptId(Convert.toLong(payload.get(JWTClaimConstants.DEPT_ID)));
        // 用户名称
        userDetails.setUsername(Convert.toStr(payload.get(JWTPayload.SUBJECT)));
        // 角色信息
        Set<SimpleGrantedAuthority> authorities = Sets.newHashSet();
        return new UsernamePasswordAuthenticationToken(userDetails, "", authorities);
    }


}