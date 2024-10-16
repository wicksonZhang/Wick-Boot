package com.wick.boot.module.system.service;

import com.wick.boot.common.core.result.PageResult;
import com.wick.boot.module.system.model.dto.dictdata.SystemDictDataDTO;
import com.wick.boot.module.system.model.dto.dictdata.SystemDictOptionsDTO;
import com.wick.boot.module.system.model.vo.dictdata.SystemDictDataAddVO;
import com.wick.boot.module.system.model.vo.dictdata.SystemDictDataQueryVO;
import com.wick.boot.module.system.model.vo.dictdata.SystemDictDataUpdateVO;

import java.util.List;

/**
 * 字典数据管理-服务层
 *
 * @author Wickson
 * @date 2024-04-08
 */
public interface SystemDictDataService {

    /**
     * 新增字典数据
     *
     * @param reqVO 新增字典数据请求参数
     * @return 字典数据主键ID
     */
    Long add(SystemDictDataAddVO reqVO);

    /**
     * 更新字典数据
     *
     * @param reqVO 更新字典数据请求参数
     */
    void update(SystemDictDataUpdateVO reqVO);

    /**
     * 批量删除字典数据
     *
     * @param ids 字典主键集合
     */
    void removeSystemDictData(List<Long> ids);

    /**
     * 获取字典类型数据ById
     *
     * @param id 主键ID
     * @return SystemDictDataDTO
     */
    SystemDictDataDTO getSystemDictData(Long id);

    /**
     * 获取字典选项
     *
     * @param typeCode 字典Code
     * @return List<SystemDictOptionsDTO>
     */
    List<SystemDictOptionsDTO<String>> getSystemDictDataListOptions(String typeCode);

    /**
     * 获取字典数据分页
     *
     * @param reqVO 字典数据请求参数
     * @return PageResult<SystemDictDataDTO>
     */
    PageResult<SystemDictDataDTO> getSystemDictDataPage(SystemDictDataQueryVO reqVO);
}
