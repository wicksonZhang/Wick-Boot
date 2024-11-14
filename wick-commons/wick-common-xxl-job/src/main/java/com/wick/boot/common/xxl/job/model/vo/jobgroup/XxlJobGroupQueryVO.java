package com.wick.boot.common.xxl.job.model.vo.jobgroup;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

/**
 * 执行器管理-查询VO
 *
 * @author Wickson
 * @date 2024-11-12
 */
@Setter
@Getter
@Accessors(chain = true)
public class XxlJobGroupQueryVO {

    /**
     * 开始页
     */
    private Integer start = 0;

    /**
     * 每页条数
     */
    private Integer length = 10;

    /**
     * appName
     */
    private String appname;

    /**
     * 执行器名称
     */
    private String titile;

}
