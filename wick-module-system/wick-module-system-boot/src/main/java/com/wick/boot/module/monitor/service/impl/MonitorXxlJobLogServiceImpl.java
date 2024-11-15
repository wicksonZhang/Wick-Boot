package com.wick.boot.module.monitor.service.impl;

import cn.hutool.http.HttpStatus;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.dtflys.forest.http.ForestResponse;
import com.wick.boot.common.core.constant.GlobalResultCodeConstants;
import com.wick.boot.common.core.exception.ServiceException;
import com.wick.boot.common.core.model.dto.OptionDTO;
import com.wick.boot.common.core.result.PageResult;
import com.wick.boot.common.xxl.job.api.ApiXxlJobLogService;
import com.wick.boot.common.xxl.job.model.entity.XxlJobInfo;
import com.wick.boot.common.xxl.job.model.entity.XxlJobLog;
import com.wick.boot.common.xxl.job.model.vo.joblog.XxlJobLogQueryVO;
import com.wick.boot.module.monitor.convert.MonitorXxlJobLogConvert;
import com.wick.boot.module.monitor.model.dto.joblog.MonitorXxlJobLogDTO;
import com.wick.boot.module.monitor.model.vo.joblog.MonitorXxlJobLogQueryVO;
import com.wick.boot.module.monitor.service.MonitorXxlJobLogService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.compress.utils.Lists;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Optional;

/**
 * 调度日志管理-服务实现类
 *
 * @author Wickson
 * @date 2024-11-15 14:22
 */
@Slf4j
@Service
public class MonitorXxlJobLogServiceImpl implements MonitorXxlJobLogService {

    @Resource
    private ApiXxlJobLogService jobLogService;

    @Override
    public PageResult<MonitorXxlJobLogDTO> getMonitorXxlJobLogPage(MonitorXxlJobLogQueryVO queryParams) {
        // Step-1: 类型转换
        XxlJobLogQueryVO queryVO = MonitorXxlJobLogConvert.INSTANCE.toQueryVO(queryParams);

        // Step-2: 发送请求
        ForestResponse<String> response = this.jobLogService.getPage(queryVO);
        validateResponse(response);

        // Step-3: 解析数据
        String content = response.getContent();
        JSONObject jsonObject = JSONUtil.parseObj(content);

        // Step-4: 提取总记录数
        int totalRecords = (int) Optional.ofNullable(jsonObject.get("recordsTotal")).orElse(0);

        // Step-5: 提取数据列表并转换类型
        List<XxlJobLog> jobLogList = jsonObject.getJSONArray("data").toList(XxlJobLog.class);

        // Step-6: 转换为 DTO 并构建分页结果
        List<MonitorXxlJobLogDTO> resultList = MonitorXxlJobLogConvert.INSTANCE.convertToDTOList(jobLogList);
        return new PageResult<>(resultList, (long) totalRecords);
    }

    @Override
    public List<OptionDTO<Integer>> getJobsByGroup(Long jobGroup) {
        ForestResponse<String> response = jobLogService.getJobsByGroup(Math.toIntExact(jobGroup));
        validateResponse(response);

        String content = response.getContent();
        JSONObject jsonObject = JSONUtil.parseObj(content);
        try {
            List<XxlJobInfo> jobList = jsonObject.getJSONArray("content").toList(XxlJobInfo.class);
            return MonitorXxlJobLogConvert.INSTANCE.toDtoList(jobList);
        } catch (Exception exception) {
            log.error("Failed to parse content:{}" + exception.getMessage());
        }
        return Lists.newArrayList();
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
