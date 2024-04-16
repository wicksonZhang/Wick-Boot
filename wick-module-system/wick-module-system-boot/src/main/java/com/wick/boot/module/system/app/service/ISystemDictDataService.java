package com.wick.boot.module.system.app.service;

import com.wick.boot.module.system.model.dto.SystemDictDataDTO;
import com.wick.boot.module.system.model.vo.dict.data.AddDictDataReqVO;
import com.wick.boot.module.system.model.vo.dict.data.QueryDictDataPageReqVO;
import com.wick.boot.common.core.result.PageResult;

/**
 * 字典数据管理-服务层
 *
 * @author ZhangZiHeng
 * @date 2024-04-08
 */
public interface ISystemDictDataService {

    /**
     * 新增字典数据
     *
     * @param reqVO 新增自带你数据请求参数
     * @return 字典数据主键ID
     */
    Long addDictData(AddDictDataReqVO reqVO);

    /**
     * 获取字典数据分页
     *
     * @param reqVO 字典数据请求参数
     * @return PageResult<SystemDictDataDTO>
     */
    PageResult<SystemDictDataDTO> getDictDataPage(QueryDictDataPageReqVO reqVO);

}
