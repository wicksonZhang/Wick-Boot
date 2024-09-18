package com.wick.boot.module.system.model.dto.logger.login;

import com.alibaba.excel.annotation.ExcelProperty;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * 后台管理-登录日志DTO
 *
 * @author Wickson
 * @date 2024-06-26
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SystemLoginLogDTO {

    @ApiModelProperty(value = "日志编号", example = "1024")
    @ExcelProperty("日志主键")
    private Long id;

    @ApiModelProperty(value = "日志类型，参见 LoginLogTypeEnum 枚举类", example = "1")
    private Integer logType;

    @ApiModelProperty(value = "用户编号", example = "666")
    private Long userId;

    @ApiModelProperty(value = "用户账号", example = "wick")
    @ExcelProperty("用户账号")
    private String userName;

    @ApiModelProperty(value = "用户 IP", example = "127.0.0.1")
    @ExcelProperty("登录 IP")
    private String userIp;

    @ApiModelProperty(value = "登录地点", example = "四川省 成都市")
    private String loginLocation;

    @ApiModelProperty(value = "登录结果，参见 LoginResultEnum 枚举类", example = "1")
    private Integer result;

    @ApiModelProperty(value = "浏览器 UserAgent", example = "Mozilla/5.0")
    @ExcelProperty("浏览器 UA")
    private String userAgent;

    @ApiModelProperty(value = "操作系统", example = "Windows 10")
    private String os;

    @ApiModelProperty(value = "登录时间", example = "2024-06-25 10:00:00")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @ExcelProperty("登录时间")
    private LocalDateTime createTime;

}
