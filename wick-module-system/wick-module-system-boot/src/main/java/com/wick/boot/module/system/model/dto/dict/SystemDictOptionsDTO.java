package com.wick.boot.module.system.model.dto.dict;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 字典类型
 *
 * @author Wickson
 * @date 2024-05-31
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class SystemDictOptionsDTO<T> {

    @ApiModelProperty(value = "字典Id", example = "1")
    private T value;

    @ApiModelProperty(value = "字典值", example = "男")
    private String label;

}
