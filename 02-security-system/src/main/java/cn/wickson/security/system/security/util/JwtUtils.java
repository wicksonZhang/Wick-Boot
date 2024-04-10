package cn.wickson.security.system.security.util;

import cn.wickson.security.system.security.model.SystemUserDetails;
import cn.wickson.security.system.security.service.SecurityUserDetails;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

@Component
public class JwtUtils {


    public static String generateToken(Authentication authentication) {
        SystemUserDetails userDetails = (SystemUserDetails) authentication.getPrincipal();

        return null;
    }

    public static String parseToken(String token) {
        return null;
    }

}
