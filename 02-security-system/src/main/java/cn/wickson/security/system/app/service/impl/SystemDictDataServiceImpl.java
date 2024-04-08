package cn.wickson.security.system.app.service.impl;

import cn.hutool.core.util.ObjUtil;
import cn.wickson.security.commons.result.PageResult;
import cn.wickson.security.system.app.service.ISystemDictDataService;
import cn.wickson.security.system.app.service.ISystemDictTypeService;
import cn.wickson.security.system.convert.SystemDictConvert;
import cn.wickson.security.system.mapper.ISystemDictDataMapper;
import cn.wickson.security.system.model.dto.SystemDictDataDTO;
import cn.wickson.security.system.model.entity.SystemDictData;
import cn.wickson.security.system.model.entity.SystemDictType;
import cn.wickson.security.system.model.vo.QueryDictDataPageReqVO;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author ZhangZiHeng
 * @date 2024-04-08
 */
@Service
public class SystemDictDataServiceImpl extends ServiceImpl<ISystemDictDataMapper, SystemDictData> implements ISystemDictDataService {

    @Resource
    private ISystemDictTypeService dictTypeService;

    @Resource
    ISystemDictDataMapper dictDataMapper;

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
