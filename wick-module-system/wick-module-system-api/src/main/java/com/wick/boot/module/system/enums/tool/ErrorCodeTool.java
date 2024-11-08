package com.wick.boot.module.system.enums.tool;

import com.wick.boot.common.core.result.ResultCode;

/**
 * System 错误码枚举类: 1-003-001-000
 * <p>
 * 业务异常的错误码区间，解决：解决各模块错误码定义，避免重复，在此只声明不做实际使用
 * 一共 10 位，分成四段
 * 第一段，1 位，类型
 * <span> 1 - 业务级别异常 </span>
 * <span> x - 预留 </span>
 * <p>
 * 第二段，3 位，系统类型
 * <span> 001 - 认证中心 </span>
 * <span> 002 - 系统管理 </span>
 * <span> 003 - 系统工具 </span>
 * ... - ...
 * <p>
 * 第三段，3 位，模块
 * <span> 不限制规则。
 * <span> 一般建议，每个系统里面，可能有多个模块，可以再去做分段。以用户系统为例子：
 * <span> 001 - 用户管理
 * <span> 002 - 角色管理
 * <span> 003 - 菜单管理
 * <span> 004 - 部门管理
 * <span> 005 - 字典管理
 * <span> 006 - 日志管理
 * <span> 007 - 数据源管理
 * <p>
 * 第四段，3 位，错误码
 * <span> 不限制规则，一般建议，每个模块自增。
 *
 * @author Wickson
 * @date 2024-11-08
 */
public interface ErrorCodeTool {

    // ======================================== Tool-代码生成器 1-003-007-000 ========================================
    ResultCode TOOL_CODE_GEN_TABLE_NOT_EXIST = new ResultCode(1003007001, "数据表不存在");

    ResultCode TOOL_DATA_SOURCE_NOT_EXIST = new ResultCode(1003007002, "数据源不存在");

    ResultCode TOOL_DATA_SOURCE_NAME_EXIST = new ResultCode(1003007003, "数据源名称已存在");

}
