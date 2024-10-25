package com.wick.boot.module.monitor.service.impl;

import com.wick.boot.common.core.constant.GlobalCacheConstants;
import com.wick.boot.common.core.result.PageResult;
import com.wick.boot.common.core.utils.ServletUtils;
import com.wick.boot.common.redis.service.RedisService;
import com.wick.boot.module.monitor.convert.MonitorOnlineConvert;
import com.wick.boot.module.monitor.model.dto.MonitorOnlineDTO;
import com.wick.boot.module.monitor.model.vo.MonitorOnlineQueryVO;
import com.wick.boot.module.monitor.service.MonitorOnlineService;
import com.wick.boot.module.system.model.dto.LoginUserInfoDTO;
import com.wick.boot.module.system.model.dto.dept.SystemDeptOptionsDTO;
import com.wick.boot.module.system.service.SystemDeptService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 在线用户-服务实现类
 *
 * @author Wickson
 * @date 2024-10-25
 */
@Slf4j
@Service
public class MonitorOnlineServiceImpl implements MonitorOnlineService {

    @Resource
    private RedisService redisService;

    @Resource
    private SystemDeptService deptService;

    @Override
    public PageResult<MonitorOnlineDTO> getMonitorOnlinePage(MonitorOnlineQueryVO query) {
        // 获取所有登录令牌的缓存键集合
        Collection<String> tokenKeys = redisService.keys(GlobalCacheConstants.getLoginAccessToken("*"));
        List<MonitorOnlineDTO> onlineMonitors = new ArrayList<>();

        Map<Long, String> map = deptService.getSystemDeptOptionsList().stream()
                .collect(Collectors.toMap(SystemDeptOptionsDTO::getValue, SystemDeptOptionsDTO::getLabel));

        // 遍历每个缓存键，获取对应的登录监控信息
        tokenKeys.forEach(tokenKey -> {
            LoginUserInfoDTO loginUserInfo = redisService.getCacheObject(tokenKey);
            MonitorOnlineDTO onlineMonitor = MonitorOnlineConvert.INSTANCE.toDTO(loginUserInfo);
            // 设置对应的 sessionId
            String sessionId = tokenKey.replace(GlobalCacheConstants.getLoginAccessToken(""), "");
            onlineMonitor.setSessionId(sessionId);
            // 设置部门信息
            onlineMonitor.setDeptName(map.get(loginUserInfo.getDeptId()));
            // 通过IP地址获取真实的登录位置，并设置到对象中
            onlineMonitor.setLoginAddress(ServletUtils.getRealAddressByIP(onlineMonitor.getLoginIp()));
            onlineMonitors.add(onlineMonitor);
        });

        // 分页查询参数
        int pageNumber = query.getPageNumber();
        int pageSize = query.getPageSize();
        int totalRecords = onlineMonitors.size();
        int fromIndex = Math.max(0, (pageNumber - 1) * pageSize);  // 确保起始索引不为负
        int toIndex = Math.min(fromIndex + pageSize, totalRecords);

        // 构建分页结果
        List<MonitorOnlineDTO> pagedResult = onlineMonitors.subList(fromIndex, toIndex);
        return new PageResult<>(pagedResult, (long) totalRecords);
    }


}
