package cn.wickson.security.system.security.util;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.IdUtil;
import cn.hutool.jwt.JWTPayload;
import cn.hutool.jwt.JWTUtil;
import cn.wickson.security.system.constants.JWTClaimConstants;
import cn.wickson.security.system.security.model.SystemUserDetails;
import com.google.common.collect.Maps;
import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.Map;

/**
 * 通过 HuTool 创建 Token
 */
@Data
@Component
@ConfigurationProperties(prefix = "jwt")
public class JwtUtils {

    /**
     * 密钥
     */
    private String secret;

    /**
     * 过期时间
     */
    private int expiration;

    /**
     * 生成 JWT Token
     *
     * @param authentication 用户认证信息
     * @return Token 字符串
     */
    public String generateToken(Authentication authentication) {
        SystemUserDetails userDetails = (SystemUserDetails) authentication.getPrincipal();
        // 载体
        Map<String, Object> payload = Maps.newHashMap();
        // 用户ID
        payload.put(JWTClaimConstants.USER_ID, userDetails.getUserId());
        // 部门ID
        payload.put(JWTClaimConstants.DEPT_ID, userDetails.getUsername());
        // 角色信息
        payload.put(JWTClaimConstants.AUTHORITIES, userDetails.getAuthorities());
        // 生成时间
        payload.put(JWTPayload.ISSUED_AT, new Date());
        // 过期时间
        payload.put(JWTPayload.EXPIRES_AT, new Date(System.currentTimeMillis() + expiration));
        payload.put(JWTPayload.SUBJECT, authentication.getName());
        payload.put(JWTPayload.JWT_ID, IdUtil.simpleUUID());

        // 创建 Token
        return JWTUtil.createToken(payload, secret.getBytes());
    }

    public static String parseToken(String token) {
        return null;
    }

}
