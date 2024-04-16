package com.wick.boot.module.system.app.service.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ObjUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wick.boot.common.core.result.PageResult;
import com.wick.boot.module.system.app.service.AbstractSystemDictTypeAppService;
import com.wick.boot.module.system.app.service.ISystemDictTypeService;
import com.wick.boot.module.system.convert.SystemDictConvert;
import com.wick.boot.module.system.model.dto.SystemDictTypeDTO;
import com.wick.boot.module.system.model.entity.SystemDictData;
import com.wick.boot.module.system.model.entity.SystemDictType;
import com.wick.boot.module.system.model.vo.dict.type.AddDictTypeReqVO;
import com.wick.boot.module.system.model.vo.dict.type.QueryDictTypePageReqVO;
import com.wick.boot.module.system.model.vo.dict.type.UpdateDictTypeReqVO;
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
        SystemDictType dictType = SystemDictConvert.INSTANCE.addVoToEntity(reqVO);
        this.dictTypeMapper.insert(dictType);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateDictType(UpdateDictTypeReqVO reqVO) {
        /* Step-1: 验证字典类型是否正确 */
        SystemDictType oldSystemDictType = this.dictTypeMapper.selectById(reqVO.getId());
        this.validateUpdateParams(oldSystemDictType, reqVO);

        /* Step-2: 更新字典类型、字典数据信息 */
        // 更新字典类型
        SystemDictType newSystemDictType = SystemDictConvert.INSTANCE.updateVoToEntity(reqVO);
        this.dictTypeMapper.updateById(newSystemDictType);
        // 更新字典数据
        this.updateDictDateByCode(newSystemDictType.getCode(), oldSystemDictType.getCode());
    }

    private void updateDictDateByCode(String newTypeCode, String oldTypeCode) {
        if (newTypeCode.equals(oldTypeCode)) {
            return;
        }
        List<SystemDictData> systemDictData = this.dictDataMapper.selectList(
                new LambdaQueryWrapper<SystemDictData>().eq(SystemDictData::getDictType, oldTypeCode)
        );
        if (CollUtil.isEmpty(systemDictData)) {
            return;
        }
        systemDictData.forEach(dictData -> dictData.setDictType(newTypeCode));
        this.dictDataMapper.updateBatch(systemDictData);
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
