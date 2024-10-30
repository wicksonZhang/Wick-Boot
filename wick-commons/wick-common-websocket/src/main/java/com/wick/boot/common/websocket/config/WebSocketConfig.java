package com.wick.boot.common.websocket.config;

import cn.hutool.core.util.StrUtil;
import com.wick.boot.common.core.constant.GlobalCacheConstants;
import com.wick.boot.common.core.constant.GlobalConstants;
import com.wick.boot.common.redis.service.RedisService;
import com.wick.boot.common.websocket.session.WebSocketSessionManager;
import com.wick.boot.module.system.model.dto.LoginUserInfoDTO;
import lombok.extern.slf4j.Slf4j;
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
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

import javax.annotation.Resource;

/**
 * WebSocket 配置类
 *
 * @author Wickson
 * @date 2024-10-28
 */
@Slf4j
@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

    @Resource
    private RedisService redisService;

    @Resource
    private WebSocketSessionManager sessionManager;

    /**
     * 注册STOMP端点，并将端点映射到特定的路径以进行握手。
     *
     * @param registry STOMP端点注册器，用于配置连接端点的相关设置。
     */
    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        // 配置WebSocket握手的端点
        registry.addEndpoint("/ws")
                // 允许所有来源的跨域请求
                .setAllowedOriginPatterns("*");
    }

    /**
     * 配置消息代理，用于消息发送和广播的前缀设置。
     *
     * @param registry 消息代理注册器，用于配置消息路径的前缀和订阅路径。
     */
    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {
        // 设置发送消息的路径前缀
        registry.setApplicationDestinationPrefixes("/app");
        // 配置用于广播和队列的路径前缀
        registry.enableSimpleBroker("/topic", "/queue");
        // 设置用户订阅消息的路径前缀
        registry.setUserDestinationPrefix("/user");
    }

    /**
     * 配置客户端入站通道的拦截器，用于处理连接和断开连接事件。
     *
     * @param registration 入站通道注册器，用于添加通道拦截器。
     */
    @Override
    public void configureClientInboundChannel(ChannelRegistration registration) {
        registration.interceptors(new ChannelInterceptor() {
            @Override
            public Message<?> preSend(Message<?> message, MessageChannel channel) {
                StompHeaderAccessor accessor = MessageHeaderAccessor.getAccessor(message, StompHeaderAccessor.class);
                if (accessor != null) {
                    // 判断是否为首次连接请求
                    if (StompCommand.CONNECT.equals(accessor.getCommand())) {
                        // 处理连接请求
                        handleConnect(accessor);
                    } else if (StompCommand.DISCONNECT.equals(accessor.getCommand())) {
                        // 处理断开连接请求
                        handleDisconnect(accessor);
                    }
                }
                return ChannelInterceptor.super.preSend(message, channel);
            }

            /**
             * 处理连接请求，校验Token并添加用户会话。
             *
             * @param accessor STOMP头信息访问器
             */
            private void handleConnect(StompHeaderAccessor accessor) {
                String token = accessor.getFirstNativeHeader(HttpHeaders.AUTHORIZATION);
                if (StrUtil.isNotBlank(token) && token.startsWith(GlobalConstants.TOKEN_TYPE_BEARER)) {
                    String finalToken = token.replace(GlobalConstants.TOKEN_TYPE_BEARER, "").trim();
                    // 从Redis中验证Token
                    LoginUserInfoDTO userInfo = redisService.getCacheObject(GlobalCacheConstants.getLoginAccessToken(finalToken));
                    if (userInfo != null) {
                        // 设置用户凭据
                        accessor.setUser(() -> finalToken);
                        // 添加会话信息
                        sessionManager.addSession(finalToken, userInfo);
                    }
                }
            }

            /**
             * 处理断开连接请求，移除用户会话。
             *
             * @param accessor STOMP头信息访问器
             */
            private void handleDisconnect(StompHeaderAccessor accessor) {
                if (accessor.getUser() != null) {
                    String username = accessor.getUser().getName();
                    // 移除会话信息
                    sessionManager.removeSession(username);
                }
            }
        });
    }
}
