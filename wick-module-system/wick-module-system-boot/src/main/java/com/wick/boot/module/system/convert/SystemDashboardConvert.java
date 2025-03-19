package com.wick.boot.module.system.convert;

import com.wick.boot.module.system.model.dto.SystemDashboardVisitStatsDTO;
import com.wick.boot.module.system.model.dto.dashboard.SystemDashboardVisitDTO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * 后台管理首页 - Convert
 *
 * @author Wickson
 * @date 2024-04-07
 */
@Mapper
public interface SystemDashboardConvert {

    SystemDashboardConvert INSTANCE = Mappers.getMapper(SystemDashboardConvert.class);

    /**
     * 转换访问量统计
     *
     * @param visitDTO
     * @return
     */
    SystemDashboardVisitStatsDTO convertToDTO(SystemDashboardVisitDTO visitDTO);

}
