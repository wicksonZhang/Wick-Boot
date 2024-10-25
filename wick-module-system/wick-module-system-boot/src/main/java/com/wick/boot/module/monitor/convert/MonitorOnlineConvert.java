package com.wick.boot.module.monitor.convert;

import com.wick.boot.module.monitor.model.dto.MonitorOnlineDTO;
import com.wick.boot.module.system.model.dto.LoginUserInfoDTO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * 在线用户-转换类
 *
 * @author Wickson
 * @date 2024-10-25
 */
@Mapper
public interface MonitorOnlineConvert {

    MonitorOnlineConvert INSTANCE = Mappers.getMapper(MonitorOnlineConvert.class);

    MonitorOnlineDTO toDTO(LoginUserInfoDTO loginUserInfo);
}
