package com.wick.boot.module.system.model.dto;

import com.alibaba.excel.annotation.ExcelProperty;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * 后台管理-登录日志DTO
 *
 * @author ZhangZiHeng
 * @date 2024-06-26
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class SystemLoginLogDTO {

    @ApiModelProperty(value = "日志编号", example = "1024")
    @ExcelProperty("日志主键")
    private Long id;

    @ApiModelProperty(value = "日志类型，参见 LoginLogTypeEnum 枚举类", example = "1")
    private Integer logType;

    @ApiModelProperty(value = "用户编号", example = "666")
    private Long userId;

    @ApiModelProperty(value = "用户类型，参见 UserTypeEnum 枚举", example = "2")
    private Integer userType;

    @ApiModelProperty(value = "链路追踪编号", example = "89aca178-a370-411c-ae02-3f0d672be4ab")
    private String traceId;

    @ApiModelProperty(value = "用户账号", example = "yudao")
    @ExcelProperty("用户账号")
    private String username;

    @ApiModelProperty(value = "登录结果，参见 LoginResultEnum 枚举类", example = "1")
    private Integer result;

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
