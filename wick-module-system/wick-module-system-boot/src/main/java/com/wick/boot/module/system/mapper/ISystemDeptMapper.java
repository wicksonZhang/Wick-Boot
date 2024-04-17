package com.wick.boot.module.system.mapper;

import cn.hutool.core.util.ObjUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.wick.boot.common.core.enums.CommonStatusEnum;
import com.wick.boot.common.mybatis.mapper.BaseMapperX;
import com.wick.boot.module.system.model.entity.SystemDept;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Set;

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

    /**
     * 根据部门id模糊查询
     *
     * @param ids 部门集合ids
     * @return List<SystemDept> 部门集合数据
     */
    default Set<SystemDept> selectDeptByIdOrTreePath(List<Long> ids) {
        // 模糊匹配查询
        // SELECT id,name,parent_id,tree_path,sort,status,create_time,update_time,create_by,update_by,deleted
        // FROM system_dept
        // WHERE deleted=0 AND (id = ? OR CONCAT(',', tree_path, ',') LIKE CONCAT('%,', ?, ',%'))
        List<SystemDept> resultList = Lists.newArrayList();
        ids.forEach(id -> {
            List<SystemDept> byTreePath = this.selectList(new LambdaQueryWrapper<SystemDept>()
                    .eq(SystemDept::getId, id)
                    .or()
                    .apply("CONCAT(',', tree_path, ',') LIKE CONCAT('%,', {0}, ',%')", id)
            );
            resultList.addAll(byTreePath);
        });
        return Sets.newHashSet(resultList);
    }
}