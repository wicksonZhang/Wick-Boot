package com.wick.module.system.app.service;

import com.wick.module.system.model.dto.SystemDictTypeDTO;
import com.wick.module.system.model.entity.SystemDictType;
import com.wick.module.system.model.vo.QueryDictTypePageReqVO;
import com.wick.common.core.result.PageResult;

/**
 * @author ZhangZiHeng
 * @date 2024-04-08
 */
public interface ISystemDictTypeService {

    /**
     * 获取字典分页信息
     *
     * @param reqVO 字典分页请求参数
     * @return PageResult<SystemDictTypeDTO>
     */
    PageResult<SystemDictTypeDTO> getDictTypePage(QueryDictTypePageReqVO reqVO);

    /**
     * 获取字典类型 To Code
     *
     * @param typeCode 字典类型编码
     * @return SystemDictType
     */
    SystemDictType getDictTypeByCode(String typeCode);
}
