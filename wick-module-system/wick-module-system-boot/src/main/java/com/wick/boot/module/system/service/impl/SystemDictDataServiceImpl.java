package com.wick.boot.module.system.service.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ObjUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.google.common.collect.Sets;
import com.wick.boot.common.core.constant.GlobalCacheConstants;
import com.wick.boot.common.core.exception.ServiceException;
import com.wick.boot.common.core.model.dto.OptionDTO;
import com.wick.boot.common.core.result.PageResult;
import com.wick.boot.common.redis.service.RedisService;
import com.wick.boot.module.system.convert.SystemDictDataConvert;
import com.wick.boot.module.system.convert.SystemDictTypeConvert;
import com.wick.boot.module.system.enums.ErrorCodeSystem;
import com.wick.boot.module.system.mapper.SystemDictTypeMapper;
import com.wick.boot.module.system.model.dto.dictdata.SystemDictDataDTO;
import com.wick.boot.module.system.model.dto.dictdata.SystemDictOptionsDTO;
import com.wick.boot.module.system.model.entity.SystemDictData;
import com.wick.boot.module.system.model.entity.SystemDictType;
import com.wick.boot.module.system.model.vo.dictdata.SystemDictDataAddVO;
import com.wick.boot.module.system.model.vo.dictdata.SystemDictDataQueryVO;
import com.wick.boot.module.system.model.vo.dictdata.SystemDictDataUpdateVO;
import com.wick.boot.module.system.service.SystemDictDataAbstractService;
import com.wick.boot.module.system.service.SystemDictDataService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * 字典数据管理-服务实现层
 *
 * @author Wickson
 * @date 2024-04-08
 */
@Service
public class SystemDictDataServiceImpl extends SystemDictDataAbstractService implements SystemDictDataService {

    @Resource
    private SystemDictTypeMapper dictTypeMapper;

    @Resource
    private RedisService redisService;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Long add(SystemDictDataAddVO reqVO) {
        /* Step-1: 验证新增字典数据 */
        this.validateAddParams(reqVO);

        /* Step-2: 新增字典数据信息 */
        SystemDictData systemDictData = SystemDictDataConvert.INSTANCE.addVoToEntity(reqVO);
        this.dictDataMapper.insert(systemDictData);

        // Step 3: 新增字典数据到 Redis 缓存
        cacheDictTypeToRedis(reqVO);
        return systemDictData.getId();
    }

    private void cacheDictTypeToRedis(SystemDictDataAddVO reqVO) {
        // 获取redis信息
        String redisKey = GlobalCacheConstants.getDictCodeKey(reqVO.getDictCode());
        SystemDictOptionsDTO dictOptionsDTO = redisService.getCacheObject(redisKey);
        Set<SystemDictOptionsDTO.DictData> dictDataList = dictOptionsDTO.getDictDataList();
        if (CollUtil.isEmpty(dictDataList)) {
            dictDataList = Sets.newHashSet();
            dictDataList.add(new SystemDictOptionsDTO.DictData(reqVO.getValue(), reqVO.getLabel(), reqVO.getTagType()));
        }
        // 新增 redis 信息
        redisService.setCacheObject(redisKey, dictOptionsDTO);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(SystemDictDataUpdateVO reqVO) {
        /* Step-1: 验证字典类型是否正确 */
        this.validateUpdateParams(reqVO);

        /* Step-2: 更新字典数据信息 */
        SystemDictData systemDictData = SystemDictDataConvert.INSTANCE.updateVoToEntity(reqVO);
        this.dictDataMapper.updateById(systemDictData);

        // Step-3: 更新redis的信息
        updateOrRemoveRedisCache(systemDictData.getDictCode());
    }

    private void updateOrRemoveRedisCache(String dictCode) {
        String redisKey = GlobalCacheConstants.getDictCodeKey(dictCode);
        SystemDictOptionsDTO dictOptionsDTO = dictTypeMapper.selectSystemDictOptions(dictCode).get(0);
        redisService.setCacheObject(redisKey, dictOptionsDTO);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void removeSystemDictData(List<Long> ids) {
        /* Step-1: 验证删除参数 */
        List<SystemDictData> dictDataList = getDictDataList(ids);
        this.validateDeleteParams(dictDataList, ids);

        /* Step-2: 批量删除数据 */
        this.dictDataMapper.deleteBatchIds(ids);

        // Step-3: 删除redis信息
        String dictCode = dictDataList.stream().map(SystemDictData::getDictCode).collect(Collectors.toList()).get(0);
        updateOrRemoveRedisCache(dictCode);
    }

    private List<SystemDictData> getDictDataList(List<Long> ids) {
        /* Step-1: 验证删除参数 */
        List<SystemDictData> systemDictDataList = this.dictDataMapper.selectBatchIds(ids);
        // 校验字典类型集合是否存在
        if (CollUtil.isEmpty(systemDictDataList)) {
            throw ServiceException.getInstance(ErrorCodeSystem.DICT_DATA_NOT_EXIST);
        }
        return systemDictDataList;
    }

    @Override
    public SystemDictDataDTO getSystemDictData(Long id) {
        /* Step-1: 获取字典数据 */
        SystemDictData systemDictData = this.dictDataMapper.selectById(id);

        return SystemDictDataConvert.INSTANCE.entityToDictDataDTO(systemDictData);
    }

    @Override
    public List<OptionDTO<String>> getSystemDictDataListOptions(String dictCode) {
        /* Step-1: 获取字典类型列表 */
        List<SystemDictData> systemDictTypes = this.dictDataMapper.selectDictDataOption(dictCode);
        /* Step-2: 返回结果集 */
        return SystemDictDataConvert.INSTANCE.entityToDictDataOptions(systemDictTypes);
    }

    @Override
    public PageResult<SystemDictDataDTO> getSystemDictDataPage(SystemDictDataQueryVO reqVO) {
        /* Step-1: 判断数据类型是否存在 */
        SystemDictType dictType = dictTypeMapper.selectDictTypeByCode(reqVO.getDictCode());
        if (ObjUtil.isNull(dictType)) {
            return PageResult.empty();
        }

        /* Step-2: 查询字典数据 */
        Page<SystemDictData> pageResult = dictDataMapper.selectDictDataPage(
                new Page<>(reqVO.getPageNumber(), reqVO.getPageSize()),
                reqVO.getName(), reqVO.getDictCode()
        );
        if (ObjUtil.isNull(pageResult)) {
            return PageResult.empty();
        }

        /* Step-3: 返回结果集 */
        List<SystemDictDataDTO> dictDataDTOS = SystemDictTypeConvert.INSTANCE.entityToDictDataDTOS(pageResult.getRecords());
        return new PageResult<>(dictDataDTOS, pageResult.getTotal());
    }
}
