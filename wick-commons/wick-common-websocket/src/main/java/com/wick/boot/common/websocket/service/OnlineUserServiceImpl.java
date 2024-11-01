package com.wick.boot.common.websocket.service;

import cn.hutool.core.collection.CollUtil;
import com.wick.boot.common.core.constant.GlobalCacheConstants;
import com.wick.boot.common.redis.service.RedisService;
import com.wick.boot.module.system.model.dto.LoginUserInfoDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Lazy;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Collection;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * OnlineUserService 实现类
 *
 * @author Wickson
 * @date 2024-10-28
 */
@Slf4j
@Service
public class OnlineUserServiceImpl implements OnlineUserService {

    /**
     * 消息模板，用于发送 WebSocket 消息
     */
    @Lazy
    @Resource
    private SimpMessagingTemplate messagingTemplate;

    /**
     * Redis 服务，用于管理用户会话和状态
     */
    @Resource
    private RedisService redisService;

    /**
     * id 与 WebSocketSession 映射
     * key：Session 编号
     */
    private final Set<String> idSessions = ConcurrentHashMap.newKeySet();

    /**
     * 在线用户数量的主题
     */
    private static final String ONLINE_USER_COUNT_TOPIC = "/topic/onlineUserCount";

    @Override
    public void addOnlineUser(String sessionKey, Boolean connected) {
        idSessions.add(sessionKey);
        updateUserConnection(sessionKey, connected);
    }

    @Override
    public void removeOnlineUser(String sessionKey, Boolean connected) {
        if (idSessions.contains(sessionKey)) {
            idSessions.remove(sessionKey);
            updateUserConnection(sessionKey, connected);
        }
    }

    /**
     * 更新用户连接状态
     *
     * @param sessionKey  会话键
     * @param isConnected 是否已连接
     */
    private void updateUserConnection(String sessionKey, boolean isConnected) {
        try {
            LoginUserInfoDTO loginUserInfoDTO = redisService.getCacheObject(sessionKey);
            if (loginUserInfoDTO != null) {
                // 更新 Redis 中的用户状态
                loginUserInfoDTO.setDisconnected(isConnected);
                long expireTime = redisService.getExpire(sessionKey);
                redisService.setCacheObject(sessionKey, loginUserInfoDTO, expireTime, TimeUnit.SECONDS);
            }
            // 广播在线用户数量
            messagingTemplate.convertAndSend(ONLINE_USER_COUNT_TOPIC, getOnlineUserCount());
        } catch (Exception e) {
            log.error("更新用户连接状态时发生错误", e);
        }
    }

    /**
     * 获取在线用户数量
     *
     * @return
     */
    @Override
    public int getOnlineUserCount() {
        AtomicInteger count = new AtomicInteger(0);
        if (CollUtil.isEmpty(idSessions)) {
            String accessToken = GlobalCacheConstants.getLoginAccessToken("*");
            Collection<String> keys = redisService.keys(accessToken);
            if (CollUtil.isEmpty(keys)) {
                return 0;
            }
            for (String key : keys) {
                LoginUserInfoDTO userInfo = redisService.getCacheObject(key);
                if (!userInfo.getDisconnected()) {
                    idSessions.add(key);
                    count.getAndIncrement();
                }
            }
        } else {
            count.set(idSessions.size());
        }
        return count.get();
    }
}
