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

    /**
     * 分页查询字典类型
     *
     * @param page 分页集合
     * @param name 字典名称
     * @param code 字典编码
     * @return
     */
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

    /**
     * 根据字典编码查询字典类型
     *
     * @param dictCode 字典编码
     * @return
     */
    default SystemDictType selectDictTypeByCode(String dictCode) {
        LambdaQueryWrapper<SystemDictType> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(SystemDictType::getDictCode, dictCode);
        return this.selectOne(queryWrapper);
    }

    /**
     * 根据字典编码查询字典数据
     *
     * @param dictCode 字典编码
     * @return
     */
    List<SystemDictOptionsDTO> selectSystemDictOptions(@Param("dictCode") String dictCode);
}
