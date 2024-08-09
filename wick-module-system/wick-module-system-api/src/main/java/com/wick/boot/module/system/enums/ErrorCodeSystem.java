package com.wick.boot.module.system.enums;

import com.wick.boot.common.core.result.ResultCode;

/**
 * System 错误码枚举类: 1-002-001-000
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
 * @author ZhangZiHeng
 * @date 2024-04-15
 */
public interface ErrorCodeSystem {

    // ======================================== System-用户管理 1-002-001-000 ========================================
    ResultCode USER_NOT_EXIST = new ResultCode(1002001001, "用户不存在");
    ResultCode USER_DOWNLOAD_ERROR = new ResultCode(1002001002, "用户导入模板下载失败");
    ResultCode USER_EXPORT_ERROR = new ResultCode(1002001003, "用户导出失败");
    ResultCode USER_IMPORT_ERROR = new ResultCode(1002001004, "用户导入失败");


    // ======================================== System-角色管理 1-002-002-000 ========================================


    // ======================================== System-菜单管理 1-002-003-000 ========================================
    ResultCode MENU_NOT_EXIST = new ResultCode(1002003001, "菜单不存在");
    ResultCode MENU_NAME_ALREADY_EXIST = new ResultCode(1002003002, "同一个菜单下不能存在相同的菜单名称");


    // ======================================== System-部门管理 1-002-004-000 ========================================
    ResultCode DEPT_NOT_EXIST = new ResultCode(1002004001, "部门不存在");
    ResultCode DEPT_NAME_ALREADY_EXIST = new ResultCode(1002004002, "同一个部门下不能存在相同的部门名称");
    ResultCode DEPT_PARENT_ID_CANNOT_BE_SELF = new ResultCode(1002004003, "部门父级ID不能是自己");

    // ======================================== System-字典管理 1-002-005-000 ========================================
    ResultCode DICT_TYPE_NOT_EXIST = new ResultCode(1002005001, "字典类型不存在");
    ResultCode DICT_TYPE_CODE_ALREADY_EXIST = new ResultCode(1002005002, "字典类型编码已存在");
    ResultCode DICT_TYPE_CODE_NOT_EXIST = new ResultCode(1002005003, "字典类型编码不存在");
    ResultCode DICT_DATA_NOT_EXIST = new ResultCode(1002005004, "字典数据不存在");
    ResultCode DICT_DATA_LABEL_ALREADY_EXIST = new ResultCode(1002005005, "字典标签已存在");
    ResultCode DICT_DATA_VALUE_ALREADY_EXIST = new ResultCode(1002005006, "字典键值已存在");

    // ======================================== System-日志管理 1-002-006-000 ========================================
    ResultCode LOGIN_LOG_EXPORT_ERROR = new ResultCode(1002006001, "用户登录日志导出失败");
    ResultCode OPERATE_LOG_EXPORT_ERROR = new ResultCode(1002006002, "用户操作日志导出失败");

    // ======================================== System-代码生成器 1-002-007-000 ========================================
    ResultCode TOOL_CODE_GEN_TABLE_NOT_EXIST = new ResultCode(1002007001, "数据表不存在");

}
