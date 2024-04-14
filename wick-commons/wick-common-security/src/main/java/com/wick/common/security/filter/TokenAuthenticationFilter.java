package com.wick.common.security.filter;

import cn.hutool.core.convert.Convert;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONUtil;
import cn.hutool.jwt.JWTPayload;
import com.wick.common.core.enums.ResultCodeSystem;
import com.wick.common.core.model.dto.LoginUserInfoDTO;
import com.wick.common.core.model.userdetails.LoginUserDetails;
import com.wick.common.security.constants.JWTClaimConstants;
import com.wick.common.security.util.JwtUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.annotation.Resource;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Token 校验过滤器
 */
public class TokenAuthenticationFilter extends OncePerRequestFilter {

    /**
     * JWT的工具类
     */
    @Resource
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
                Authentication authentication = this.getAuthentication(payload);
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        }
        filterChain.doFilter(request, response);
    }

    private Authentication getAuthentication(Map<String, Object> payload) {
        LoginUserDetails userDetails = new LoginUserDetails();
        // 用户ID
        userDetails.setUserId(Convert.toLong(payload.get(JWTClaimConstants.USER_ID)));
        // 部门ID
        userDetails.setDeptId(Convert.toLong(payload.get(JWTClaimConstants.DEPT_ID)));
        // 用户名称
        userDetails.setUsername(Convert.toStr(payload.get(JWTPayload.SUBJECT)));
        // 角色信息
        Set<SimpleGrantedAuthority> authorities = ((JSONArray) payload.get(JWTClaimConstants.AUTHORITIES))
                .stream()
                .map(authority -> new SimpleGrantedAuthority(Convert.toStr(authority)))
                .collect(Collectors.toSet());
        return new UsernamePasswordAuthenticationToken(userDetails, "", authorities);
    }


}