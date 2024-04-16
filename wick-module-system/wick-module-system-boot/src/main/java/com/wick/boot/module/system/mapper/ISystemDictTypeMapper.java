package com.wick.boot.module.system.mapper;

import cn.hutool.core.util.ObjUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wick.boot.common.mybatis.mapper.BaseMapperX;
import com.wick.boot.module.system.model.entity.SystemDictType;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author ZhangZiHeng
 * @date 2024-04-08
 */
@Mapper
public interface ISystemDictTypeMapper extends BaseMapperX<SystemDictType> {

    default Page<SystemDictType> selectDictTypePage(Page<SystemDictType> page, String name, String code) {
        return this.selectPage(page, new LambdaQueryWrapper<SystemDictType>()
                .like(ObjUtil.isNotNull(name), SystemDictType::getName, name)
                .like(ObjUtil.isNotNull(code), SystemDictType::getCode, code));
    }

    default SystemDictType selectDictTypeByCode(String typeCode) {
        return this.selectOne(new LambdaQueryWrapper<SystemDictType>().eq(SystemDictType::getCode, typeCode));
    }

}
