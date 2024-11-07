package com.wick.boot.module.monitor.mapper;

import cn.hutool.core.util.ObjUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wick.boot.common.mybatis.mapper.BaseMapperX;
import com.wick.boot.module.monitor.model.entity.MonitorJob;
import com.wick.boot.module.monitor.model.vo.job.MonitorJobQueryVO;
import org.apache.ibatis.annotations.Mapper;


/**
 * 定时任务调度管理-Mapper接口
 *
 * @date 2024-11-07 13:48
 */
@Mapper
public interface MonitorJobMapper extends BaseMapperX<MonitorJob> {

    /**
     * 分页查询数据表
     *
     * @param page    分页集合
     * @param queryVO 请求参数
     * @return 数据表分页集合
     */
    default Page<MonitorJob> getMonitorJobPage(Page<MonitorJob> page, MonitorJobQueryVO queryVO) {
        LambdaQueryWrapper<MonitorJob> wrapper = new LambdaQueryWrapper<>();
        wrapper.select(
                        MonitorJob::getId,
                        MonitorJob::getJobName,
                        MonitorJob::getJobGroup,
                        MonitorJob::getInvokeTarget,
                        MonitorJob::getCronExpression,
                        MonitorJob::getMisfirePolicy,
                        MonitorJob::getConcurrent,
                        MonitorJob::getStatus,
                        MonitorJob::getRemark
                )
                .likeRight(ObjUtil.isNotEmpty(queryVO.getJobName()), MonitorJob::getJobName, queryVO.getJobName())
                .orderByDesc(MonitorJob::getId);

        return selectPage(page, wrapper);
    }
}
