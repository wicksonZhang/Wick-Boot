package com.wick.boot.module.system.app.service;

import cn.hutool.core.util.ObjUtil;
import com.wick.boot.common.core.exception.ServiceException;
import com.wick.boot.module.system.enums.ErrorCodeSystem;
import com.wick.boot.module.system.mapper.ISystemDictDataMapper;
import com.wick.boot.module.system.mapper.ISystemDictTypeMapper;
import com.wick.boot.module.system.model.entity.SystemDictType;
import com.wick.boot.module.system.model.vo.dict.type.AddDictTypeReqVO;
import com.wick.boot.module.system.model.vo.dict.type.UpdateDictTypeReqVO;

import javax.annotation.Resource;

/**
 * 字典类型-防腐层
 *
 * @author ZhangZiHeng
 * @date 2024-04-15
 */
public abstract class AbstractSystemDictTypeAppService {

    @Resource
    protected ISystemDictTypeMapper dictTypeMapper;

    @Resource
    protected ISystemDictDataMapper dictDataMapper;

    // ============================================== 新增参数校验 ==============================================

    /**
     * 字典类型新增参数
     *
     * @param reqVO 新增请求参数
     */
    protected void validateAddParams(AddDictTypeReqVO reqVO) {
        // 验证字典编码是否存在
        this.validateDictTypeByCode(reqVO.getCode());
    }

    /**
     * 验证字典编码是否存在
     *
     * @param code 字典编码
     */
    private void validateDictTypeByCode(String code) {
        SystemDictType dictType = this.dictTypeMapper.selectDictTypeByCode(code);
        if (ObjUtil.isNotNull(dictType)) {
            throw ServiceException.getInstance(ErrorCodeSystem.DICT_TYPE_CODE_ALREADY_EXIST);
        }
    }

    // ============================================== 更新参数校验 ==============================================

    /**
     * 字典类型更新参数
     *
     * @param reqVO 更新请求参数
     */
    protected void validateUpdateParams(SystemDictType systemDictType, UpdateDictTypeReqVO reqVO) {
        // 验证字典类型是否存在
        this.validateDictType(systemDictType);
        // 验证字典编码是否存在
        this.validateDictTypeByCode(systemDictType.getCode(), reqVO.getCode());
    }

    /**
     * 验证字典类型是否存在
     *
     * @param systemDictType 字典类型ID
     * @return SystemDictType 字典类型
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


}
