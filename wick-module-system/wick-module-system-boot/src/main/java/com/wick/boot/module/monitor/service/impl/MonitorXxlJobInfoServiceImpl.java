package com.wick.boot.module.monitor.service.impl;

import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.dtflys.forest.http.ForestResponse;
import com.dtflys.forest.http.HttpStatus;
import com.wick.boot.common.core.result.PageResult;
import com.wick.boot.common.xxl.job.api.ApiXxlJobInfoService;
import com.wick.boot.common.xxl.job.model.entity.XxlJobInfo;
import com.wick.boot.common.xxl.job.model.vo.jobinfo.XxlJobInfoQueryVO;
import com.wick.boot.module.monitor.convert.MonitorXxlJobInfoConvert;
import com.wick.boot.module.monitor.model.dto.jobinfo.MonitorXxlJobInfoDTO;
import com.wick.boot.module.monitor.model.vo.jobinfo.MonitorXxlJobInfoAddVO;
import com.wick.boot.module.monitor.model.vo.jobinfo.MonitorXxlJobInfoQueryVO;
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
        /* Step-1: 校验新增参数 */
        this.validateAddParams(reqVO);

        /* Step-2: 转换实体类型 */
        XxlJobInfo monitorXxlJobInfo = MonitorXxlJobInfoConvert.INSTANCE.addVoToEntity(reqVO);

        /* Step-3: 保存定时任务管理信息 */
    }

    /**
     * 更新定时任务管理数据
     *
     * @param reqVO 更新请求参数
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateMonitorXxlJobInfo(MonitorXxlJobInfoUpdateVO reqVO) {
        /* Step-1: 校验更新参数 */
        this.validateUpdateParams(reqVO);

        /* Step-2: 转换实体类型 */
        XxlJobInfo monitorXxlJobInfo = MonitorXxlJobInfoConvert.INSTANCE.updateVoToEntity(reqVO);

        /* Step-3: 更新定时任务管理信息 */
    }

    /**
     * 删除定时任务管理数据
     *
     * @param ids 主键集合
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteMonitorXxlJobInfo(List<Long> ids) {
        /* Step-1: 校验删除参数 */

        /* Step-2: 删除定时任务管理信息 */
    }

    /**
     * 获取定时任务管理分页数据
     *
     * @param queryParams 分页查询参数
     * @return MonitorXxlJobInfoDTO 定时任务管理DTO
     */
    @SuppressWarnings("unchecked")
    public PageResult<MonitorXxlJobInfoDTO> getMonitorXxlJobInfoPage(MonitorXxlJobInfoQueryVO queryParams) {
        // Step-1: 类型转换
        XxlJobInfoQueryVO xxlJobInfoQueryVO = MonitorXxlJobInfoConvert.INSTANCE.toQueryVO(queryParams);

        // Step-2: 发送api
        ForestResponse<Map<String, Object>> response = this.jobInfoService.getMonitorJobPage(xxlJobInfoQueryVO);
        if (response.getStatusCode() != HttpStatus.OK) {
            return PageResult.empty();
        }

        // Step-4: 提取总记录数
        Map<String, Object> responseMap = Optional.ofNullable(response.getContent())
                .map(content -> JSONUtil.toBean(content, HashMap.class))
                .orElseGet(HashMap::new);

        // Step-5: 提取总记录数
        int totalRecords = (int) Optional.ofNullable(responseMap.get("recordsTotal")).orElse(0);

        // Step-6: 提取数据列表并转换类型
        JSONArray array = JSONUtil.parseArray(responseMap.get("data"));
        List<XxlJobInfo> jobInfoList = array.stream()
                .map(item -> JSONUtil.toBean((JSONObject) item, XxlJobInfo.class))
                .collect(Collectors.toList());

        List<MonitorXxlJobInfoDTO> monitorXxlJobInfoPages = MonitorXxlJobInfoConvert.INSTANCE.entityToPage(jobInfoList);
        return new PageResult<>(monitorXxlJobInfoPages, (long) totalRecords);
    }

}