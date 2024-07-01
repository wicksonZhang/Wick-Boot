package com.wick.boot.module.system.model.vo.logger.login;

import com.wick.boot.common.core.model.vo.CommonPageParamVO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

/**
 * 系统管理 - 登录日志查询条件
 *
 * @author ZhangZiHeng
 * @date 2024-06-26
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "QueryLoginLogPageReqVO", description = "登录日志查询条件参数")
public class QueryLoginLogPageReqVO extends CommonPageParamVO {

    @ApiModelProperty(value = "用户名称", example = "系统管理员")
    private String username;

    @ApiModelProperty(value = "登录地址", example = "127.0.0.1")
    private String userIp;

    @ApiModelProperty(value = "开始时间", example = "[2024-06-01 00:00:00, 2024-06-01 23:59:59]")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime[] createTime;

}
