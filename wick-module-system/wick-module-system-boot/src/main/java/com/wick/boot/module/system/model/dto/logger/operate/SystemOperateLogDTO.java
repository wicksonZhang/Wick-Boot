package com.wick.boot.module.system.model.dto.logger.operate;

import com.alibaba.excel.annotation.ExcelProperty;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.Map;

/**
 * 后台管理-操作日志DTO
 *
 * @author ZhangZiHeng
 * @date 2024-07-01
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class SystemOperateLogDTO {

    @ApiModelProperty(value = "日志编号",  example = "1024")
    @ExcelProperty("日志编号")
    private Long id;

    @ApiModelProperty(value = "链路追踪编号", example = "89aca178-a370-411c-ae02-3f0d672be4ab")
    private String traceId;

    @ApiModelProperty(value = "用户编号", example = "1024")
    @NotNull(message = "用户编号不能为空")
    private Long userId;

    @ApiModelProperty(value = "用户昵称", example = "1024")
    private String userNickname;

    @ApiModelProperty(value = "操作模块", example = "订单")
    @NotEmpty(message = "操作模块不能为空")
    private String module;

    @ApiModelProperty(value = "操作名", example = "创建订单")
    @NotEmpty(message = "操作名")
    private String name;

    @ApiModelProperty(value = "操作分类，参见 OperateLogTypeEnum 枚举类", example = "1")
    @NotNull(message = "操作分类不能为空")
    private Integer type;

    @ApiModelProperty(value = "操作明细", example = "修改编号为 1 的用户信息，将性别从男改成女，将姓名从芋道改成源码。")
    private String content;

    @ApiModelProperty(value = "拓展字段", example = "{'orderId': 1}")
    private Map<String, Object> exts;

    @ApiModelProperty(value = "请求方法名", example = "GET")
    @NotEmpty(message = "请求方法名不能为空")
    private String requestMethod;

    @ApiModelProperty(value = "请求地址", example = "/xxx/yyy")
    @NotEmpty(message = "请求地址不能为空")
    private String requestUrl;

    @ApiModelProperty(value = "用户 IP", example = "127.0.0.1")
    @NotEmpty(message = "用户 IP 不能为空")
    private String userIp;

    @ApiModelProperty(value = "浏览器 UserAgent", example = "Mozilla/5.0")
    @NotEmpty(message = "浏览器 UserAgent 不能为空")
    private String userAgent;

    @ApiModelProperty(value = "Java 方法名", example = "")
    @NotEmpty(message = "Java 方法名不能为空")
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
