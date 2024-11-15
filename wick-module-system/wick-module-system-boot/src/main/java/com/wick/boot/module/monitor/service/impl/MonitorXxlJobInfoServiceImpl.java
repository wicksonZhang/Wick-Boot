package com.wick.boot.module.monitor.service.impl;

import cn.hutool.http.HttpStatus;
import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.dtflys.forest.http.ForestResponse;
import com.wick.boot.common.core.constant.GlobalResultCodeConstants;
import com.wick.boot.common.core.enums.CommonStatusEnum;
import com.wick.boot.common.core.exception.ServiceException;
import com.wick.boot.common.core.result.PageResult;
import com.wick.boot.common.xxl.job.api.ApiXxlJobInfoService;
import com.wick.boot.common.xxl.job.model.entity.XxlJobInfo;
import com.wick.boot.common.xxl.job.model.vo.jobinfo.XxlJobInfoQueryVO;
import com.wick.boot.module.monitor.convert.MonitorXxlJobInfoConvert;
import com.wick.boot.module.monitor.model.dto.jobinfo.MonitorXxlJobInfoDTO;
import com.wick.boot.module.monitor.model.vo.jobinfo.MonitorXxlJobInfoAddVO;
import com.wick.boot.module.monitor.model.vo.jobinfo.MonitorXxlJobInfoQueryVO;
import com.wick.boot.module.monitor.model.vo.jobinfo.MonitorXxlJobInfoTriggerVO;
import com.wick.boot.module.monitor.model.vo.jobinfo.MonitorXxlJobInfoUpdateVO;
import com.wick.boot.module.monitor.service.MonitorXxlJobInfoAbstractService;
import com.wick.boot.module.monitor.service.MonitorXxlJobInfoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * 定时任务管理管理-服务实现类
 *
 * @author Wickson
 * @date 2024-11-13 10:24
 */
@Slf4j
@Service
public class MonitorXxlJobInfoServiceImpl extends MonitorXxlJobInfoAbstractService implements MonitorXxlJobInfoService {

    @Resource
    private ApiXxlJobInfoService jobInfoService;

    /**
     * 新增定时任务管理数据
     *
     * @param reqVO 新增请求参数
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void addMonitorXxlJobInfo(MonitorXxlJobInfoAddVO reqVO) {
        // Step-1: 校验新增参数
        this.validateAddParams(reqVO);

        // Step-2: 转换请求参数为实体对象
        XxlJobInfo xxlJobInfo = MonitorXxlJobInfoConvert.INSTANCE.addVoToEntity(reqVO);

        // Step-3: 调用外部服务保存定时任务信息，并验证返回结果
        ForestResponse<String> response = this.jobInfoService.add(xxlJobInfo);
        validateResponse(response);
    }

    /**
     * 更新定时任务管理数据
     *
     * @param reqVO 更新请求参数
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateMonitorXxlJobInfo(MonitorXxlJobInfoUpdateVO reqVO) {
        // Step-1: 校验更新参数
        this.validateUpdateParams(reqVO);

        // Step-2: 转换请求参数为实体对象
        XxlJobInfo xxlJobInfo = MonitorXxlJobInfoConvert.INSTANCE.updateVoToEntity(reqVO);

        // Step-3: 调用外部服务更新定时任务信息，并验证返回结果
        ForestResponse<String> response = this.jobInfoService.update(xxlJobInfo);
        validateResponse(response);
    }

    /**
     * 删除定时任务管理数据
     *
     * @param ids 主键集合
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteMonitorXxlJobInfo(List<Long> ids) {
        // Step-1: 遍历ID集合，删除定时任务管理信息
        ids.forEach(id -> this.jobInfoService.delete(Math.toIntExact(id)));
    }

    @Override
    public void updateStatus(Integer id, Integer status) {
        CommonStatusEnum statusEnum = CommonStatusEnum.getStatus(status);
        switch (statusEnum) {
            case ENABLE:
                ForestResponse<String> startResponse = this.jobInfoService.start(id);
                validateResponse(startResponse);
                break;
            case DISABLE:
                ForestResponse<String> stopResponse = this.jobInfoService.stop(id);
                validateResponse(stopResponse);
                break;
        }
    }

    @Override
    public void executeTrigger(MonitorXxlJobInfoTriggerVO addVO) {
        ForestResponse<String> response = this.jobInfoService.trigger(addVO.getId(), addVO.getExecutorParam(), addVO.getAddressList());
        validateResponse(response);
    }

    /**
     * 获取定时任务管理分页数据
     *
     * @param queryParams 分页查询参数
     * @return MonitorXxlJobInfoDTO 定时任务管理DTO
     */
    @SuppressWarnings("unchecked")
    public PageResult<MonitorXxlJobInfoDTO> getMonitorXxlJobInfoPage(MonitorXxlJobInfoQueryVO queryParams) {
        // Step-1: 将查询参数转换为服务端请求的类型
        XxlJobInfoQueryVO xxlJobInfoQueryVO = MonitorXxlJobInfoConvert.INSTANCE.toQueryVO(queryParams);

        // Step-2: 发送API请求获取分页数据
        ForestResponse<Map<String, Object>> response = this.jobInfoService.getMonitorJobPage(xxlJobInfoQueryVO);

        // Step-3: 如果请求失败，返回空的分页结果
        if (response.getStatusCode() != HttpStatus.HTTP_OK) {
            log.error("获取定时任务分页数据失败，状态码: {}", response.getStatusCode());
            return PageResult.empty();
        }

        // Step-4: 提取响应内容，并解析为Map对象
        Map<String, Object> responseMap = Optional.ofNullable(response.getContent())
                .map(content -> JSONUtil.toBean(content, HashMap.class))
                .orElseGet(HashMap::new);

        // Step-5: 获取总记录数
        int totalRecords = (int) Optional.ofNullable(responseMap.get("recordsTotal")).orElse(0);

        // Step-6: 获取数据列表并转换为XxlJobInfo实体对象列表
        JSONArray array = JSONUtil.parseArray(responseMap.get("data"));
        List<XxlJobInfo> jobInfoList = array.stream()
                .map(item -> JSONUtil.toBean((JSONObject) item, XxlJobInfo.class))
                .collect(Collectors.toList());

        // Step-7: 将实体列表转换为DTO列表并返回分页结果
        List<MonitorXxlJobInfoDTO> monitorXxlJobInfoPages = MonitorXxlJobInfoConvert.INSTANCE.entityToPage(jobInfoList);
        return new PageResult<>(monitorXxlJobInfoPages, (long) totalRecords);
    }

    /**
     * 响应验证方法
     * 用于统一验证外部服务的响应状态码是否为OK。
     *
     * @param response 外部服务返回的响应对象
     */
    private static void validateResponse(ForestResponse<String> response) {
        int statusCode = response.statusCode();
        // 检查响应状态码是否为HTTP OK (200)，如果不是则抛出服务异常
        if (HttpStatus.HTTP_OK != statusCode) {
            log.error("服务请求失败，状态码: {}", statusCode);
            throw ServiceException.getInstance(GlobalResultCodeConstants.FAIL);
        }
    }
}
