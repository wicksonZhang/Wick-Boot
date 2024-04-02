package cn.wickson.security.system.mapper;

import cn.wickson.security.system.model.entity.SystemUser;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author ZhangZiHeng
 * @date 2024-04-02
 */
@Mapper
public interface ISystemUserMapper extends BaseMapper<SystemUser> {

    /**
     * 校验用户名唯一
     *
     * @param username 用户名
     * @return AdminUser
     */
    default SystemUser selectByUsername(String username) {
        return selectOne(new LambdaQueryWrapper<SystemUser>().eq(SystemUser::getUsername, username));
    }

}
