package cn.wickson.security.system.mapper;

import cn.wickson.security.system.model.entity.SystemRole;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 后台管理 - 角色Mapper
 *
 * @author ZhangZiHeng
 * @date 2024-04-07
 */
@Mapper
public interface ISystemRoleMapper extends BaseMapper<SystemRole> {

}
