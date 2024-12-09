package com.wick.boot.module.system.service.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ObjUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wick.boot.common.core.constant.GlobalCacheConstants;
import com.wick.boot.common.core.constant.GlobalConstants;
import com.wick.boot.common.core.result.PageResult;
import com.wick.boot.common.redis.service.RedisService;
import com.wick.boot.module.system.convert.SystemDictTypeConvert;
import com.wick.boot.module.system.model.dto.dictdata.SystemDictOptionsDTO;
import com.wick.boot.module.system.model.dto.dicttype.SystemDictTypeDTO;
import com.wick.boot.module.system.model.entity.SystemDictData;
import com.wick.boot.module.system.model.entity.SystemDictType;
import com.wick.boot.module.system.model.vo.dicttype.SystemDictTypeAddVO;
import com.wick.boot.module.system.model.vo.dicttype.SystemDictTypeQueryVO;
import com.wick.boot.module.system.model.vo.dicttype.SystemDictTypeUpdateVO;
import com.wick.boot.module.system.service.SystemDictTypeAbstractService;
import com.wick.boot.module.system.service.SystemDictTypeService;
import org.springframework.context.annotation.DependsOn;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.Collection;
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
@DependsOn(GlobalConstants.FLYWAY_INITIALIZER)
public class SystemDictTypeServiceImpl extends SystemDictTypeAbstractService implements SystemDictTypeService {

    @Resource
    private RedisService redisService;

