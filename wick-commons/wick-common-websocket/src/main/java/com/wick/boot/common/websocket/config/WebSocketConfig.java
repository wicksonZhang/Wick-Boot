package com.wick.boot.common.websocket.config;

import cn.hutool.core.util.StrUtil;
import com.wick.boot.common.core.constant.GlobalCacheConstants;
import com.wick.boot.common.core.constant.GlobalConstants;
import com.wick.boot.common.redis.service.RedisService;
import com.wick.boot.common.websocket.service.OnlineUserService;
import com.wick.boot.module.system.model.dto.LoginUserInfoDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.config.ChannelRegistration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.messaging.support.MessageHeaderAccessor;
import org.springframework.web.socket.client.standard.StandardWebSocketClient;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;
import org.springframework.web.socket.messaging.WebSocketStompClient;
import org.springframework.web.socket.sockjs.client.SockJsClient;
import org.springframework.web.socket.sockjs.client.Transport;
import org.springframework.web.socket.sockjs.client.WebSocketTransport;

import javax.annotation.Resource;
import java.security.Principal;
import java.util.Collections;
import java.util.List;

/**
 * WebSocket 配置类
 * 用于配置 WebSocket 服务器，处理实时消息通信
 *
 * @author Your Name
 * @since 2024-01-01
 */
@Slf4j
@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

    /**
     * Redis 服务，用于管理用户会话和状态
     */
    @Resource
    private RedisService redisService;

    /**
     * 在线用户服务，用于更新用户在线状态
     */
    @Resource
    private OnlineUserService onlineUserService;

    /**
     * 注册 STOMP 端点并配置 SockJS 支持
     *
     * @param registry STOMP 端点注册器
     */
    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        // 配置WebSocket握手的端点
        registry.addEndpoint("/ws")
                // 允许所有来源的跨域请求
                .setAllowedOriginPatterns("*");
    }

    /**
     * 配置消息代理
     *
     * @param registry 消息代理注册器
     */
    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {
        // 设置应用程序消息路径前缀
        registry.setApplicationDestinationPrefixes("/app");
        // 配置广播和队列消息路径
        registry.enableSimpleBroker("/topic", "/queue");
        // 设置用户专属消息订阅路径前缀
        registry.setUserDestinationPrefix("/user");
    }

    /**
     * 配置客户端入站通道拦截器
     * 用于处理 WebSocket 连接、断开事件
     *
     * @param registration 通道注册器
     */
    @Override
    public void configureClientInboundChannel(ChannelRegistration registration) {
        registration.interceptors(new ChannelInterceptor() {
            @Override
            public Message<?> preSend(Message<?> message, MessageChannel channel) {
                // 获取 STOMP 消息头访问器
                StompHeaderAccessor accessor = MessageHeaderAccessor.getAccessor(message, StompHeaderAccessor.class);
                // 若访问器为空，直接返回原消息
                if (accessor == null) {
                    return ChannelInterceptor.super.preSend(message, channel);
                }

                // 根据 STOMP 命令类型处理连接、断开请求
                StompCommand command = accessor.getCommand();
                if (command == StompCommand.CONNECT || command == StompCommand.RECEIPT) {
                    // 处理连接请求
                    handleUserConnect(accessor);
                } else if (command == StompCommand.DISCONNECT) {
                    // 处理断开请求
                    handleUserDisconnect(accessor);
                }
                return ChannelInterceptor.super.preSend(message, channel);
            }
        });
    }

    /**
     * 处理用户连接事件
     * 更新用户在线状态，并记录连接信息
     *
     * @param accessor STOMP 消息头访问器
     */
    private void handleUserConnect(StompHeaderAccessor accessor) {
        String token = accessor.getFirstNativeHeader(HttpHeaders.AUTHORIZATION);
        if (StrUtil.isBlank(token) || !token.startsWith(GlobalConstants.TOKEN_TYPE_BEARER)) {
            log.warn("无效的认证令牌: {}", token);
            return;
        }

        String accessToken = token.replace(GlobalConstants.TOKEN_TYPE_BEARER, "").trim();
        String sessionKey = GlobalCacheConstants.getLoginAccessToken(accessToken);
        LoginUserInfoDTO userInfo = redisService.getCacheObject(sessionKey);

        if (userInfo != null) {
            // 设置用户身份信息
            accessor.setUser(() -> sessionKey);
            // 添加在线用户信息
            onlineUserService.addOnlineUser(sessionKey, true);
        }
    }

    /**
     * 处理用户断开事件
     * 更新用户离线状态，并移除连接信息
     *
     * @param accessor STOMP 消息头访问器
     */
    private void handleUserDisconnect(StompHeaderAccessor accessor) {
        // 获取会话密钥
        Principal principal = accessor.getUser();
        if (principal != null) {
            // 移除在线用户信息
            onlineUserService.removeOnlineUser(principal.getName(), false);
        }
    }

    @Bean
    public WebSocketStompClient webSocketStompClient() {
        List<Transport> transports = Collections.singletonList(new WebSocketTransport(new StandardWebSocketClient()));
        SockJsClient sockJsClient = new SockJsClient(transports);
        return new WebSocketStompClient(sockJsClient);
    }

}
