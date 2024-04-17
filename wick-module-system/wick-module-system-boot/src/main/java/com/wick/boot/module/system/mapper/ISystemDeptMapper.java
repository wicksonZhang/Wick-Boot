package com.wick.boot.module.system.mapper;

import cn.hutool.core.util.ObjUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.wick.boot.common.core.enums.CommonStatusEnum;
import com.wick.boot.common.mybatis.mapper.BaseMapperX;
import com.wick.boot.module.system.model.entity.SystemDept;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @author ZhangZiHeng
 * @date 2024-04-02
 */
@Mapper
public interface ISystemDeptMapper extends BaseMapperX<SystemDept> {

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
                .eq(ObjUtil.isNotNull(status), SystemDept::getStatus, status)
                .orderByAsc(SystemDept::getSort));
    }

    /**
     * 部门下拉选项
     *
     * @return List<SystemDept>
     */
    default List<SystemDept> selectDeptOptions() {
        return selectList(new LambdaQueryWrapper<SystemDept>()
                .select(SystemDept::getId, SystemDept::getParentId, SystemDept::getName)
                .eq(SystemDept::getStatus, CommonStatusEnum.ENABLE.getValue())
                .orderByAsc(SystemDept::getSort));
    }


    /**
     * 通过部门父级Id和部门名称获取部门信息
     *
     * @param parentId 父级ID
     * @param name     部门名称
     * @return SystemDept 部门信息
     */
    default Long selectCountByParentIdAndName(Long parentId, String name) {
        return selectCount(new LambdaQueryWrapper<SystemDept>()
                .eq(SystemDept::getParentId, parentId)
                .eq(SystemDept::getName, name)
        );
    }

    default List<SystemDept> selectDeptByIdOrTreePath(List<Long> ids) {
        return this.selectList(new LambdaQueryWrapper<SystemDept>()
                .in(SystemDept::getId, ids).or()
                .apply("CONCAT (',',tree_path,',') LIKE CONCAT('%,',{0},',%')", ids)
        );
    }
}