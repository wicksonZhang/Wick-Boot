package com.wick.boot.module.system.app.service.impl;

import cn.hutool.core.util.ObjUtil;
import com.wick.boot.module.system.model.entity.SystemDictData;
import com.wick.boot.module.system.model.entity.SystemDictType;
import com.wick.boot.module.system.app.service.ISystemDictDataService;
import com.wick.boot.module.system.app.service.ISystemDictTypeService;
import com.wick.boot.module.system.convert.SystemDictConvert;
import com.wick.boot.module.system.mapper.ISystemDictDataMapper;
import com.wick.boot.module.system.model.dto.SystemDictDataDTO;
import com.wick.boot.module.system.model.vo.dict.data.QueryDictDataPageReqVO;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wick.boot.common.core.result.PageResult;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * 字典数据管理-服务实现层
 *
 * @author ZhangZiHeng
 * @date 2024-04-08
 */
@Service
public class SystemDictDataServiceImpl implements ISystemDictDataService {

    @Resource
    private ISystemDictTypeService dictTypeService;

    @Resource
    private ISystemDictDataMapper dictDataMapper;

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
        List<SystemDictDataDTO> dictDataDTOS = SystemDictConvert.INSTANCE.entityToDictDataDTOS(pageResult.getRecords());
        return new PageResult<>(dictDataDTOS, pageResult.getTotal());
    }
}
