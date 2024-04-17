package com.wick.boot.module.system.app.service.impl;

import cn.hutool.core.util.ObjUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wick.boot.common.core.result.PageResult;
import com.wick.boot.module.system.app.service.AbstractSystemDictDataAppService;
import com.wick.boot.module.system.app.service.ISystemDictDataService;
import com.wick.boot.module.system.app.service.ISystemDictTypeService;
import com.wick.boot.module.system.convert.SystemDictDataConvert;
import com.wick.boot.module.system.convert.SystemDictTypeConvert;
import com.wick.boot.module.system.model.dto.SystemDictDataDTO;
import com.wick.boot.module.system.model.entity.SystemDictData;
import com.wick.boot.module.system.model.entity.SystemDictType;
import com.wick.boot.module.system.model.vo.dict.data.AddDictDataReqVO;
import com.wick.boot.module.system.model.vo.dict.data.QueryDictDataPageReqVO;
import com.wick.boot.module.system.model.vo.dict.data.UpdateDictDataReqVO;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

/**
 * 字典数据管理-服务实现层
 *
 * @author ZhangZiHeng
 * @date 2024-04-08
 */
@Service
public class SystemDictDataServiceImpl extends AbstractSystemDictDataAppService implements ISystemDictDataService {

    @Resource
    private ISystemDictTypeService dictTypeService;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Long addDictData(AddDictDataReqVO reqVO) {
        /* Step-1: 验证新增字典数据 */
        this.validateAddParams(reqVO);

        /* Step-2: 新增字典数据信息 */
        SystemDictData systemDictData = SystemDictDataConvert.INSTANCE.addVoToEntity(reqVO);
        this.dictDataMapper.insert(systemDictData);
        return systemDictData.getId();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateDictData(UpdateDictDataReqVO reqVO) {
        /* Step-1: 验证字典类型是否正确 */
        this.validateUpdateParams(reqVO);

        /* Step-2: 更新字典数据信息 */
        SystemDictData systemDictData = SystemDictDataConvert.INSTANCE.updateVoToEntity(reqVO);
        this.dictDataMapper.updateById(systemDictData);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteDictData(List<Long> ids) {
        /* Step-1: 验证删除参数 */
        this.validateDeleteParams(ids);

        /* Step-2: 批量删除数据 */
        this.dictDataMapper.deleteBatchIds(ids);
    }

    @Override
    public SystemDictDataDTO getDictData(Long id) {
        /* Step-1: 获取字典数据 */
        SystemDictData systemDictData = this.dictDataMapper.selectById(id);

        return SystemDictDataConvert.INSTANCE.entityToDictDataDTO(systemDictData);
    }

    @Override
    public PageResult<SystemDictDataDTO> getDictDataPage(QueryDictDataPageReqVO reqVO) {
        /* Step-1: 判断数据类型是否存在 */
        SystemDictType dictType = dictTypeService.getDictTypeByCode(reqVO.getTypeCode());
        if (ObjUtil.isNull(dictType)) {
            return PageResult.empty();
        }

        /* Step-2: 查询字典数据 */
        Page<SystemDictData> pageResult = dictDataMapper.selectDictDataPage(
                new Page<>(reqVO.getPageNumber(), reqVO.getPageSize()),
                reqVO.getName(), reqVO.getTypeCode()
        );
        if (ObjUtil.isNull(pageResult)) {
            return PageResult.empty();
        }

        /* Step-3: 返回结果集 */
        List<SystemDictDataDTO> dictDataDTOS = SystemDictTypeConvert.INSTANCE.entityToDictDataDTOS(pageResult.getRecords());
        return new PageResult<>(dictDataDTOS, pageResult.getTotal());
    }
}
