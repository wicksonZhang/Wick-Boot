package com.wick.common.security.util;

import cn.hutool.json.JSONUtil;
import com.wick.common.core.result.ResultCode;
import org.springframework.http.MediaType;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

/**
 * 响应工具类
 */
public class ResponseUtils {

    /**
     * 异常消息返回(适用过滤器中处理异常响应)
     *
     * @param response   Http响应
     * @param resultCode 结果集
     */
    public static void writeErrMsg(HttpServletResponse response, ResultCode resultCode) throws IOException {
        // 设置响应的内容类型和字符编码
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        ServletOutputStream outputStream = response.getOutputStream();
        // 生成相应的失败结果，并转换成 JSON 格式写入到响应中
        outputStream.write(JSONUtil.toJsonStr(resultCode).getBytes(StandardCharsets.UTF_8));
        outputStream.flush();
        outputStream.close();
    }

}