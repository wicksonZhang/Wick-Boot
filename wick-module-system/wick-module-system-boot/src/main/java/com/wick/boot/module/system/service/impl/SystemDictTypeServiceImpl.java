package com.wick.boot.module.system.service.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ObjUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wick.boot.common.core.result.PageResult;
import com.wick.boot.module.system.model.vo.dicttype.SystemDictTypeAddVO;
import com.wick.boot.module.system.model.vo.dicttype.SystemDictTypeUpdateVO;
import com.wick.boot.module.system.service.SystemDictTypeAbstractService;
import com.wick.boot.module.system.service.SystemDictTypeService;
import com.wick.boot.module.system.convert.SystemDictTypeConvert;
import com.wick.boot.module.system.model.dto.dictdata.SystemDictOptionsDTO;
import com.wick.boot.module.system.model.dto.dicttype.SystemDictTypeDTO;
import com.wick.boot.module.system.model.entity.SystemDictData;
import com.wick.boot.module.system.model.entity.SystemDictType;
import com.wick.boot.module.system.model.vo.dicttype.SystemDictTypeQueryVO;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 字典类型管理-服务实现层
 *
 * @author Wickson
 * @date 2024-04-08
 */
@Service
public class SystemDictTypeServiceImpl extends SystemDictTypeAbstractService implements SystemDictTypeService {

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Long addDictType(SystemDictTypeAddVO reqVO) {
        /* Step-1: 验证字典类型是否正确 */
        this.validateAddParams(reqVO);

        /* Step-2: 新增字典信息 */
        SystemDictType dictType = SystemDictTypeConvert.INSTANCE.addVoToEntity(reqVO);
        this.dictTypeMapper.insert(dictType);
        return dictType.getId();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateDictType(SystemDictTypeUpdateVO reqVO) {
        /* Step-1: 验证字典类型是否正确 */
        SystemDictType oldSystemDictType = this.dictTypeMapper.selectById(reqVO.getId());
        this.validateUpdateParams(oldSystemDictType, reqVO);

        /* Step-2: 更新字典类型、字典数据信息 */
        // 更新字典类型
        SystemDictType newSystemDictType = SystemDictTypeConvert.INSTANCE.updateVoToEntity(reqVO);
        this.dictTypeMapper.updateById(newSystemDictType);
        // 更新字典数据
        this.updateDictDateByCode(newSystemDictType.getDictCode(), oldSystemDictType.getDictCode());
    }

    private void updateDictDateByCode(String newDictCode, String oldDictCode) {
        // 如果更新的 code 是一致的，则不用更新
        if (newDictCode.equals(oldDictCode)) {
            return;
        }
        // 通过 code 获取 system_dict_data 所有数据
        List<SystemDictData> dictDataList = getDictDataByTypeCode(Collections.singletonList(oldDictCode));
        if (CollUtil.isEmpty(dictDataList)) {
            return;
        }
        // 批量更新字典数据
        dictDataList.forEach(dictData -> dictData.setDictCode(newDictCode));
        this.dictDataMapper.updateBatch(dictDataList);
    }

    private List<SystemDictData> getDictDataByTypeCode(List<String> dictCodes) {
        return this.dictDataMapper.selectList(
                new LambdaQueryWrapper<SystemDictData>().in(SystemDictData::getDictCode, dictCodes)
        );
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void removeSystemDictType(List<Long> ids) {
        /* Step-1: 验证删除参数 */
        List<SystemDictType> systemDictTypes = this.dictTypeMapper.selectBatchIds(ids);
        this.validateDeleteParams(systemDictTypes, ids);

        /* Step-2: 先删除从表 system_dict_data, 在删除主表 system_dict_type */
        // 删除从表
        List<String> codes = systemDictTypes.stream().map(SystemDictType::getDictCode).collect(Collectors.toList());
        List<SystemDictData> dictDataList = getDictDataByTypeCode(codes);
        if (CollUtil.isNotEmpty(dictDataList)) {
            this.dictDataMapper.deleteBatchIds(dictDataList);
        }
        // 删除主表
        this.dictTypeMapper.deleteBatchIds(ids);
    }

    @Override
    public SystemDictTypeDTO getSystemDictType(Long id) {
        /* Step-1: 通过ID获取字典类型数据 */
        SystemDictType systemDictType = this.dictTypeMapper.selectById(id);
        return SystemDictTypeConvert.INSTANCE.entityToDictTypeDTO(systemDictType);
    }

    @Override
    public PageResult<SystemDictTypeDTO> getSystemDictTypePage(SystemDictTypeQueryVO reqVO) {
        Page<SystemDictType> pageResult = this.dictTypeMapper.selectDictTypePage(
                new Page<>(reqVO.getPageNumber(), reqVO.getPageSize()),
                reqVO.getName(), reqVO.getCode()
        );

        if (ObjUtil.isNull(pageResult)) {
            return PageResult.empty();
        }

        List<SystemDictTypeDTO> dictTypeDTOS = SystemDictTypeConvert.INSTANCE.entityToDictTypeDTOS(pageResult.getRecords());
        return new PageResult<>(dictTypeDTOS, pageResult.getTotal());
    }

    @Override
    public SystemDictType getDictTypeByCode(String typeCode) {
        return this.dictTypeMapper.selectDictTypeByCode(typeCode);
    }

    @Override
    public List<SystemDictOptionsDTO> getSystemDictTypeList() {
        return this.dictTypeMapper.selectSystemDictOptions();
    }
}
