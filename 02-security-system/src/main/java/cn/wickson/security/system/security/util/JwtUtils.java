package cn.wickson.security.system.security.util;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.jwt.JWT;
import cn.hutool.jwt.JWTPayload;
import cn.hutool.jwt.JWTUtil;
import cn.wickson.security.commons.constant.GlobalSystemConstants;
import cn.wickson.security.system.constants.JWTClaimConstants;
import cn.wickson.security.system.security.model.SystemUserDetails;
import com.google.common.collect.Maps;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

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
        payload.put(JWTClaimConstants.DEPT_ID, userDetails.getDeptId());
        // 角色信息
        Set<String> roles = userDetails.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toSet());
        payload.put(JWTClaimConstants.AUTHORITIES, roles);

        // 生成时间
        Date now = new Date();
        Date expirationDate = DateUtil.offsetSecond(now, expiration);
        payload.put(JWTPayload.ISSUED_AT, now);
        // 过期时间
        payload.put(JWTPayload.EXPIRES_AT, expirationDate);
        // 主体
        payload.put(JWTPayload.SUBJECT, authentication.getName());
        // jwt的唯一身份标识，主要用来作为一次性 token ,从而回避重放攻击
        payload.put(JWTPayload.JWT_ID, IdUtil.simpleUUID());

        // 创建 Token
        return JWTUtil.createToken(payload, secret.getBytes());
    }

    /**
     * Http请求头中的Authorization为什么要添加Bearer？
     * <p> 这可能就是乌龟的屁股，规定吧 </p>
     * <a href="https://www.lizc.net/blog/articles/63170882825ab8414ea333a6">Http请求头中的Authorization为什么要添加Bearer？</a>
     *
     * @param token token 口令
     * @return
     */
    public Map<String, Object> parseToken(String token) {
        if (StrUtil.isBlank(token)) {
            return null;
        }
        // 替换掉 Token type
        String bearer = GlobalSystemConstants.TOKEN_TYPE_BEARER;
        if (token.startsWith(bearer)) {
            token = token.replace(bearer, "").trim();
        }
        // 验证JWT是否有效，验证包括：Token是否正确、生效时间不能晚于当前时间、失效时间不能早于当前时间、签发时间不能晚于当前时间
        JWT jwt = JWTUtil.parseToken(token);
        if (jwt.setKey(secret.getBytes()).validate(0)) {
            return jwt.getPayloads();
        }
        return null;
    }

}
