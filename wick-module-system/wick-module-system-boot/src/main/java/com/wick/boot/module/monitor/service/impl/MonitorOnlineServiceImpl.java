package com.wick.boot.module.monitor.service.impl;

import cn.hutool.core.util.ObjUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.http.useragent.UserAgent;
import cn.hutool.http.useragent.UserAgentUtil;
import com.wick.boot.common.core.constant.GlobalCacheConstants;
import com.wick.boot.common.core.result.PageResult;
import com.wick.boot.common.core.utils.ServletUtils;
import com.wick.boot.common.redis.service.RedisService;
import com.wick.boot.module.monitor.convert.MonitorOnlineConvert;
import com.wick.boot.module.monitor.model.dto.MonitorOnlineDTO;
import com.wick.boot.module.monitor.model.vo.MonitorOnlineQueryVO;
import com.wick.boot.module.monitor.service.MonitorOnlineService;
import com.wick.boot.module.system.enums.LoginLogTypeEnum;
import com.wick.boot.module.system.enums.LoginResultEnum;
import com.wick.boot.module.system.model.dto.LoginLogReqDTO;
import com.wick.boot.module.system.model.dto.LoginUserInfoDTO;
import com.wick.boot.module.system.model.dto.dept.SystemDeptOptionsDTO;
import com.wick.boot.module.system.service.SystemDeptService;
import com.wick.boot.module.system.service.SystemLoginLogService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;
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

    @Resource
    private SystemLoginLogService loginLogService;

    @Override
    public PageResult<MonitorOnlineDTO> getMonitorOnlinePage(MonitorOnlineQueryVO query) {
        // 获取所有登录令牌的缓存键集合
        Collection<String> tokenKeys = redisService.keys(GlobalCacheConstants.getLoginAccessToken("*"));
        List<MonitorOnlineDTO> onlineMonitors = new ArrayList<>();

        Map<Long, String> map = deptService.getSystemDeptOptionsListAll().stream()
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

        // 根据查询条件过滤和排序
        List<MonitorOnlineDTO> filteredMonitors = onlineMonitors.stream()
                .filter(monitor -> matchesFilter(monitor, query.getUsername(), query.getLoginAddress()))
                .sorted(Comparator.comparing(MonitorOnlineDTO::getLoginDate).reversed())
                .collect(Collectors.toList());

        // 构建分页结果
        // 分页查询参数
        int pageNumber = query.getPageNumber();
        int pageSize = query.getPageSize();
        int totalRecords = filteredMonitors.size();
        // 确保起始索引不为负
        int fromIndex = Math.max(0, (pageNumber - 1) * pageSize);
        int toIndex = Math.min(fromIndex + pageSize, totalRecords);
        List<MonitorOnlineDTO> pagedResult = filteredMonitors.subList(fromIndex, toIndex);
        return new PageResult<>(pagedResult, (long) totalRecords);
    }

    /**
     * 判断 MonitorOnlineDTO 是否符合过滤条件
     */
    private boolean matchesFilter(MonitorOnlineDTO monitor, String usernameFilter, String loginAddressFilter) {
        boolean matchesUsername = (StrUtil.isEmptyIfStr(usernameFilter) || monitor.getUsername().startsWith(usernameFilter));
        boolean matchesLoginAddress = (StrUtil.isEmptyIfStr(loginAddressFilter) || monitor.getLoginAddress().startsWith(loginAddressFilter));
        return matchesUsername && matchesLoginAddress;
    }

    @Override
    public void forceQuit(String sessionId) {
        // 判断当前 sessionId 是否存在
        String key = GlobalCacheConstants.getLoginAccessToken(sessionId);
        LoginUserInfoDTO userInfoDTO = redisService.getCacheObject(key);
        // 如果为 null 终止
        if (ObjUtil.isNull(userInfoDTO)) {
            return;
        }
        // 记录强制退出日志
        LoginLogReqDTO logReqDTO = getLoginLog(userInfoDTO);
        loginLogService.createLoginLog(logReqDTO);
        // 删除 key
        redisService.deleteObject(key);
    }

    private LoginLogReqDTO getLoginLog(LoginUserInfoDTO userInfoDTO) {
        // 用户 IP
        String clientIp = ServletUtils.getClientIP();
        // 用户地址
        String loginLocation = ServletUtils.getRealAddressByIP(clientIp);
        // 浏览器信息
        String header = ServletUtils.getUserAgent();
        UserAgent userAgent = UserAgentUtil.parse(header);
        // 获取客户端操作系统
        String os = userAgent.getOs().getName();
        // 获取客户端浏览器
        String browser = userAgent.getBrowser().getName();
        LoginLogReqDTO reqDTO = new LoginLogReqDTO();
        reqDTO.setLogType(LoginLogTypeEnum.LOGOUT_DELETE.getType());
        reqDTO.setUserId(userInfoDTO.getUserId());
        reqDTO.setUserName(userInfoDTO.getUsername());
        reqDTO.setUserIp(ServletUtils.getClientIP());
        reqDTO.setLoginLocation(loginLocation);
        reqDTO.setResult(LoginResultEnum.SUCCESS.getResult());
        reqDTO.setUserAgent(browser);
        reqDTO.setOs(os);
        return reqDTO;
    }
}
