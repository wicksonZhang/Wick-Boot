package com.wick.boot.module.monitor.service.impl;

import com.wick.boot.common.core.result.PageResult;
import com.wick.boot.module.monitor.model.dto.job.MonitorJobDTO;
import com.wick.boot.module.monitor.model.vo.job.MonitorJobAddVO;
import com.wick.boot.module.monitor.model.vo.job.MonitorJobQueryVO;
import com.wick.boot.module.monitor.model.vo.job.MonitorJobUpdateVO;
import com.wick.boot.module.monitor.service.MonitorJobAbstractService;
import com.wick.boot.module.monitor.service.MonitorJobService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 定时任务调度管理-服务实现类
 *
 * @author Wickson
 * @date 2024-11-07 13:48
 */
@Slf4j
@Service
public class MonitorJobServiceImpl extends MonitorJobAbstractService implements MonitorJobService {

    /**
     * 新增定时任务调度数据
     *
     * @param reqVO 新增请求参数
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Long addMonitorJob(MonitorJobAddVO reqVO) {
        /* Step-1: 校验新增参数 */
        this.validateAddParams(reqVO);

        return null;
    }

    /**
     * 更新定时任务调度数据
     *
     * @param reqVO 更新请求参数
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateMonitorJob(MonitorJobUpdateVO reqVO) {
        /* Step-1: 校验更新参数 */
        this.validateUpdateParams(reqVO);

        /* Step-2: 转换实体类型 */

        /* Step-3: 更新定时任务调度信息 */
    }

    /**
     * 删除定时任务调度数据
     *
     * @param ids 主键集合
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteMonitorJob(List<Long> ids) {
        /* Step-1: 校验删除参数 */

        /* Step-2: 删除定时任务调度信息 */
    }

    /**
     * 获取定时任务调度数据
     *
     * @param id 定时任务调度ID
     * @return MonitorJobDTO 定时任务调度DTO
     */
    public MonitorJobDTO getMonitorJob(Long id) {
        /* Step-1: 通过主键获取定时任务调度 */

        /* Step-2: 转换实体类型 */
        return null;
    }

    /**
     * 获取定时任务调度分页数据
     *
     * @param queryParams 分页查询参数
     * @return MonitorJobDTO 定时任务调度DTO
     */
    public PageResult<MonitorJobDTO> getMonitorJobPage(MonitorJobQueryVO queryParams) {
//        Page<MonitorJob> pageResult = this.monitorJobMapper.getMonitorJobPage(
//                new Page<>(queryParams.getPageNumber(), queryParams.getPageSize()),
//                queryParams
//        );
//
//        if (ObjUtil.isNull(pageResult)) {
//            return PageResult.empty();
//        }
//
//        List<MonitorJobDTO> monitorJobPages = MonitorJobConvert.INSTANCE.entityToPage(pageResult.getRecords());
//        return new PageResult<>(monitorJobPages, pageResult.getTotal());
        return null;
    }

}