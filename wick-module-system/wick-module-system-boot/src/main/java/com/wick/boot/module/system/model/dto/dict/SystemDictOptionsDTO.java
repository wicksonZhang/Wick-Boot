package com.wick.boot.module.system.model.dto.dict;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

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
public class SystemDictOptionsDTO<T> implements Serializable {

    @ApiModelProperty(value = "字典Id", example = "1")
    private T value;

    @ApiModelProperty(value = "字典值", example = "男")
    private String label;

}