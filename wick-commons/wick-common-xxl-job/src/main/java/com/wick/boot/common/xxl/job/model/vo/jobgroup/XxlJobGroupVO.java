package com.wick.boot.common.xxl.job.model.vo.jobgroup;

import cn.hutool.core.util.StrUtil;
import lombok.Getter;
import lombok.Setter;

import java.util.Arrays;
import java.util.List;

/**
 * 执行器管理-新增OR更新VO
 *
 * @author Wickson
 * @date 2024-11-11
 */
@Setter
@Getter
public class XxlJobGroupVO {

    /**
     * 主键id
     */
    private Integer id;

    /**
     * 执行器AppName
     */
    private String appname;

    /**
     * 执行器名称
     */
    private String title;

    /**
     * 执行器地址类型：0=自动注册、1=手动录入
     */
    private Integer addressType;

    /**
     * 执行器地址列表，多地址逗号分隔
     */
    private String addressList;

    /**
     * 执行器地址列表(系统注册)
     */
    private List<String> registryList;

    public List<String> getRegistryList() {
        if (StrUtil.isNotBlank(addressList)) {
            registryList = Arrays.asList(addressList.split(","));
        }
        return registryList;
    }
}
