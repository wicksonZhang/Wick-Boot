package com.wick.boot.module.system.model.vo.logger.login;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.write.style.ColumnWidth;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * 用户登录日志
 *
 * @author Wickson
 * @date 2024-07-01
 */
@Data
@ColumnWidth(20)
public class SystemLoginLogExportVO {

    @ApiModelProperty(value = "日志编号", example = "1024")
    @ExcelProperty("日志主键")
    private Long id;

    @ApiModelProperty(value = "用户账号", example = "wick")
    @ExcelProperty("用户账号")
    private String userName;

    @ApiModelProperty(value = "用户 IP", example = "127.0.0.1")
    @ExcelProperty("登录 IP")
    private String userIp;

    @ApiModelProperty(value = "浏览器 UserAgent", example = "Mozilla/5.0")
    @ExcelProperty("浏览器 UA")
    private String userAgent;

    @ApiModelProperty(value = "登录时间", example = "2024-06-25 10:00:00")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @ExcelProperty("登录时间")
    private LocalDateTime createTime;
}
