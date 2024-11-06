package com.wick.boot.module.system.model.dto.logger.operate;

import com.alibaba.excel.annotation.ExcelProperty;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.wick.boot.common.log.enums.OperateLogTypeEnum;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

/**
 * 后台管理-操作日志DTO
 *
 * @author Wickson
 * @date 2024-07-01
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SystemOperateLogDTO {

    @ApiModelProperty(value = "日志编号", example = "1024")
    @ExcelProperty("日志编号")
    private Long id;

    @ApiModelProperty(value = "用户编号", example = "1024")
    @NotNull(message = "用户编号不能为空")
    private Long userId;

    @ApiModelProperty(value = "用户昵称", example = "1024")
    private String userName;

    @ApiModelProperty(value = "操作模块", example = "订单")
    @NotBlank(message = "操作模块不能为空")
    private String module;

    @ApiModelProperty(value = "操作名", example = "创建订单")
    @NotBlank(message = "操作名")
    private String name;

    /**
     * 操作分类查询如下枚举类
     *
     * {@link OperateLogTypeEnum }
     */
    @ApiModelProperty(value = "操作分类", example = "1")
    @NotNull(message = "操作分类不能为空")
    private Integer type;

    @ApiModelProperty(value = "请求方法名", example = "GET")
    @NotBlank(message = "请求方法名不能为空")
    private String requestMethod;

    @ApiModelProperty(value = "请求地址", example = "/xxx/yyy")
    @NotBlank(message = "请求地址不能为空")
    private String requestUrl;

    @ApiModelProperty(value = "用户 IP", example = "127.0.0.1")
    @NotBlank(message = "用户 IP 不能为空")
    private String userIp;

    @ApiModelProperty(value = "用户操作地址", example = "内网ip")
    @NotBlank(message = "用户操作地址不能为空")
    private String operateLocation;

    @ApiModelProperty(value = "浏览器 UserAgent", example = "Mozilla/5.0")
    @NotBlank(message = "浏览器 UserAgent 不能为空")
    private String userAgent;

    @ApiModelProperty(value = "Java 方法名", example = "")
    @NotBlank(message = "Java 方法名不能为空")
    private String javaMethod;

    @ApiModelProperty(value = "Java 方法的参数")
    private String javaMethodArgs;

    @ApiModelProperty(value = "开始时间")
    @NotNull(message = "开始时间不能为空")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private LocalDateTime startTime;

    @ApiModelProperty(value = "执行时长，单位：毫秒")
    @NotNull(message = "执行时长不能为空")
    private Integer duration;

    @ApiModelProperty(value = "结果码")
    @NotNull(message = "结果码不能为空")
    private Integer resultCode;

    @ApiModelProperty(value = "结果提示")
    private String resultMsg;

    @ApiModelProperty(value = "结果数据")
    private String resultData;

}
