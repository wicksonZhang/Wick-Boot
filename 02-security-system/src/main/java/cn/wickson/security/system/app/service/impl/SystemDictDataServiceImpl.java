package cn.wickson.security.system.app.service.impl;

import cn.wickson.security.commons.result.PageResult;
import cn.wickson.security.system.app.service.ISystemDictDataService;
import cn.wickson.security.system.mapper.ISystemDictDataMapper;
import cn.wickson.security.system.model.dto.SystemDictDataDTO;
import cn.wickson.security.system.model.entity.SystemDictData;
import cn.wickson.security.system.model.vo.QueryDictDataPageReqVO;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * @author ZhangZiHeng
 * @date 2024-04-08
 */
@Service
public class SystemDictDataServiceImpl extends ServiceImpl<ISystemDictDataMapper, SystemDictData> implements ISystemDictDataService {
    @Override
    public PageResult<SystemDictDataDTO> getDictDataPage(QueryDictDataPageReqVO reqVO) {
        return null;
    }
}
