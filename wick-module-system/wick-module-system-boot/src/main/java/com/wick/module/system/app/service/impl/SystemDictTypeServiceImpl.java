package com.wick.module.system.app.service.impl;

import cn.hutool.core.util.ObjUtil;
import com.wick.module.system.convert.SystemDictConvert;
import com.wick.module.system.mapper.ISystemDictTypeMapper;
import com.wick.module.system.model.dto.SystemDictTypeDTO;
import com.wick.module.system.model.entity.SystemDictType;
import com.wick.module.system.model.vo.QueryDictTypePageReqVO;
import com.wick.module.system.app.service.ISystemDictTypeService;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wick.common.core.result.PageResult;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * 字典类型管理-服务实现层
 *
 * @author ZhangZiHeng
 * @date 2024-04-08
 */
@Service
public class SystemDictTypeServiceImpl implements ISystemDictTypeService {

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
