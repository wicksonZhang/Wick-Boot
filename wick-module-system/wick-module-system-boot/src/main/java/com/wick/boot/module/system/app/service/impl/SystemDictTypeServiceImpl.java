package com.wick.boot.module.system.app.service.impl;

import cn.hutool.core.util.ObjUtil;
import com.wick.boot.module.system.model.entity.SystemDictType;
import com.wick.boot.module.system.convert.SystemDictConvert;
import com.wick.boot.module.system.mapper.ISystemDictTypeMapper;
import com.wick.boot.module.system.model.dto.SystemDictTypeDTO;
import com.wick.boot.module.system.model.vo.QueryDictTypePageReqVO;
import com.wick.boot.module.system.app.service.ISystemDictTypeService;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wick.boot.common.core.result.PageResult;
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
