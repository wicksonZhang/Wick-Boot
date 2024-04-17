package com.wick.boot.module.system.app.service;

import com.wick.boot.common.core.result.PageResult;
import com.wick.boot.module.system.model.dto.SystemDictTypeDTO;
import com.wick.boot.module.system.model.entity.SystemDictType;
import com.wick.boot.module.system.model.vo.dict.type.AddDictTypeReqVO;
import com.wick.boot.module.system.model.vo.dict.type.QueryDictTypePageReqVO;
import com.wick.boot.module.system.model.vo.dict.type.UpdateDictTypeReqVO;

import java.util.List;

/**
 * @author ZhangZiHeng
 * @date 2024-04-08
 */
public interface ISystemDictTypeService {

    /**
     * 新增字典类型数据
     *
     * @param reqVO 新增请求参数
     * @return Long 字典类型主键ID
     */
    Long addDictType(AddDictTypeReqVO reqVO);

    /**
     * 更新字典类型数据
     *
     * @param reqVO 更新请求参数
     */
    void updateDictType(UpdateDictTypeReqVO reqVO);

    /**
     * 删除字典类型数据
     *
     * @param ids 主键id
     */
    void deleteDictType(List<Long> ids);

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
