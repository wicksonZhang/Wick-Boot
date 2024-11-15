package com.wick.boot.module.monitor.convert;

import com.wick.boot.common.core.model.dto.OptionDTO;
import com.wick.boot.common.xxl.job.model.entity.XxlJobInfo;
import com.wick.boot.common.xxl.job.model.entity.XxlJobLog;
import com.wick.boot.common.xxl.job.model.vo.joblog.XxlJobLogQueryVO;
import com.wick.boot.module.monitor.model.dto.joblog.MonitorXxlJobLogDTO;
import com.wick.boot.module.monitor.model.vo.joblog.MonitorXxlJobLogQueryVO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;

/**
 * 调度日志管理-转换类
 *
 * @author Wickson
 * @date 2024-11-15 15:25
 */
@Mapper
public interface MonitorXxlJobLogConvert {

    MonitorXxlJobLogConvert INSTANCE = Mappers.getMapper(MonitorXxlJobLogConvert.class);

    List<OptionDTO<Integer>> toDtoList(List<XxlJobInfo> jobList);

    @Mappings({
            @Mapping(target = "value", source = "id"),
            @Mapping(target = "label", source = "jobDesc")
    })
    OptionDTO<Integer> toXxlJonInfo(XxlJobInfo jobInfo);

    @Mappings({
            @Mapping(target = "start", expression = "java(convertStart(queryParams))"),
            @Mapping(target = "length", source = "pageSize"),
            @Mapping(target = "jobGroup", defaultValue = "0", source = "jobGroup"),
            @Mapping(target = "jobId", defaultValue = "0", source = "jobId"),
            @Mapping(target = "filterTime", expression = "java(convertFilterTime(queryParams))")
    })
    XxlJobLogQueryVO toQueryVO(MonitorXxlJobLogQueryVO queryParams);

    default int convertStart(MonitorXxlJobLogQueryVO queryParams) {
        return Math.max(queryParams.getPageNumber() - 1, 0);
    }

    default String convertFilterTime(MonitorXxlJobLogQueryVO queryParams) {
        LocalDateTime[] triggerTime = queryParams.getTriggerTime();
        DateTimeFormatter defaultFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        if (triggerTime == null) {
            String time = defaultFormatter.format(LocalDateTime.now());
            return time + " 00:00:00" + " - " + time + "23:59:59";
        }
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String startTime = formatter.format(triggerTime[0]);
        String endTime = formatter.format(triggerTime[1]);
        return startTime + " - " + endTime;
    }

    List<MonitorXxlJobLogDTO> convertToDTOList(List<XxlJobLog> jobLogList);

    @Mappings({
            @Mapping(target = "triggerTime", expression = "java(convertTriggerTime(xxlJobLog))"),
            @Mapping(target = "handleTime", expression = "java(convertHandleTime(xxlJobLog))")
    })
    MonitorXxlJobLogDTO toDTO(XxlJobLog xxlJobLog);

    default LocalDateTime convertTriggerTime(XxlJobLog xxlJobLog) {
        Date triggerTime = xxlJobLog.getTriggerTime();
        if (triggerTime == null) {
            return null;
        }
        return triggerTime.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
    }

    default LocalDateTime convertHandleTime(XxlJobLog xxlJobLog) {
        Date handleTime = xxlJobLog.getHandleTime();
        if (handleTime == null) {
            return null;
        }
        return handleTime.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
    }
}