package cn.wickson.security.system.app.service;

import cn.wickson.security.commons.result.PageResult;
import cn.wickson.security.system.model.dto.SystemDictDataDTO;
import cn.wickson.security.system.model.entity.SystemDictData;
import cn.wickson.security.system.model.vo.QueryDictDataPageReqVO;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * @author ZhangZiHeng
 * @date 2024-04-08
 */
public interface ISystemDictDataService extends IService<SystemDictData> {
    PageResult<SystemDictDataDTO> getDictDataPage(QueryDictDataPageReqVO reqVO);
}
