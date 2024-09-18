package com.wick.boot.module.system.service;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.ObjUtil;
import com.wick.boot.common.core.exception.ServiceException;
import com.wick.boot.module.system.enums.ErrorCodeSystem;
import com.wick.boot.module.system.mapper.SystemDictDataMapper;
import com.wick.boot.module.system.mapper.SystemDictTypeMapper;
import com.wick.boot.module.system.model.entity.SystemDictData;
import com.wick.boot.module.system.model.entity.SystemDictType;
import com.wick.boot.module.system.model.vo.dict.data.SystemDictDataAddVO;
import com.wick.boot.module.system.model.vo.dict.data.SystemDictDataUpdateVO;

import javax.annotation.Resource;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 字典数据 - 防腐层
 *
 * @author Wickson
 * @date 2024-04-16
 */
public abstract class SystemDictDataAbstractService {

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
    protected void validateAddParams(SystemDictDataAddVO reqVO) {
        // 验证字典类型
        this.validateDictDataByCode(reqVO.getTypeCode());
        // 验证当前字典类型下是否存在字典标签
        this.validateDictDataByName(reqVO.getTypeCode(), reqVO.getName());
        // 验证当前字典类型下是否存在字典键值
        this.validateDictDataByValue(reqVO.getTypeCode(), reqVO.getValue());
    }

    /**
     * 校验字典编码是否存在
     *
     * @param typeCode 字典编码
     */
    private void validateDictDataByCode(String typeCode) {
        SystemDictType dictType = this.dictTypeMapper.selectDictTypeByCode(typeCode);
        if (ObjUtil.isNull(dictType)) {
            throw ServiceException.getInstance(ErrorCodeSystem.DICT_TYPE_CODE_NOT_EXIST);
        }
    }

    /**
     * 验证当前字典类型下是否存在字典标签
     *
     * @param typeCode 字典编码
     * @param name     字典标签
     */
    private void validateDictDataByName(String typeCode, String name) {
        SystemDictData dictData = this.dictDataMapper.selectDictDataByName(typeCode, name);
        if (ObjUtil.isNotNull(dictData)) {
            throw ServiceException.getInstance(ErrorCodeSystem.DICT_DATA_LABEL_ALREADY_EXIST);
        }
    }

    /**
     * 验证当前字典类型下是否存在字典键值
     *
     * @param typeCode 字典编码
     * @param value    字典键值
     */
    private void validateDictDataByValue(String typeCode, String value) {
        SystemDictData dictData = this.dictDataMapper.selectDictDataByValue(typeCode, value);
        if (ObjUtil.isNotNull(dictData)) {
            throw ServiceException.getInstance(ErrorCodeSystem.DICT_DATA_VALUE_ALREADY_EXIST);
        }
    }

    // ============================================== 更新参数校验 ==============================================

    /**
     * 校验字典数据更新参数
     *
     * @param reqVO 字典数据更新参数
     */
    protected void validateUpdateParams(SystemDictDataUpdateVO reqVO) {
        // 验证字典数据是否存在
        SystemDictData systemDictData = this.validateDictData(reqVO.getId());
        // 验证字典类型
        this.validateDictDataByCode(systemDictData.getDictType(), reqVO.getTypeCode());
        // 验证字典标签
        this.validateDictDataByName(systemDictData.getLabel(), reqVO.getTypeCode(), reqVO.getName());
        // 验证字典键值
        this.validateDictDataByValue(systemDictData.getValue(), reqVO.getTypeCode(), reqVO.getValue());
    }

    /**
     * 验证字典数据
     *
     * @param id 字典数据主键ID
     */
    private SystemDictData validateDictData(Long id) {
        SystemDictData systemDictData = this.dictDataMapper.selectById(id);
        if (ObjUtil.isNull(systemDictData)) {
            throw ServiceException.getInstance(ErrorCodeSystem.DICT_DATA_NOT_EXIST);
        }
        return systemDictData;
    }

    /**
     * 校验字典编码
     *
     * @param oldTypeCode 旧字典编码
     * @param newTypeCode 新字典编码
     */
    private void validateDictDataByCode(String oldTypeCode, String newTypeCode) {
        if (oldTypeCode.equals(newTypeCode)) {
            return;
        }
        this.validateDictDataByCode(newTypeCode);
    }

    /**
     * 验证当前字典类型下是否存在字典标签
     *
     * @param oldName     旧字典名称
     * @param newTypeCode 字典编码
     * @param newName     新字典名称
     */
    private void validateDictDataByName(String oldName, String newTypeCode, String newName) {
        if (newName.equals(oldName)) {
            return;
        }
        this.validateDictDataByName(newTypeCode, newName);
    }

    /**
     * 验证当前字典类型下是否存在字典键值
     *
     * @param oldValue    旧字典键值
     * @param newTypeCode 字典编码
     * @param newValue    新字典键值
     */
    private void validateDictDataByValue(String oldValue, String newTypeCode, String newValue) {
        if (oldValue.equals(newValue)) {
            return;
        }
        this.validateDictDataByValue(newTypeCode, newValue);
    }

    // ============================================== 删除参数校验 ==============================================

    /**
     * 校验字典数据删除参数
     *
     * @param ids ids集合
     */
    protected void validateDeleteParams(List<Long> ids) {
        // 验证字典类型是否存在
        List<SystemDictData> systemDictDataList = this.validateDictDataList(ids);
        // 验证字典类型集合和 ids 是否匹配
        this.validateDictDataByIds(systemDictDataList, ids);
    }

    private List<SystemDictData> validateDictDataList(List<Long> ids) {
        /* Step-1: 验证删除参数 */
        List<SystemDictData> systemDictDataList = this.dictDataMapper.selectBatchIds(ids);
        // 校验字典类型集合是否存在
        if (CollUtil.isEmpty(systemDictDataList)) {
            throw ServiceException.getInstance(ErrorCodeSystem.DICT_DATA_NOT_EXIST);
        }
        return systemDictDataList;
    }

    private void validateDictDataByIds(List<SystemDictData> systemDictDataList, List<Long> ids) {
        // 校验不存在的字典类型ID
        List<Long> dictDataIds = systemDictDataList.stream().map(SystemDictData::getId).collect(Collectors.toList());
        Collection<Long> errorIds = CollectionUtil.subtract(ids, dictDataIds);
        if (CollUtil.isNotEmpty(errorIds)) {
            String errorMsg = "请确认字典数据主键 " + errorIds + " 是否存在";
            throw ServiceException.getInstance(ErrorCodeSystem.DICT_DATA_NOT_EXIST.getCode(), errorMsg);
        }
    }
}
