package com.wick.boot.common.core.model.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModelProperty;
import jdk.nashorn.internal.runtime.options.Option;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * 下拉选项DTO
 *
 * @author Wickson
 * @date 2024-11-04
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OptionDTO<T> {

    @ApiModelProperty(value = "选项的值")
    private T value;

    @ApiModelProperty(value = "选项的标签")
    private String label;

    @ApiModelProperty(value = "标签类型")
    @JsonInclude(value = JsonInclude.Include.NON_EMPTY)
    private String tag;

    @ApiModelProperty(value = "子选项列表")
    @JsonInclude(value = JsonInclude.Include.NON_EMPTY)
    private List<Option<T>> children;

}
