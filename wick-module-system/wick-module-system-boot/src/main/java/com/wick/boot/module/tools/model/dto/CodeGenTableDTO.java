package com.wick.boot.module.tools.model.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * 代码自动生成器 - DTO
 *
 * @author ZhangZiHeng
 * @date 2024-07-23
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class CodeGenTableDTO {

    @ApiModelProperty(value = "表名", example = "system_user")
    private String tableName;

    @ApiModelProperty(value = "表描述", example = "用户信息表")
    private String tableComment;

    @ApiModelProperty(value = "创建时间", example = "2024-07-23 02:24:37")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private LocalDateTime createTime;

    @ApiModelProperty(value = "更新时间", example = "2024-07-23 02:24:37")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private LocalDateTime updateTime;

}
