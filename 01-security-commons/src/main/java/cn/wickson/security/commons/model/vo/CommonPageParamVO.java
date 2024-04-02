package cn.wickson.security.commons.model.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Getter
@Setter
public class CommonPageParamVO implements Serializable {

    private static final Integer PAGE_NUMBER = 1;

    private static final Integer PAGE_SIZE = 10;

    @ApiModelProperty(value = "查询页码", required = true)
    @Min(value = 1L, message = "页码必须大于0")
    @NotNull(message = "查询页码不能空")
    private Integer pageNumber = PAGE_NUMBER;

    @ApiModelProperty(value = "每页显示记录条数", required = true)
    @Min(value = 1L, message = "每页显示记录条数：必须大于0")
    @Max(value = 50L, message = "每页显示记录条数：必须小于等于50")
    @NotNull(message = "每页显示记录条数不能空")
    private Integer pageSize = PAGE_SIZE;

}
