package com.wick.boot.module.system.model.vo.user;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.write.style.ColumnWidth;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@ColumnWidth(20)
public class UserExportVO {

    @ExcelProperty(value = "用户名")
    private String username;

    @ExcelProperty(value = "用户昵称")
    private String nickname;

    @ExcelProperty(value = "部门")
    private String deptName;

    @ExcelProperty(value = "性别")
    private String gender;

    @ExcelProperty(value = "手机号码")
    private String mobile;

    @ExcelProperty(value = "邮箱")
    private String email;

    @ExcelProperty(value = "创建时间")
    @JsonFormat(shape = JsonFormat.Shape.STRING,
            pattern = "yyyy-MM-dd HH:mm:ss",
            timezone = "GMT+8")
    private LocalDateTime createTime;

}