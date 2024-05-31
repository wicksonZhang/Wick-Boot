package com.wick.boot.module.system.mapper;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wick.boot.common.mybatis.mapper.BaseMapperX;
import com.wick.boot.module.system.model.entity.SystemDictType;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @author ZhangZiHeng
 * @date 2024-04-08
 */
@Mapper
public interface ISystemDictTypeMapper extends BaseMapperX<SystemDictType> {

    default Page<SystemDictType> selectDictTypePage(Page<SystemDictType> page, String name, String code) {
        LambdaQueryWrapper<SystemDictType> queryWrapper = new LambdaQueryWrapper<>();
        if (StrUtil.isNotBlank(name)) {
            queryWrapper.likeRight(SystemDictType::getName, name);
        }
        if (StrUtil.isNotBlank(code)) {
            queryWrapper.likeRight(SystemDictType::getCode, code);
        }
        return this.selectPage(page, queryWrapper);
    }

    default SystemDictType selectDictTypeByCode(String typeCode) {
        return this.selectOne(new LambdaQueryWrapper<SystemDictType>().eq(SystemDictType::getCode, typeCode));
    }

}
