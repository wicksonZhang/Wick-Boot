package com.wick.boot.module.monitor.convert;

import com.wick.boot.module.monitor.model.dto.job.MonitorJobDTO;
import com.wick.boot.module.monitor.model.entity.MonitorJob;
import com.wick.boot.module.monitor.model.vo.job.MonitorJobAddVO;
import com.wick.boot.module.monitor.model.vo.job.MonitorJobUpdateVO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * 定时任务调度管理-转换类
 *
 * @author Wickson
 * @date 2024-11-07 13:48
 */
@Mapper
public interface MonitorJobConvert {

    MonitorJobConvert INSTANCE = Mappers.getMapper(MonitorJobConvert.class);

    /**
     * Convert addVo To entity
     *
     * @param reqVO 新增请求参数VO
     * @return MonitorJob 定时任务调度
     */
     MonitorJob addVoToEntity(MonitorJobAddVO reqVO);

    /**
     * Convert updateVo To entity
     *
     * @param reqVO 更新请求参数VO
     * @return MonitorJob 定时任务调度实体
     */
     MonitorJob updateVoToEntity(MonitorJobUpdateVO reqVO);

    /**
     * Convert entity to DTO
     *
     * @param monitorJob 定时任务调度实体
     * @return MonitorJobDTO
     */
     MonitorJobDTO entityToDTO(MonitorJob monitorJob);

    /**
     * Convert entity to DTOList
     *
     * @param monitorJobList 定时任务调度实体集合
     * @return List<MonitorJobDTO> MonitorJobDTO集合对象
     */
    List<MonitorJobDTO> entityToPage(List<MonitorJob> monitorJobList);

}