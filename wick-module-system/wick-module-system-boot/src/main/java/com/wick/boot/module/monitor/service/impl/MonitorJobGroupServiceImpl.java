package com.wick.boot.module.monitor.service.impl;

import cn.hutool.http.HttpStatus;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.dtflys.forest.http.ForestResponse;
import com.wick.boot.common.core.constant.GlobalResultCodeConstants;
import com.wick.boot.common.core.exception.ServiceException;
import com.wick.boot.common.core.model.dto.OptionDTO;
import com.wick.boot.common.core.result.PageResult;
import com.wick.boot.common.xxl.job.api.ApiXxlJobGroupService;
import com.wick.boot.common.xxl.job.api.ApiXxlJobLoginService;
import com.wick.boot.common.xxl.job.model.entity.XxlJobGroup;
import com.wick.boot.common.xxl.job.model.vo.jobgroup.XxlJobGroupQueryVO;
import com.wick.boot.module.monitor.convert.MonitorJobGroupConvert;
import com.wick.boot.module.monitor.model.dto.jobgroup.MonitorJobGroupDTO;
import com.wick.boot.module.monitor.model.vo.jobgroup.MonitorJobGroupAddVO;
import com.wick.boot.module.monitor.model.vo.jobgroup.MonitorJobGroupQueryVO;
import com.wick.boot.module.monitor.model.vo.jobgroup.MonitorJobGroupUpdateVO;
import com.wick.boot.module.monitor.service.MonitorJobGroupAbstractService;
import com.wick.boot.module.monitor.service.MonitorJobService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.List;
import java.util.Optional;

/**
 * 执行器管理-服务实现类
 *
 * @author Wickson
 * @date 2024-11-07 13:48
 */
@Slf4j
@Service
public class MonitorJobGroupServiceImpl extends MonitorJobGroupAbstractService implements MonitorJobService {

    @Value("${xxl.job.enabled}")
    private Boolean enabled;

    @Value("${xxl.job.admin.userName}")
    private String userName;

    @Value("${xxl.job.admin.password}")
    private String password;

    @Resource
    private ApiXxlJobGroupService xxlJobGroupService;

    @Resource
    private ApiXxlJobLoginService loginService;

    @PostConstruct
    private void login() {
        if (!enabled) {
            return;
        }
        ForestResponse<String> response = loginService.login(userName, password);
        if (response.getStatusCode() == HttpStatus.HTTP_OK) {
            log.info("Xxl-Job Login Success...");
        }
    }

    /**
     * 新增执行器管理数据
     *
     * @param reqVO 新增请求参数
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void addMonitorJobGroup(MonitorJobGroupAddVO reqVO) {
        /* Step-1: 校验新增参数 */
        this.validateAddParams(reqVO);

        XxlJobGroup xxlJobGroup = MonitorJobGroupConvert.INSTANCE.convertAddVoToEntity(reqVO);
        ForestResponse<String> response = xxlJobGroupService.addMonitorJobGroup(xxlJobGroup);
        validateResponse(response);
    }

    /**
     * 更新执行器管理数据
     *
     * @param reqVO 更新请求参数
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateMonitorJobGroup(MonitorJobGroupUpdateVO reqVO) {
        /* Step-1: 校验更新参数 */
        this.validateUpdateParams(reqVO);

        /* Step-2: 转换实体类型 */
        XxlJobGroup xxlJobGroup = MonitorJobGroupConvert.INSTANCE.convertUpdateVoToEntity(reqVO);

        /* Step-3: 更新执行器管理信息 */
        ForestResponse<String> response = xxlJobGroupService.updateMonitorJobGroup(xxlJobGroup);
        validateResponse(response);
    }

    private static void validateResponse(ForestResponse<String> response) {
        int statusCode = response.statusCode();
        if (HttpStatus.HTTP_OK != statusCode) {
            throw ServiceException.getInstance(GlobalResultCodeConstants.FAIL);
        }
    }

    /**
     * 删除执行器管理数据
     *
     * @param ids 主键集合
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteMonitorJobGroup(List<Long> ids) {
        /* Step-1: 删除执行器管理信息 */
        ids.forEach(id -> this.xxlJobGroupService.deleteMonitorJobGroup(Math.toIntExact(id)));
    }

    /**
     * 获取执行器管理分页数据
     *
     * @return MonitorJobGroupDTO 执行器管理DTO
     */
    public PageResult<MonitorJobGroupDTO> getMonitorJobPage(MonitorJobGroupQueryVO queryParams) {
        // Step-1: 类型转换
        XxlJobGroupQueryVO queryVO = MonitorJobGroupConvert.INSTANCE.convertToQueryVo(queryParams);

        // Step-2: 调用服务获取分页数据
        ForestResponse<String> response = xxlJobGroupService.getMonitorJobGroupPage(queryVO);
        validateResponse(response);

        // Step-3: 解析响应内容为JSONObject
        JSONObject jsonObject = JSONUtil.parseObj(response.getContent());

        // Step-4: 提取总记录数
        int totalRecords = (int) Optional.ofNullable(jsonObject.get("recordsTotal")).orElse(0);

        // Step-5: 提取数据列表并转换类型
        List<XxlJobGroup> jobGroupList = jsonObject.getJSONArray("data").toList(XxlJobGroup.class);

        // Step-6: 转换为 DTO 并构建分页结果
        List<MonitorJobGroupDTO> resultList = MonitorJobGroupConvert.INSTANCE.convertToDTOList(jobGroupList);
        return new PageResult<>(resultList, (long) totalRecords);
    }

    @Override
    public List<OptionDTO<Integer>> getMonitorJobList() {
        XxlJobGroupQueryVO queryVO = new XxlJobGroupQueryVO().setStart(0).setLength(500);

        // Step-2: 调用服务获取分页数据
        ForestResponse<String> response = xxlJobGroupService.getMonitorJobGroupPage(queryVO);
        validateResponse(response);

        // Step-3: 解析响应内容为Map并处理异常
        JSONObject jsonObject = JSONUtil.parseObj(response.getContent());

        // Step-4: 提取数据列表并转换类型
        List<XxlJobGroup> jobGroupList = jsonObject.getJSONArray("data").toList(XxlJobGroup.class);
        return MonitorJobGroupConvert.INSTANCE.convertToOptionDTOList(jobGroupList);
    }
}