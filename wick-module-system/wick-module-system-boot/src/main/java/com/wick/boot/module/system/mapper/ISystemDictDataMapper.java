package com.wick.boot.module.system.mapper;

import cn.hutool.core.util.ObjUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wick.boot.common.mybatis.mapper.BaseMapperX;
import com.wick.boot.module.system.model.entity.SystemDictData;
import org.apache.ibatis.annotations.Mapper;

/**
 * 系统管理-字典数据持久层
 *
 * @author ZhangZiHeng
 * @date 2024-04-08
 */
@Mapper
public interface ISystemDictDataMapper extends BaseMapperX<SystemDictData> {

    /**
     * 获取字典数据分页
     *
     * @param page     分页page
     * @param name     字典名称
     * @param typeCode 字典编码
     * @return Page<SystemDictData>
     */
    default Page<SystemDictData> selectDictDataPage(Page<SystemDictData> page, String name, String typeCode) {
        return this.selectPage(page, new LambdaQueryWrapper<SystemDictData>()
                .likeRight(ObjUtil.isNotNull(name), SystemDictData::getLabel, name)
                .eq(SystemDictData::getDictType, typeCode));
    }

}
