package com.wick.boot.module.monitor.convert;

import com.wick.boot.common.core.model.dto.OptionDTO;
import com.wick.boot.common.xxl.job.model.entity.XxlJobGroup;
import com.wick.boot.common.xxl.job.model.vo.jobgroup.XxlJobGroupQueryVO;
import com.wick.boot.module.monitor.model.dto.jobgroup.MonitorJobGroupDTO;
import com.wick.boot.module.monitor.model.vo.jobgroup.MonitorJobGroupAddVO;
import com.wick.boot.module.monitor.model.vo.jobgroup.MonitorJobGroupQueryVO;
import com.wick.boot.module.monitor.model.vo.jobgroup.MonitorJobGroupUpdateVO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * 执行器管理-转换类
 *
 * @author Wickson
 * @date 2024-11-12
 */
@Mapper
public interface MonitorJobGroupConvert {

    MonitorJobGroupConvert INSTANCE = Mappers.getMapper(MonitorJobGroupConvert.class);

    /**
     * 将新增请求参数 VO 转换为 XxlJobGroup 实体
     *
     * @param reqVO 新增请求参数VO
     * @return 转换后的 XxlJobGroup 实体
     */
    @Mappings({
            @Mapping(target = "appname", source = "appName")
    })
    XxlJobGroup convertAddVoToEntity(MonitorJobGroupAddVO reqVO);

    /**
     * 将新增请求参数 VO 转换为 XxlJobGroup 实体
     *
     * @param reqVO 新增请求参数VO
     * @return 转换后的 XxlJobGroup 实体
     */
    @Mappings({
            @Mapping(target = "appname", source = "appName")
    })
    XxlJobGroup convertUpdateVoToEntity(MonitorJobGroupUpdateVO reqVO);

    @Mappings({
            @Mapping(target = "start", expression = "java(convertStart(queryParams))"),
            @Mapping(target = "length", source = "pageSize"),
            @Mapping(target = "appname", source = "appName")
    })
    XxlJobGroupQueryVO convertToQueryVo(MonitorJobGroupQueryVO queryParams);

    default int convertStart(MonitorJobGroupQueryVO queryParams) {
        return Math.max(queryParams.getPageNumber() - 1, 0);
    }

    List<MonitorJobGroupDTO> convertToDTOList(List<XxlJobGroup> jobGroupList);

    @Mappings({
            @Mapping(target = "appName", source = "appname")
    })
    MonitorJobGroupDTO toDTO(XxlJobGroup xxlJobGroup);

    /**
     * 转换为 OptionDTO
     *
     * @param jobGroupList 定时任务管理器集合
     * @return
     */
    List<OptionDTO<Integer>> convertToOptionDTOList(List<XxlJobGroup> jobGroupList);

    @Mappings({
            @Mapping(target = "value", source = "id"),
            @Mapping(target = "label", source = "title"),
    })
    OptionDTO<Integer> toOptionDTO(XxlJobGroup xxlJobGroup);

}
