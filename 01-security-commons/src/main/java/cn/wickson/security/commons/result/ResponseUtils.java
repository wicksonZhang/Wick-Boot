package cn.wickson.security.commons.result;

import cn.hutool.json.JSONUtil;
import org.springframework.http.MediaType;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

/**
 * 用户 认证、授权 响应结果集
 */
public class ResponseUtils {

    /**
     * 向 HttpServletResponse 写入错误消息
     *
     * @param response   HttpServletResponse 对象
     * @param resultCode 错误结果码
     * @throws IOException 如果发生 IO 错误
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