package com.wick.boot.module.system.app.service;

import cn.hutool.core.util.ObjUtil;
import com.wick.boot.common.core.exception.ServiceException;
import com.wick.boot.module.system.enums.ErrorCodeSystem;
import com.wick.boot.module.system.mapper.ISystemDictDataMapper;
import com.wick.boot.module.system.mapper.ISystemDictTypeMapper;
import com.wick.boot.module.system.model.entity.SystemDictData;
import com.wick.boot.module.system.model.entity.SystemDictType;
import com.wick.boot.module.system.model.vo.dict.data.AddDictDataReqVO;

import javax.annotation.Resource;

/**
 * 字典数据 - 防腐层
 *
 * @author ZhangZiHeng
 * @date 2024-04-16
 */
public abstract class AbstractSystemDictDataAppService {

    @Resource
    protected ISystemDictTypeMapper dictTypeMapper;

    @Resource
    protected ISystemDictDataMapper dictDataMapper;

    // ============================================== 新增参数校验 ==============================================

    /**
     * 校验字典类型新增参数
     *
     * @param reqVO 新增请求参数
     */
    protected void validateAddParams(AddDictDataReqVO reqVO) {
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

}
