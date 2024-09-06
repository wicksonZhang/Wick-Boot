package com.wick.boot.module.system.model.vo.logger.operate;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.write.style.ColumnWidth;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.wick.boot.common.log.enums.OperateTypeEnum;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * 用户操作日志
 *
 * @author Wickson
 * @date 2024-07-02
 */
@Data
@ColumnWidth(20)
public class OperateLogExportVO {

    @ApiModelProperty(value = "日志编号", example = "1024")
    @ExcelProperty("日志主键")
    private Long id;

    @ApiModelProperty(value = "操作模块", example = "订单")
    @ExcelProperty("操作模块")
    private String module;

    @ApiModelProperty(value = "操作名", example = "创建订单")
    @ExcelProperty("操作名")
    private String name;

    @ApiModelProperty(value = "操作分类，参见 OperateLogTypeEnum 枚举类", example = "1")
    @ExcelProperty("操作分类")
    private String type;

    @ApiModelProperty(value = "开始时间")
    @ExcelProperty("开始时间")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private LocalDateTime startTime;

    @ApiModelProperty(value = "执行时长，单位：毫秒")
    @ExcelProperty("执行时长")
    private Integer duration;

    @ApiModelProperty(value = "结果提示")
    @ExcelProperty("结果提示")
    private String resultMsg;

    public String getType() {
        return OperateTypeEnum.valueOf(Integer.parseInt(type));
    }
}
