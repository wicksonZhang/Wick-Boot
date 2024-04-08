package cn.wickson.security.system.app.service.impl;

import cn.hutool.core.util.ObjUtil;
import cn.wickson.security.commons.result.PageResult;
import cn.wickson.security.system.app.service.ISystemDictTypeService;
import cn.wickson.security.system.convert.SystemDictConvert;
import cn.wickson.security.system.mapper.ISystemDictTypeMapper;
import cn.wickson.security.system.model.dto.SystemDictTypeDTO;
import cn.wickson.security.system.model.entity.SystemDictType;
import cn.wickson.security.system.model.vo.QueryDictTypePageReqVO;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * 后台管理 - 字典类型信息
 *
 * @author ZhangZiHeng
 * @date 2024-04-08
 */
@Service
public class SystemDictTypeServiceImpl extends ServiceImpl<ISystemDictTypeMapper, SystemDictType> implements ISystemDictTypeService {

    @Resource
    private ISystemDictTypeMapper dictTypeMapper;

    @Override
    public PageResult<SystemDictTypeDTO> getDictTypePage(QueryDictTypePageReqVO reqVO) {
        Page<SystemDictType> pageResult = this.dictTypeMapper.selectDictTypePage(
                new Page<>(reqVO.getPageNumber(), reqVO.getPageSize()),
                reqVO.getName(), reqVO.getCode()
        );

        if (ObjUtil.isNull(pageResult)) {
            return PageResult.empty();
        }

        List<SystemDictTypeDTO> dictTypeDTOS = SystemDictConvert.INSTANCE.entityToDictTypeDTOS(pageResult.getRecords());
        return new PageResult<>(dictTypeDTOS, pageResult.getTotal());
    }

    @Override
    public SystemDictType getDictTypeByCode(String typeCode) {
        return this.dictTypeMapper.selectDictTypeByCode(typeCode);
    }
}
