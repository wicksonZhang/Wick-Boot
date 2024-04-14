package com.wick.module.system.mapper;

import cn.hutool.core.util.ObjUtil;
import com.wick.module.system.model.entity.SystemDictData;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author ZhangZiHeng
 * @date 2024-04-08
 */
@Mapper
public interface ISystemDictDataMapper extends BaseMapper<SystemDictData> {

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
