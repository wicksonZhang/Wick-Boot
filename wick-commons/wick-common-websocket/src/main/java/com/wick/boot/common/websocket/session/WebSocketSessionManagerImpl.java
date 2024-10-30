package com.wick.boot.common.websocket.session;

import com.wick.boot.common.core.constant.GlobalCacheConstants;
import com.wick.boot.common.redis.service.RedisService;
import com.wick.boot.module.system.api.ApiSystemDashboard;
import com.wick.boot.module.system.model.dto.LoginUserInfoDTO;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Collections;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * WebSocketSessionManager 实现类
 *
 * @author Wickson
 * @date 2024-10-28
 */
@Service
public class WebSocketSessionManagerImpl implements WebSocketSessionManager {

    @Resource
    private SimpMessagingTemplate messagingTemplate;

    @Resource
    private ApiSystemDashboard systemDashboard;

    @Resource
    private RedisService redisService;

    /**
     * id 与 WebSocketSession 映射
     * key：Session 编号
     */
    private final Set<String> idSessions = ConcurrentHashMap.newKeySet();

    @Override
    public void addSession(String sessionKey, LoginUserInfoDTO userInfo) {
        // 添加到 idSessions 中
        idSessions.add(sessionKey);

        userInfo.setDisconnected(true);
        String redisKey = GlobalCacheConstants.getLoginAccessToken(sessionKey);
        redisService.setCacheObject(redisKey, userInfo);

        // 推送数据
        messagingTemplate.convertAndSend("/topic/onlineUserCount", systemDashboard.getVisitStatsList());
    }

    @Override
    public void removeSession(String sessionKey) {
        if (idSessions.contains(sessionKey)) {
            // 移除从 idSessions 中
            idSessions.remove(sessionKey);

            // 当session断开时, 标记为断开状态
            String accessToken = GlobalCacheConstants.getLoginAccessToken(sessionKey);
            LoginUserInfoDTO userInfo = redisService.getCacheObject(accessToken);
            if (userInfo != null) {
                userInfo.setDisconnected(false);
                String redisKey = GlobalCacheConstants.getLoginAccessToken(sessionKey);
                redisService.setCacheObject(redisKey, userInfo);
            }

            // 推送数据
            messagingTemplate.convertAndSend("/topic/onlineUserCount", systemDashboard.getVisitStatsList());
        }
    }

    @Override
    public Set<String> getAllSession() {
        return Collections.unmodifiableSet(idSessions);
    }
}
