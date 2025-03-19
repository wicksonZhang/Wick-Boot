package com.wick.boot.module.system.model.vo.notice;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import javax.validation.constraints.NotNull;

/**
 * 通知公告更新参数类
 *
 * @author Wickson
 * @date 2025-03-18 18:03
 */
@Setter
@Getter
@ApiModel(value = "SystemNoticeUpdateVO对象", description = "通知公告更新参数")
public class SystemNoticeUpdateVO extends SystemNoticeAddVO implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "主键ID", required = true, example = "1")
    @NotNull(message = "主键ID不能为空")
    private Long id;
}
