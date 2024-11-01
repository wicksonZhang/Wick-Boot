package com.wick.boot.common.websocket.service;

/**
 * 管理器的接口
 *
 * @author 芋道源码
 */
public interface OnlineUserService {

    /**
     * 添加用户到在线用户集合
     *
     * @param sessionKey
     * @param connected
     */
    void addOnlineUser(String sessionKey, Boolean connected);

    /**
     * 从在线用户集合移除用户
     *
     * @param sessionKey sessionKey
     */
    void removeOnlineUser(String sessionKey, Boolean connected);

    /**
     * 获取在线用户数量
     *
     * @return 在线用户数量
     */
    int getOnlineUserCount();

}