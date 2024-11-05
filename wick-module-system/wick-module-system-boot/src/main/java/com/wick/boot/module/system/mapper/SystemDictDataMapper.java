package com.wick.boot.module.system.mapper;

import cn.hutool.core.util.ObjUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wick.boot.common.mybatis.mapper.BaseMapperX;
import com.wick.boot.module.system.model.dto.dictdata.SystemDictOptionsDTO;
import com.wick.boot.module.system.model.entity.SystemDictData;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

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
     * @param dictCode 字典编码
     * @return Page<SystemDictData>
     */
    default Page<SystemDictData> selectDictDataPage(Page<SystemDictData> page, String name, String dictCode) {
        return this.selectPage(page, new LambdaQueryWrapper<SystemDictData>()
                .likeRight(ObjUtil.isNotNull(name), SystemDictData::getLabel, name)
                .eq(SystemDictData::getDictCode, dictCode));
    }

    /**
     * 获取字典数据信息
     *
     * @param dictCode 类型Code
     * @return List<SystemDictData>
     */
    default List<SystemDictData> selectDictDataOption(String dictCode) {
        return this.selectList(new LambdaQueryWrapper<SystemDictData>()
                .select(SystemDictData::getValue, SystemDictData::getLabel)
                .eq(SystemDictData::getDictCode, dictCode)
        );
    }

    SystemDictOptionsDTO.DictData getDictDataList(@Param("dictCode") String dictCode);

    default long countDictDataByDictCodeAndValue(String dictCode, String value) {
        return this.selectCount(
                new LambdaQueryWrapper<SystemDictData>()
                        .eq(SystemDictData::getDictCode, dictCode)
                        .eq(SystemDictData::getValue, value)
        );
    }
}
