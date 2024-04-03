package cn.wickson.security.system.mapper;

import cn.hutool.core.util.ObjUtil;
import cn.wickson.security.commons.result.PageResult;
import cn.wickson.security.system.model.dto.SystemUserDTO;
import cn.wickson.security.system.model.entity.SystemUser;
import cn.wickson.security.system.model.vo.QueryUserPageReqVO;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

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

    /**
     * 分页查询用户信息
     *
     * @param reqVO 用户请求信息
     * @return PageResult<SystemUser>
     */
    Page<SystemUserDTO> selectPage(Page<QueryUserPageReqVO> page, @Param("reqVO") QueryUserPageReqVO reqVO);

}
