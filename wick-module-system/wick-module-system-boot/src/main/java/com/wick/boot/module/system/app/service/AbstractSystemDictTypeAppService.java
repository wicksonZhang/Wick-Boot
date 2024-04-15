package com.wick.boot.module.system.app.service;

import cn.hutool.core.util.ObjUtil;
import com.wick.boot.common.core.exception.ServiceException;
import com.wick.boot.module.system.enums.ErrorCodeSystem;
import com.wick.boot.module.system.mapper.ISystemDictTypeMapper;
import com.wick.boot.module.system.model.entity.SystemDictType;
import com.wick.boot.module.system.model.vo.AddDictTypeReqVO;

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

    /**
     * 字典类型新增参数
     *
     * @param reqVO 请求参数
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

}
