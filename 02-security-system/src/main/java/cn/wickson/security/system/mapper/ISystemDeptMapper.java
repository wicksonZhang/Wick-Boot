package cn.wickson.security.system.mapper;

import cn.hutool.core.util.ObjUtil;
import cn.hutool.core.util.StrUtil;
import cn.wickson.security.system.model.entity.SystemDept;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Objects;

/**
 * @author ZhangZiHeng
 * @date 2024-04-02
 */
@Mapper
public interface ISystemDeptMapper extends BaseMapper<SystemDept> {

    /**
     * 查询部门列表信息
     *
     * @param name   部门名称
     * @param status 状态
     * @return List<SystemDept>
     */
    default List<SystemDept> selectList(String name, Integer status) {
        return selectList(new LambdaQueryWrapper<SystemDept>()
                .likeRight(ObjUtil.isNotNull(name), SystemDept::getName, name)
                .eq(ObjUtil.isNotNull(status), SystemDept::getStatus, status));
    }

}