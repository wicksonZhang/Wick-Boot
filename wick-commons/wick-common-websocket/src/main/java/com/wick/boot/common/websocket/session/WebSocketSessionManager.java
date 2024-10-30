package com.wick.boot.common.websocket.session;

import com.wick.boot.module.system.model.dto.LoginUserInfoDTO;

import java.util.Set;

/**
 * 管理器的接口
 *
 * @author 芋道源码
 */
public interface WebSocketSessionManager {

    /**
     * 添加 Session
     *
     * @param sessionKey
     * @param userInfo
     */
    void addSession(String sessionKey, LoginUserInfoDTO userInfo);

    /**
     * 移除 Session
     *
     * @param sessionKey sessionKey
     */
    void removeSession(String sessionKey);

    /**
     * 获取所有 Session
     *
     * @return
     */
    Set<String> getAllSession();

}