package cn.wickson.security.system.security.handler;

import cn.wickson.security.commons.constant.GlobalResultCodeConstants;
import cn.wickson.security.commons.result.ResponseUtils;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class MyAccessDeniedHandler implements AccessDeniedHandler {

    @Override
    public void handle(HttpServletRequest request,
                       HttpServletResponse response,
                       AccessDeniedException accessDeniedException) throws IOException {
        ResponseUtils.writeErrMsg(response, GlobalResultCodeConstants.ACCESS_UNAUTHORIZED_CODE);
    }

}
