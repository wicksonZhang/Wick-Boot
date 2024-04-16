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


}