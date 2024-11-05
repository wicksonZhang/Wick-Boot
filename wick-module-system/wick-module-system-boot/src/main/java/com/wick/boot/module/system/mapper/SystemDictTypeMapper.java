package com.wick.boot.module.system.mapper;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wick.boot.common.mybatis.mapper.BaseMapperX;
import com.wick.boot.module.system.model.dto.dictdata.SystemDictOptionsDTO;
import com.wick.boot.module.system.model.entity.SystemDictType;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 字典类型Mapper
 *
 * @author Wickson
 * @date 2024-04-08
 */
@Mapper
public interface SystemDictTypeMapper extends BaseMapperX<SystemDictType> {

    default Page<SystemDictType> selectDictTypePage(Page<SystemDictType> page, String name, String code) {
        LambdaQueryWrapper<SystemDictType> queryWrapper = new LambdaQueryWrapper<>();
        if (StrUtil.isNotBlank(name)) {
            queryWrapper.likeRight(SystemDictType::getName, name);
        }
        if (StrUtil.isNotBlank(code)) {
            queryWrapper.likeRight(SystemDictType::getDictCode, code);
        }
        return this.selectPage(page, queryWrapper);
    }

    default SystemDictType selectDictTypeByCode(String dictCode) {
        return this.selectOne(new LambdaQueryWrapper<SystemDictType>().eq(SystemDictType::getDictCode, dictCode));
    }

    List<SystemDictOptionsDTO> selectSystemDictOptions(@Param("dictCode") String dictCode);
}
