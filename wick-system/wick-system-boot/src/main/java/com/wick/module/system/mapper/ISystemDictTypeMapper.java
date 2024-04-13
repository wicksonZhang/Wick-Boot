package com.wick.module.system.mapper;

import cn.hutool.core.util.ObjUtil;
import com.wick.module.system.model.entity.SystemDictType;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author ZhangZiHeng
 * @date 2024-04-08
 */
@Mapper
public interface ISystemDictTypeMapper extends BaseMapper<SystemDictType> {

    default Page<SystemDictType> selectDictTypePage(Page<SystemDictType> page, String name, String code) {
        return this.selectPage(page, new LambdaQueryWrapper<SystemDictType>()
                .like(ObjUtil.isNotNull(name), SystemDictType::getName, name)
                .like(ObjUtil.isNotNull(code), SystemDictType::getCode, code));
    }

    default SystemDictType selectDictTypeByCode(String typeCode) {
        return this.selectOne(new LambdaQueryWrapper<SystemDictType>().eq(SystemDictType::getCode, typeCode));
    }

}
