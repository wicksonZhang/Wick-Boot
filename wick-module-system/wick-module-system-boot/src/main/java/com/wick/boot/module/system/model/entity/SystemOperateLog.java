package com.wick.boot.module.system.model.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.wick.boot.common.core.enums.UserTypeEnum;
import com.wick.boot.common.core.model.entity.BaseDO;
import com.wick.boot.common.core.result.ResultUtil;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.time.LocalDateTime;

/**
 * 操作日志表
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@TableName(value = "system_operate_log")
public class SystemOperateLog extends BaseDO {

    /**
     * {@link #javaMethodArgs} 的最大长度
     */
    public static final Integer JAVA_METHOD_ARGS_MAX_LENGTH = 8000;

    /**
     * {@link #resultData} 的最大长度
     */
    public static final Integer RESULT_MAX_LENGTH = 4000;

    /**
     * 日志主键
     */
    @TableId
    private Long id;
    /**
     * 用户编号
     * <p>
     * 关联 MemberUserDO 的 id 属性，或者 AdminUserDO 的 id 属性
     */
    private Long userId;
    /**
     * 用户类型
     * <p>
     * 关联 {@link  UserTypeEnum}
     */
    private Integer userType;
    /**
     * 操作模块
     */
    private String module;
    /**
     * 操作名
     */
    private String name;
    /**
     * 操作分类
     * <p>
     */
    private Integer type;
    /**
     * 请求方式
     */
    private String requestMethod;
    /**
     * 请求地址
     */
    private String requestUrl;
    /**
     * 用户 IP
     */
    private String userIp;
    /**
     * 用户操作地址
     */
    private String operateLocation;
    /**
     * 浏览器 UA
     */
    private String userAgent;
    /**
     * Java 方法名
     */
    private String javaMethod;
    /**
     * Java 方法的参数
     * <p>
     * 实际格式为 Map<String, Object>
     * 不使用 @TableField(typeHandler = FastjsonTypeHandler.class) 注解的原因是，数据库存储有长度限制，会进行裁剪，会导致 JSON 反序列化失败
     * 其中，key 为参数名，value 为参数值
     */
    private String javaMethodArgs;
    /**
     * 开始时间
     */
    private LocalDateTime startTime;
    /**
     * 执行时长，单位：毫秒
     */
    private Integer duration;
    /**
     * 结果码
     * <p>
     * 目前使用的 {@link ResultUtil#getCode()} 属性
     */
    private Integer resultCode;
    /**
     * 结果提示
     * <p>
     * 目前使用的 {@link ResultUtil#getMsg()} 属性
     */
    private String resultMsg;
    /**
     * 结果数据
     * <p>
     * 如果是对象，则使用 JSON 格式化
     */
    private String resultData;
}