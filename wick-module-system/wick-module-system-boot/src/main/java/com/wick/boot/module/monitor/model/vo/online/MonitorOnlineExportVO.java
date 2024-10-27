package com.wick.boot.module.monitor.model.vo.online;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.write.style.ColumnWidth;
import lombok.Data;

/**
 * 在线用户导出类
 *
 * @author Wickson
 * @date 2024-10-27 17:52
 **/
@Data
@ColumnWidth(20)
public class MonitorOnlineExportVO {

    @ExcelProperty(value = "会话编号")
    private String sessionId;

    @ExcelProperty(value = "登录用户")
    private String username;

    @ExcelProperty(value = "部门名称")
    private String deptName;

    @ExcelProperty(value = "登录ip")
    private String loginIp;

    @ExcelProperty(value = "登录地址")
    private String loginAddress;

    @ExcelProperty(value = "登录时间")
    private String loginDate;
}
