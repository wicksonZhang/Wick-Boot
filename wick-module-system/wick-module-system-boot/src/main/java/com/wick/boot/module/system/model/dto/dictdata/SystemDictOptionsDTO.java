package com.wick.boot.module.system.model.dto.dictdata;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.List;

/**
 * 字典类型
 *
 * @author Wickson
 * @date 2024-05-31
 */
@Data
@Builder
@ToString
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
public class SystemDictOptionsDTO implements Serializable {

    @ApiModelProperty(value = "字典名称", example = "男")
    private String name;

    @ApiModelProperty(value = "字典编码", example = "1")
    private String dictCode;

    @ApiModelProperty(value = "字典数据集合")
    private List<DictData> dictDataList;

    public SystemDictOptionsDTO(String name, String dictCode) {
        this.name = name;
        this.dictCode = dictCode;
    }

    @Getter
    @Setter
    @ToString
    @NoArgsConstructor
    @AllArgsConstructor
    @ApiModel(value = "字典数据")
    public static class DictData {

        @ApiModelProperty(value = "字典数据值")
        private String value;

        @ApiModelProperty(value = "字典数据标签")
        private String label;

        @ApiModelProperty(value = "标签类型")
        private String tagType;
    }

}