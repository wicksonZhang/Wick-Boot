package com.wick.boot.module.monitor.model.vo;

import com.wick.boot.common.core.model.vo.CommonPageParamVO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

/**
 * 在线用户-分页查询参数
 *
 * @author Wickson
 * @date 2024-10-25
 */
@Setter
@Getter
@ToString
@ApiModel(value = "MonitorOnlineQueryVO", description = "在线用户记录分页查询参数")
public class MonitorOnlineQueryVO extends CommonPageParamVO implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "登录名称")
    private String username;

    @ApiModelProperty(value = "登录地址")
    private String loginAddress;

}
