package com.wick.boot.module.system.app.service.impl;

import cn.hutool.core.util.ObjUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wick.boot.common.core.result.PageResult;
import com.wick.boot.module.system.app.service.AbstractSystemDictTypeAppService;
import com.wick.boot.module.system.app.service.ISystemDictTypeService;
import com.wick.boot.module.system.convert.SystemDictConvert;
import com.wick.boot.module.system.model.dto.SystemDictTypeDTO;
import com.wick.boot.module.system.model.entity.SystemDictType;
import com.wick.boot.module.system.model.vo.AddDictTypeReqVO;
import com.wick.boot.module.system.model.vo.QueryDictTypePageReqVO;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 字典类型管理-服务实现层
 *
 * @author ZhangZiHeng
 * @date 2024-04-08
 */
@Service
public class SystemDictTypeServiceImpl extends AbstractSystemDictTypeAppService implements ISystemDictTypeService {

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void addDictType(AddDictTypeReqVO reqVO) {
        /* Step-1: 验证字典类型是否正确 */
        this.validateAddParams(reqVO);

        /* Step-2: 新增字典信息 */
        SystemDictType dictType = SystemDictConvert.INSTANCE.addOrUpdateVoToEntity(reqVO);
        this.dictTypeMapper.insert(dictType);
    }

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
