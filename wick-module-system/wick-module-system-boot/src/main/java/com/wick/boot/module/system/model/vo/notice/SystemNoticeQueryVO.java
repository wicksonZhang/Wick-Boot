package com.wick.boot.module.system.model.vo.notice;

import com.wick.boot.common.core.model.vo.CommonPageParamVO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * 通知公告分页查询参数
 *
 * @author Wickson
 * @date 2025-03-18 18:03
 */
@Setter
@Getter
@ApiModel(value = "SystemNoticeQueryVO", description = "通知公告分页查询参数")
public class SystemNoticeQueryVO extends CommonPageParamVO implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "通知标题")
    private String title;

    @ApiModelProperty(value = "发布状态")
    private Integer publishStatus;

    @ApiModelProperty(value = "是否已读", example = "0")
    private Integer isRead;

}
