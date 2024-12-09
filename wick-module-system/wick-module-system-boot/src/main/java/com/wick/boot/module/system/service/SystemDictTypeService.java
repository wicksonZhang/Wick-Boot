package com.wick.boot.module.system.service;

import com.wick.boot.common.core.result.PageResult;
import com.wick.boot.module.system.model.dto.dictdata.SystemDictOptionsDTO;
import com.wick.boot.module.system.model.dto.dicttype.SystemDictTypeDTO;
import com.wick.boot.module.system.model.entity.SystemDictType;
import com.wick.boot.module.system.model.vo.dicttype.SystemDictTypeAddVO;
import com.wick.boot.module.system.model.vo.dicttype.SystemDictTypeQueryVO;
import com.wick.boot.module.system.model.vo.dicttype.SystemDictTypeUpdateVO;

import java.util.List;

/**
 * @author Wickson
 * @date 2024-04-08
 */
public interface SystemDictTypeService {

    /**
     * 新增字典类型数据
     *
     * @param reqVO 新增请求参数
     * @return Long 字典类型主键ID
     */
    Long addDictType(SystemDictTypeAddVO reqVO);

    /**
     * 更新字典类型数据
     *
     * @param reqVO 更新请求参数
     */
    void updateDictType(SystemDictTypeUpdateVO reqVO);

    /**
     * 删除字典类型数据
     *
     * @param ids 主键id
     */
    void removeSystemDictType(List<Long> ids);

    /**
     * 通过字典ID获取字典类型数据
     *
     * @param id 字典主键ID
     * @return SystemDictTypeDTO 字典类型DTO
     */
    SystemDictTypeDTO getSystemDictType(Long id);

    /**
     * 获取字典分页信息
     *
     * @param reqVO 字典分页请求参数
     * @return PageResult<SystemDictTypeDTO>
     */
    PageResult<SystemDictTypeDTO> getSystemDictTypePage(SystemDictTypeQueryVO reqVO);

    /**
     * 获取字典类型 To Code
     *
     * @param typeCode 字典类型编码
     * @return SystemDictType
     */
    SystemDictType getDictTypeByCode(String typeCode);

    /**
     * 获取字典列表
     *
     * @return 字典列表集合
     */
    List<SystemDictOptionsDTO> getSystemDictTypeList();

    /**
     * 刷新缓存
     */
    void refreshCache();

    /**
     * 更新字典类型状态
     *
     * @param id     字典类型主键ID
     * @param status 状态
     */
    void updateStatus(Integer id, Integer status);
}
