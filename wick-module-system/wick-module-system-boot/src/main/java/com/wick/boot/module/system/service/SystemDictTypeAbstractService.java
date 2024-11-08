package com.wick.boot.module.system.service;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.ObjUtil;
import com.wick.boot.common.core.exception.ServiceException;
import com.wick.boot.module.system.enums.system.ErrorCodeSystem;
import com.wick.boot.module.system.mapper.SystemDictDataMapper;
import com.wick.boot.module.system.mapper.SystemDictTypeMapper;
import com.wick.boot.module.system.model.entity.SystemDictType;
import com.wick.boot.module.system.model.vo.dicttype.SystemDictTypeAddVO;
import com.wick.boot.module.system.model.vo.dicttype.SystemDictTypeUpdateVO;

import javax.annotation.Resource;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 字典类型-防腐层
 *
 * @author Wickson
 * @date 2024-04-15
 */
public abstract class SystemDictTypeAbstractService {

    @Resource
    protected SystemDictTypeMapper dictTypeMapper;

    @Resource
    protected SystemDictDataMapper dictDataMapper;

    // ============================================== 新增参数校验 ==============================================

    /**
     * 校验字典类型新增参数
     *
     * @param reqVO 新增请求参数
     */
    protected void validateAddParams(SystemDictTypeAddVO reqVO) {
        // 验证字典编码是否存在
        this.validateDictTypeByCode(reqVO.getDictCode());
    }

    /**
     * 校验字典编码是否存在
     *
     * @param dictCode 字典编码
     */
    private void validateDictTypeByCode(String dictCode) {
        SystemDictType dictType = this.dictTypeMapper.selectDictTypeByCode(dictCode);
        if (ObjUtil.isNotNull(dictType)) {
            throw ServiceException.getInstance(ErrorCodeSystem.DICT_TYPE_CODE_ALREADY_EXIST);
        }
    }

    // ============================================== 更新参数校验 ==============================================

    /**
     * 校验字典类型更新参数
     *
     * @param reqVO 更新请求参数
     */
    protected void validateUpdateParams(SystemDictType systemDictType, SystemDictTypeUpdateVO reqVO) {
        // 验证字典类型是否存在
        this.validateDictType(systemDictType);
        // 验证字典编码是否存在
        this.validateDictTypeByCode(systemDictType.getDictCode(), reqVO.getDictCode());
    }

    /**
     * 校验字典类型是否存在
     *
     * @param systemDictType 字典类型ID
     */
    protected void validateDictType(SystemDictType systemDictType) {
        if (ObjUtil.isNull(systemDictType)) {
            throw ServiceException.getInstance(ErrorCodeSystem.DICT_TYPE_NOT_EXIST);
        }
    }

    /**
     * 验证字典编码
     *
     * @param oldCode 旧字典编码
     * @param newCode 新字典编码
     */
    private void validateDictTypeByCode(String oldCode, String newCode) {
        if (oldCode.equals(newCode)) {
            return;
        }
        this.validateDictTypeByCode(newCode);
    }

    // ============================================== 删除参数校验 ==============================================

    /**
     * 校验字典类型删除参数
     *
     * @param ids             ids集合
     * @param systemDictTypes 字典编码集合
     */
    protected void validateDeleteParams(List<SystemDictType> systemDictTypes, List<Long> ids) {
        // 验证字典类型是否存在
        this.validateDictTypes(systemDictTypes);
        // 验证字典类型集合和 ids 是否匹配
        this.validateDictTypeByIds(systemDictTypes, ids);
    }

    private void validateDictTypes(List<SystemDictType> systemDictTypes) {
        // 校验字典类型集合是否存在
        if (CollUtil.isEmpty(systemDictTypes)) {
            throw ServiceException.getInstance(ErrorCodeSystem.DICT_TYPE_NOT_EXIST);
        }
    }

    private void validateDictTypeByIds(List<SystemDictType> systemDictTypes, List<Long> ids) {
        // 校验不存在的字典类型ID
        List<Long> dictTypeIds = systemDictTypes.stream().map(SystemDictType::getId).collect(Collectors.toList());
        Collection<Long> errorIds = CollectionUtil.subtract(ids, dictTypeIds);
        if (CollUtil.isNotEmpty(errorIds)) {
            String errorMsg = "请确认字典类型主键 " + errorIds + " 是否存在";
            throw ServiceException.getInstance(ErrorCodeSystem.DICT_TYPE_NOT_EXIST.getCode(), errorMsg);
        }
    }
}
