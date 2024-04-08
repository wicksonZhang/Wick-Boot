package cn.wickson.security.system.app.service.impl;

import cn.wickson.security.commons.result.PageResult;
import cn.wickson.security.system.app.service.ISystemDictTypeService;
import cn.wickson.security.system.mapper.ISystemDictTypeMapper;
import cn.wickson.security.system.model.dto.SystemDictTypeDTO;
import cn.wickson.security.system.model.entity.SystemDictType;
import cn.wickson.security.system.model.vo.QueryDictTypePageReqVO;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author ZhangZiHeng
 * @date 2024-04-08
 */
@Service
public class SystemDictTypeServiceImpl extends ServiceImpl<ISystemDictTypeMapper, SystemDictType> implements ISystemDictTypeService {

    @Resource
    private ISystemDictTypeMapper dictTypeMapper;

    @Override
    public PageResult<SystemDictTypeDTO> getDictTypePage(QueryDictTypePageReqVO reqVO) {

        return null;
    }

}