    /**
     * 初始化字典数据到缓存中
     */
    @PostConstruct
    private void initDict() {
        refreshCache();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Long addDictType(SystemDictTypeAddVO reqVO) {
        // Step 1: 验证字典类型参数
        this.validateAddParams(reqVO);

        // Step 2: 新增字典信息到数据库
        SystemDictType dictType = SystemDictTypeConvert.INSTANCE.addVoToEntity(reqVO);
        this.dictTypeMapper.insert(dictType);

        // Step 3: 新增字典信息到 Redis 缓存
        cacheDictTypeToRedis(reqVO.getName(), reqVO.getDictCode());

        return dictType.getId();
    }

    private void cacheDictTypeToRedis(String name, String dictCode) {
        String redisKey = GlobalCacheConstants.getDictCodeKey(dictCode);
        SystemDictOptionsDTO optionsDTO = new SystemDictOptionsDTO(name, dictCode);
        redisService.setCacheObject(redisKey, optionsDTO);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateDictType(SystemDictTypeUpdateVO reqVO) {
        // Step 1: 验证更新字典类型参数
        SystemDictType oldSystemDictType = this.dictTypeMapper.selectById(reqVO.getId());
        this.validateUpdateParams(oldSystemDictType, reqVO);

        // Step 2: 更新字典类型信息到数据库
        SystemDictType newSystemDictType = SystemDictTypeConvert.INSTANCE.updateVoToEntity(reqVO);
        this.dictTypeMapper.updateById(newSystemDictType);
        updateDictDataByCode(newSystemDictType.getDictCode(), oldSystemDictType.getDictCode());

        // Step 3: 更新关联字典数据并更新 Redis 缓存
        updateRedisCache(newSystemDictType, oldSystemDictType);
    }

    private void updateDictDataByCode(String newDictCode, String oldDictCode) {
        // 字典代码相同，无需更新数据
        if (newDictCode.equals(oldDictCode)) {
            return;
        }

        // 更新字典数据中的字典代码
        List<SystemDictData> dictDataList = getDictDataByTypeCode(Collections.singletonList(oldDictCode));
        if (CollUtil.isEmpty(dictDataList)) {
            return;
        }

        dictDataList.forEach(dictData -> dictData.setDictCode(newDictCode));
        this.dictDataMapper.updateBatch(dictDataList);
    }

    private void updateRedisCache(SystemDictType newDict, SystemDictType oldDict) {
        // 字典代码和名称相同，无需更新
        if (newDict.getDictCode().equals(oldDict.getDictCode()) && newDict.getName().equals(oldDict.getName())) {
            return;
        }

        // 删除旧的缓存
        String oldRedisKey = GlobalCacheConstants.getDictCodeKey(oldDict.getDictCode());
        SystemDictOptionsDTO optionsDTO = redisService.getCacheObject(oldRedisKey);
        redisService.deleteObject(oldRedisKey);

        // 新增新的缓存信息
        String newRedisKey = GlobalCacheConstants.getDictCodeKey(newDict.getDictCode());
        optionsDTO.setDictCode(newDict.getDictCode());
        optionsDTO.setName(newDict.getName());
        redisService.setCacheObject(newRedisKey, optionsDTO);
    }

    private List<SystemDictData> getDictDataByTypeCode(List<String> dictCodes) {
        return this.dictDataMapper.selectList(
                new LambdaQueryWrapper<SystemDictData>().in(SystemDictData::getDictCode, dictCodes)
        );
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void removeSystemDictType(List<Long> ids) {
        // Step 1: 验证删除参数
        List<SystemDictType> systemDictTypes = this.dictTypeMapper.selectBatchIds(ids);
        this.validateDeleteParams(systemDictTypes, ids);

        // Step 2: 删除从表数据并删除主表数据
        List<String> dictCodeList = systemDictTypes.stream().map(SystemDictType::getDictCode).collect(Collectors.toList());
        List<SystemDictData> dictDataList = getDictDataByTypeCode(dictCodeList);

        if (CollUtil.isNotEmpty(dictDataList)) {
            this.dictDataMapper.deleteBatchIds(dictDataList);
        }
        this.dictTypeMapper.deleteBatchIds(ids);

        // Step 3: 删除 Redis 缓存
        dictCodeList.forEach(dictCode -> redisService.deleteObject(GlobalCacheConstants.getDictCodeKey(dictCode)));
    }

    @Override
    public SystemDictTypeDTO getSystemDictType(Long id) {
        // 根据 ID 获取字典类型数据并转换为 DTO 返回
        SystemDictType systemDictType = this.dictTypeMapper.selectById(id);
        return SystemDictTypeConvert.INSTANCE.entityToDictTypeDTO(systemDictType);
    }

    @Override
    public PageResult<SystemDictTypeDTO> getSystemDictTypePage(SystemDictTypeQueryVO reqVO) {
        // 分页查询字典类型数据并转换为 DTO 列表返回
        Page<SystemDictType> pageResult = this.dictTypeMapper.selectDictTypePage(
                new Page<>(reqVO.getPageNumber(), reqVO.getPageSize()),
                reqVO.getName(), reqVO.getCode()
        );

        if (ObjUtil.isNull(pageResult)) {
            return PageResult.empty();
        }

        List<SystemDictTypeDTO> dictTypeList = SystemDictTypeConvert.INSTANCE.entityToDictTypeDTOS(pageResult.getRecords());
        return new PageResult<>(dictTypeList, pageResult.getTotal());
    }

    @Override
    public SystemDictType getDictTypeByCode(String typeCode) {
        // 根据字典代码查询字典类型数据
        return this.dictTypeMapper.selectDictTypeByCode(typeCode);
    }

    @Override
    public List<SystemDictOptionsDTO> getSystemDictTypeList() {
        // 获取所有字典数据的缓存列表
        Collection<String> keys = redisService.keys(GlobalCacheConstants.getDictCodeKey("*"));
        List<SystemDictOptionsDTO> resultList = keys.stream()
                .map(key -> redisService.<SystemDictOptionsDTO>getCacheObject(key))
                .collect(Collectors.toList());

        if (CollUtil.isEmpty(resultList)) {
            // 若缓存为空，则从数据库加载数据
            return this.dictTypeMapper.selectSystemDictOptions(null);
        }

        return resultList;
    }

    @Override
    public void refreshCache() {
        // 清空缓存并重新加载字典数据到缓存
        Collection<String> keys = redisService.keys(GlobalCacheConstants.getDictCodeKey("*"));
        redisService.deleteObject(keys);

        List<SystemDictOptionsDTO> optionsList = getSystemDictTypeList();
        optionsList.forEach(option -> {
            String redisKey = GlobalCacheConstants.getDictCodeKey(option.getDictCode());
            redisService.setCacheObject(redisKey, option);
        });
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateStatus(Integer id, Integer status) {
        // Step 1: 校验字典状态参数
        SystemDictType dictType = this.dictTypeMapper.selectById(id);
        validateDictType(dictType);

        // Step 2: 更新字典状态
        dictType.setStatus(status);
        this.dictTypeMapper.updateById(dictType);
    }
}
