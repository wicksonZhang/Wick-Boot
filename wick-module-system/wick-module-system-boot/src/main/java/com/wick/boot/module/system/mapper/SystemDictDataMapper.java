package com.wick.boot.module.system.mapper;

import cn.hutool.core.util.ObjUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wick.boot.common.mybatis.mapper.BaseMapperX;
import com.wick.boot.module.system.model.entity.SystemDictData;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 系统管理-字典数据持久层
 *
 * @author Wickson
 * @date 2024-04-08
 */
@Mapper
public interface SystemDictDataMapper extends BaseMapperX<SystemDictData> {

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

    /**
     * 通过字典类型编码、标签获取字典数据
     *
     * @param typeCode 字典编码
     * @param name     字典标签
     * @return SystemDictData 字典数据
     */
    default SystemDictData selectDictDataByName(String typeCode, String name) {
        return this.selectOne(new LambdaQueryWrapper<SystemDictData>()
                .eq(SystemDictData::getDictType, typeCode)
                .eq(SystemDictData::getLabel, name)
        );
    }

    /**
     * 通过字典类型编码、键值获取字典数据
     *
     * @param typeCode 字典编码
     * @param value    字典键值
     * @return SystemDictData 字典数据
     */
    default SystemDictData selectDictDataByValue(String typeCode, String value) {
        return this.selectOne(new LambdaQueryWrapper<SystemDictData>()
                .eq(SystemDictData::getDictType, typeCode)
                .eq(SystemDictData::getValue, value)
        );
    }

    /**
     * 获取字典数据信息
     *
     * @param typeCode 类型Code
     * @return List<SystemDictData>
     */
    default List<SystemDictData> selectDictDataOption(String typeCode) {
        return this.selectList(new LambdaQueryWrapper<SystemDictData>()
                .select(SystemDictData::getValue, SystemDictData::getLabel)
                .eq(SystemDictData::getDictType, typeCode)
        );
    }
}
