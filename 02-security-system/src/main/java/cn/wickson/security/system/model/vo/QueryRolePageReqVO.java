package cn.wickson.security.system.model.vo;

import cn.wickson.security.commons.model.vo.CommonPageParamVO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * 系统管理-角色信息查询
 *
 * @author ZhangZiHeng
 * @date 2024-04-07
 */
@Setter
@Getter
@ApiModel(value = "QueryUserPageReqVO", description = "用户信息查询条件参数")
public class QueryRolePageReqVO extends CommonPageParamVO {

    @ApiModelProperty(value = "角色名称", example = "系统管理员")
    private String name;

    @ApiModelProperty(value = "角色编码", example = "ADMIN")
    private String code;

}
