package com.wick.boot.module.system.service.impl;

import cn.hutool.core.util.ObjUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wick.boot.common.core.result.PageResult;
import com.wick.boot.module.system.model.vo.dictdata.SystemDictDataAddVO;
import com.wick.boot.module.system.model.vo.dictdata.SystemDictDataUpdateVO;
import com.wick.boot.module.system.service.SystemDictDataAbstractService;
import com.wick.boot.module.system.service.SystemDictDataService;
import com.wick.boot.module.system.service.SystemDictTypeService;
import com.wick.boot.module.system.convert.SystemDictDataConvert;
import com.wick.boot.module.system.convert.SystemDictTypeConvert;
import com.wick.boot.module.system.model.dto.dictdata.SystemDictDataDTO;
import com.wick.boot.module.system.model.dto.dictdata.SystemDictOptionsDTO;
import com.wick.boot.module.system.model.entity.SystemDictData;
import com.wick.boot.module.system.model.entity.SystemDictType;
import com.wick.boot.module.system.model.vo.dictdata.SystemDictDataQueryVO;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

/**
 * 字典数据管理-服务实现层
 *
 * @author Wickson
 * @date 2024-04-08
 */
@Service
public class SystemDictDataServiceImpl extends SystemDictDataAbstractService implements SystemDictDataService {

    @Resource
    private SystemDictTypeService dictTypeService;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Long add(SystemDictDataAddVO reqVO) {
        /* Step-1: 验证新增字典数据 */
        this.validateAddParams(reqVO);

        /* Step-2: 新增字典数据信息 */
        SystemDictData systemDictData = SystemDictDataConvert.INSTANCE.addVoToEntity(reqVO);
        this.dictDataMapper.insert(systemDictData);
        return systemDictData.getId();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(SystemDictDataUpdateVO reqVO) {
        /* Step-1: 验证字典类型是否正确 */
        this.validateUpdateParams(reqVO);

        /* Step-2: 更新字典数据信息 */
        SystemDictData systemDictData = SystemDictDataConvert.INSTANCE.updateVoToEntity(reqVO);
        this.dictDataMapper.updateById(systemDictData);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void removeSystemDictData(List<Long> ids) {
        /* Step-1: 验证删除参数 */
        this.validateDeleteParams(ids);

        /* Step-2: 批量删除数据 */
        this.dictDataMapper.deleteBatchIds(ids);
    }

    @Override
    public SystemDictDataDTO getSystemDictData(Long id) {
        /* Step-1: 获取字典数据 */
        SystemDictData systemDictData = this.dictDataMapper.selectById(id);

        return SystemDictDataConvert.INSTANCE.entityToDictDataDTO(systemDictData);
    }

    @Override
    public List<SystemDictOptionsDTO<String>> getSystemDictDataListOptions(String typeCode) {
        /* Step-1: 获取字典类型列表 */
        List<SystemDictData> systemDictTypes = this.dictDataMapper.selectDictDataOption(typeCode);
        /* Step-2: 返回结果集 */
        return SystemDictDataConvert.INSTANCE.entityToDictDataOptions(systemDictTypes);
    }

    @Override
    public PageResult<SystemDictDataDTO> getSystemDictDataPage(SystemDictDataQueryVO reqVO) {
        /* Step-1: 判断数据类型是否存在 */
        SystemDictType dictType = dictTypeService.getDictTypeByCode(reqVO.getCode());
        if (ObjUtil.isNull(dictType)) {
            return PageResult.empty();
        }

        /* Step-2: 查询字典数据 */
        Page<SystemDictData> pageResult = dictDataMapper.selectDictDataPage(
                new Page<>(reqVO.getPageNumber(), reqVO.getPageSize()),
                reqVO.getName(), reqVO.getCode()
        );
        if (ObjUtil.isNull(pageResult)) {
            return PageResult.empty();
        }

        /* Step-3: 返回结果集 */
        List<SystemDictDataDTO> dictDataDTOS = SystemDictTypeConvert.INSTANCE.entityToDictDataDTOS(pageResult.getRecords());
        return new PageResult<>(dictDataDTOS, pageResult.getTotal());
    }
}
