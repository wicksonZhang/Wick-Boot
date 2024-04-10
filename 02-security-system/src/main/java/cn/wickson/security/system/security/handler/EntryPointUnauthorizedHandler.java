package cn.wickson.security.system.security.handler;

import cn.wickson.security.commons.constant.GlobalResultCodeConstants;
import cn.wickson.security.commons.result.ResponseUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
@Component
public class EntryPointUnauthorizedHandler implements AuthenticationEntryPoint {

    /**
     * @param request
     * @param response
     * @param authException
     * @throws IOException
     */
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException {
        ResponseUtils.writeErrMsg(response, GlobalResultCodeConstants.ACCESS_UNAUTHORIZED_CODE);
    }

}