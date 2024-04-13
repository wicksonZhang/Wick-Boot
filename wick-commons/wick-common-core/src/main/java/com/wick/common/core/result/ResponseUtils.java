package com.wick.common.core.result;

/**
 * 用户 认证、授权 响应结果集
 */
//public class ResponseUtils {
//
//    /**
//     * 向 HttpServletResponse 写入错误消息
//     *
//     * @param response   HttpServletResponse 对象
//     * @param resultCode 错误结果码
//     * @throws IOException 如果发生 IO 错误
//     */
//    public static void writeErrMsg(HttpServletResponse response, ResultCode resultCode) throws IOException {
//        // 设置响应的内容类型和字符编码
//        response.setContentType("application/json");
//        ServletOutputStream outputStream = response.getOutputStream();
//        // 生成相应的失败结果，并转换成 JSON 格式写入到响应中
//        outputStream.write(JSONUtil.toJsonStr(resultCode).getBytes(StandardCharsets.UTF_8));
//        outputStream.flush();
//        outputStream.close();
//    }
//
//}