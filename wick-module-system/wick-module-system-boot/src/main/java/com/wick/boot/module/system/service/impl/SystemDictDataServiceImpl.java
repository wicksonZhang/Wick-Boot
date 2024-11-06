package com.wick.boot.module.system.service.impl;

import cn.hutool.core.collection.CollUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
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
import org.springframework.transaction.support.TransactionSynchronization;
import org.springframework.transaction.support.TransactionSynchronizationManager;

import javax.annotation.Resource;
import java.util.ArrayList;
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
        // Step 1: 验证新增字典数据参数是否合法
        this.validateAddParams(reqVO);

        // Step 2: 新增字典数据并插入数据库
        SystemDictData systemDictData = SystemDictDataConvert.INSTANCE.addVoToEntity(reqVO);
        this.dictDataMapper.insert(systemDictData);

        // Step 3: 将字典数据缓存到 Redis 中
        cacheDictTypeToRedis(reqVO);
        return systemDictData.getId();
    }

    /**
     * 缓存字典数据到 Redis
     *
     * @param reqVO 字典数据添加请求对象
     */
    private void cacheDictTypeToRedis(SystemDictDataAddVO reqVO) {
        String redisKey = GlobalCacheConstants.getDictCodeKey(reqVO.getDictCode());
        SystemDictOptionsDTO dictOptionsDTO = redisService.getCacheObject(redisKey);

        // 如果缓存对象为空，则初始化字典选项对象
        if (dictOptionsDTO == null) {
            dictOptionsDTO = new SystemDictOptionsDTO();
        }

        // 获取字典数据列表，如果为空则初始化列表
        List<SystemDictOptionsDTO.DictData> dictDataList = dictOptionsDTO.getDictDataList();
        if (dictDataList == null) {
            dictDataList = new ArrayList<>();
            dictOptionsDTO.setDictDataList(dictDataList);
        }

        // 将新的字典数据添加到字典数据列表中
        dictDataList.add(new SystemDictOptionsDTO.DictData(reqVO.getValue(), reqVO.getLabel(), reqVO.getTagType()));
        // 更新 Redis 缓存
        redisService.setCacheObject(redisKey, dictOptionsDTO);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(SystemDictDataUpdateVO reqVO) {
        // Step 1: 验证字典数据更新参数是否合法
        this.validateUpdateParams(reqVO);

        // Step 2: 更新字典数据到数据库
        SystemDictData systemDictData = SystemDictDataConvert.INSTANCE.updateVoToEntity(reqVO);
        this.dictDataMapper.updateById(systemDictData);

        // Step 3: 注册事务完成后缓存更新操作
        registerPostCommitCacheUpdate(systemDictData.getDictCode());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void removeSystemDictData(List<Long> ids) {
        // Step 1: 获取待删除的字典数据列表，并验证参数是否合法
        List<SystemDictData> dictDataList = getDictDataList(ids);
        this.validateDeleteParams(dictDataList, ids);

        // Step 2: 批量删除字典数据
        this.dictDataMapper.deleteBatchIds(ids);

        // Step 3: 收集待更新的字典编码，去重以避免重复操作
        Set<String> dictCodesToUpdate = dictDataList.stream()
                .map(SystemDictData::getDictCode)
                .collect(Collectors.toSet());

        // Step 4: 注册事务完成后缓存更新操作
        dictCodesToUpdate.forEach(this::registerPostCommitCacheUpdate);
    }

    /**
     * 根据字典数据 ID 列表获取字典数据
     *
     * @param ids 字典数据 ID 列表
     * @return 字典数据列表
     */
    private List<SystemDictData> getDictDataList(List<Long> ids) {
        List<SystemDictData> systemDictDataList = this.dictDataMapper.selectBatchIds(ids);

        // 校验字典数据是否存在
        if (CollUtil.isEmpty(systemDictDataList)) {
            throw ServiceException.getInstance(ErrorCodeSystem.DICT_DATA_NOT_EXIST);
        }
        return systemDictDataList;
    }

    /**
     * 在事务提交后更新或删除 Redis 缓存的字典数据
     *
     * @param dictCode 字典编码
     */
    private void registerPostCommitCacheUpdate(String dictCode) {
        TransactionSynchronizationManager.registerSynchronization(new TransactionSynchronization() {
            @Override
            public void afterCommit() {
                updateOrRemoveRedisCache(dictCode);
            }
        });
    }

    /**
     * 更新或删除 Redis 缓存的字典数据
     *
     * @param dictCode 字典编码
     */
    private void updateOrRemoveRedisCache(String dictCode) {
        String redisKey = GlobalCacheConstants.getDictCodeKey(dictCode);
        List<SystemDictOptionsDTO> optionsList = dictTypeMapper.selectSystemDictOptions(dictCode);

        if (CollUtil.isNotEmpty(optionsList)) {
            // 如果数据库查询结果不为空空，则删除 Redis 中的缓存数据
            redisService.deleteObject(redisKey);
        }

        // 更新 Redis 缓存
        redisService.setCacheObject(redisKey, optionsList.get(0));

    }

    @Override
    public SystemDictDataDTO getSystemDictData(Long id) {
        // Step 1: 根据 ID 获取字典数据
        SystemDictData systemDictData = this.dictDataMapper.selectById(id);

        // 转换并返回字典数据传输对象
        return SystemDictDataConvert.INSTANCE.entityToDictDataDTO(systemDictData);
    }

    @Override
    public List<OptionDTO<String>> getSystemDictDataListOptions(String dictCode) {
        // Step 1: 根据字典编码获取字典数据选项列表
        List<SystemDictData> systemDictTypes = this.dictDataMapper.selectDictDataOption(dictCode);

        // Step 2: 转换并返回字典选项结果集
        return SystemDictDataConvert.INSTANCE.entityToDictDataOptions(systemDictTypes);
    }

    @Override
    public PageResult<SystemDictDataDTO> getSystemDictDataPage(SystemDictDataQueryVO reqVO) {
        // Step 1: 校验字典类型是否存在
        SystemDictType dictType = dictTypeMapper.selectDictTypeByCode(reqVO.getDictCode());
        if (dictType == null) {
            return PageResult.empty();
        }

        // Step 2: 分页查询字典数据
        Page<SystemDictData> pageResult = dictDataMapper.selectDictDataPage(
                new Page<>(reqVO.getPageNumber(), reqVO.getPageSize()),
                reqVO.getName(), reqVO.getDictCode()
        );

        // 若查询结果为空，则返回空结果集
        if (pageResult == null) {
            return PageResult.empty();
        }

        // Step 3: 转换并返回分页结果集
        List<SystemDictDataDTO> dictDataDTOS = SystemDictTypeConvert.INSTANCE.entityToDictDataDTOS(pageResult.getRecords());
        return new PageResult<>(dictDataDTOS, pageResult.getTotal());
    }
}
