package cn.wickson.security.system.app.service;

import cn.wickson.security.commons.result.PageResult;
import cn.wickson.security.system.model.dto.SystemDictDataDTO;
import cn.wickson.security.system.model.vo.QueryDictDataPageReqVO;

/**
 * 字典数据管理-服务层
 *
 * @author ZhangZiHeng
 * @date 2024-04-08
 */
public interface ISystemDictDataService {

    /**
     * 获取字典数据分页
     *
     * @param reqVO 字典数据请求参数
     * @return PageResult<SystemDictDataDTO>
     */
    PageResult<SystemDictDataDTO> getDictDataPage(QueryDictDataPageReqVO reqVO);

}
