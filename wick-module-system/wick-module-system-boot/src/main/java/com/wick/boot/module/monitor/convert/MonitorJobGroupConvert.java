package com.wick.boot.module.monitor.convert;

import com.wick.boot.common.xxl.job.model.vo.jobgroup.XxlJobGroupQueryVO;
import com.wick.boot.common.xxl.job.model.vo.jobgroup.XxlJobGroupVO;
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
     * 将新增请求参数 VO 转换为 XxlJobGroupVO 实体
     *
     * @param reqVO 新增请求参数VO
     * @return 转换后的 XxlJobGroupVO 实体
     */
    @Mappings({
            @Mapping(target = "appname", source = "appName")
    })
    XxlJobGroupVO convertAddVoToEntity(MonitorJobGroupAddVO reqVO);

    /**
     * 将新增请求参数 VO 转换为 XxlJobGroupVO 实体
     *
     * @param reqVO 新增请求参数VO
     * @return 转换后的 XxlJobGroupVO 实体
     */
    @Mappings({
            @Mapping(target = "appname", source = "appName")
    })
    XxlJobGroupVO convertUpdateVoToEntity(MonitorJobGroupUpdateVO reqVO);

    @Mappings({
            @Mapping(target = "start", expression = "java(convertStart(queryParams))"),
            @Mapping(target = "length", source = "pageSize"),
            @Mapping(target = "appname", source = "appName")
    })
    XxlJobGroupQueryVO convertToQueryVo(MonitorJobGroupQueryVO queryParams);

    default int convertStart(MonitorJobGroupQueryVO queryParams) {
        return Math.max(queryParams.getPageNumber() - 1, 0);
    }

    List<MonitorJobGroupDTO> convertToDTOList(List<XxlJobGroupVO> jobGroupList);

    @Mappings({
            @Mapping(target = "appName", source = "appname")
    })
    MonitorJobGroupDTO toDTO(XxlJobGroupVO xxlJobGroupVO);
}
