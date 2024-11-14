package com.wick.boot.module.monitor.convert;

import com.wick.boot.common.xxl.job.model.entity.XxlJobInfo;
import com.wick.boot.common.xxl.job.model.vo.jobinfo.XxlJobInfoQueryVO;
import com.wick.boot.module.monitor.model.dto.jobinfo.MonitorXxlJobInfoDTO;
import com.wick.boot.module.monitor.model.vo.jobinfo.MonitorXxlJobInfoAddVO;
import com.wick.boot.module.monitor.model.vo.jobinfo.MonitorXxlJobInfoQueryVO;
import com.wick.boot.module.monitor.model.vo.jobinfo.MonitorXxlJobInfoUpdateVO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.util.Date;
import java.util.List;

/**
 * 定时任务管理管理-转换类
 *
 * @author Wickson
 * @date 2024-11-13 10:24
 */
@Mapper
public interface MonitorXxlJobInfoConvert {

    MonitorXxlJobInfoConvert INSTANCE = Mappers.getMapper(MonitorXxlJobInfoConvert.class);

    /**
     * Convert addVo To entity
     *
     * @param reqVO 新增请求参数VO
     * @return MonitorXxlJobInfo 定时任务管理
     */
    XxlJobInfo addVoToEntity(MonitorXxlJobInfoAddVO reqVO);

    /**
     * Convert updateVo To entity
     *
     * @param reqVO 更新请求参数VO
     * @return MonitorXxlJobInfo 定时任务管理实体
     */
    XxlJobInfo updateVoToEntity(MonitorXxlJobInfoUpdateVO reqVO);

    /**
     * Convert entity to DTOList
     *
     * @param monitorXxlJobInfoList 定时任务管理实体集合
     * @return List<MonitorXxlJobInfoDTO> MonitorXxlJobInfoDTO集合对象
     */
    List<MonitorXxlJobInfoDTO> entityToPage(List<XxlJobInfo> monitorXxlJobInfoList);

    @Mappings({
            @Mapping(target = "addTime", expression = "java(convertAddTime(xxlJobInfo))"),
            @Mapping(target = "glueUpdatetime", expression = "java(convertUpdateTime(xxlJobInfo))"),
            @Mapping(target = "triggerLastTime", expression = "java(convertLastTime(xxlJobInfo))"),
            @Mapping(target = "triggerNextTime", expression = "java(convertNextTime(xxlJobInfo))")
    })
    MonitorXxlJobInfoDTO toXxlJobInfoDTO(XxlJobInfo xxlJobInfo);

    /**
     * 转换添加时间
     *
     * @param xxlJobInfo 定时任务信息
     * @return 添加时间
     */
    default LocalDateTime convertAddTime(XxlJobInfo xxlJobInfo) {
        Date addTime = xxlJobInfo.getAddTime();
        if (addTime == null) {
            return null;
        }
        return addTime.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
    }

    /**
     * 转换添加时间
     *
     * @param xxlJobInfo 定时任务信息
     * @return 添加时间
     */
    default LocalDateTime convertUpdateTime(XxlJobInfo xxlJobInfo) {
        Date updateTime = xxlJobInfo.getGlueUpdatetime();
        if (updateTime == null) {
            return null;
        }
        return updateTime.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
    }

    /**
     * 转换上次调度时间
     *
     * @param xxlJobInfo 定时任务信息
     * @return 上次调度时间
     */
    default LocalDateTime convertLastTime(XxlJobInfo xxlJobInfo) {
        long triggerLastTime = xxlJobInfo.getTriggerLastTime();
        if (triggerLastTime == 0) {
            return null;
        }
        return LocalDateTime.ofEpochSecond(triggerLastTime / 1000, 0, ZoneOffset.ofHours(8));
    }

    /**
     * 转换下次调度时间
     *
     * @param xxlJobInfo 定时任务信息
     * @return 下次调度时间
     */
    default LocalDateTime convertNextTime(XxlJobInfo xxlJobInfo) {
        long triggerNextTime = xxlJobInfo.getTriggerNextTime();
        if (triggerNextTime == 0) {
            return null;
        }
        return LocalDateTime.ofEpochSecond(triggerNextTime / 1000, 0, ZoneOffset.ofHours(8));
    }

    @Mappings({
            @Mapping(target = "start", expression = "java(convertStart(queryParams))"),
            @Mapping(target = "length", source = "pageSize")
    })
    XxlJobInfoQueryVO toQueryVO(MonitorXxlJobInfoQueryVO queryParams);

    default int convertStart(MonitorXxlJobInfoQueryVO queryParams) {
        return Math.max(queryParams.getPageNumber() - 1, 0);
    }

}