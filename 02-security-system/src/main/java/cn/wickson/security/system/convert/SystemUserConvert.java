package cn.wickson.security.system.convert;

import cn.wickson.security.system.model.dto.SystemUserDTO;
import cn.wickson.security.system.model.entity.SystemUser;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * 后台管理 - 用户信息
 *
 * @author ZhangZiHeng
 * @date 2024-04-02
 */
@Mapper
public interface SystemUserConvert {

    SystemUserConvert INSTANCE = Mappers.getMapper(SystemUserConvert.class);

    SystemUserDTO entityToDTO(SystemUser systemUser);

}
