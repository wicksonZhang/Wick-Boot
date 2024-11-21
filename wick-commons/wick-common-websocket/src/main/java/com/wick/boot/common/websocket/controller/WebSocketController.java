package com.wick.boot.common.websocket.controller;

import com.wick.boot.common.websocket.model.dto.MessageDTO;
import com.wick.boot.common.websocket.service.OnlineUserService;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.security.Principal;

/**
 * WebSocket - 控制类
 *
 * @author Wickson
 * @date 2024-10-28
 */
@RestController
public class WebSocketController {

    @Resource
    private SimpMessagingTemplate messagingTemplate;

    @Resource
    private OnlineUserService onlineUserService;

    /**
     * 广播发送消息
     *
     * @param message 消息内容
     */
    @MessageMapping("/sendToAll")
    @SendTo("/topic/notice")
    public String sendToAll(String message) {
        return "服务端通知: " + message;
    }

    @MessageMapping("/request-initial-data")
    public void handleInitialDataRequest(String message) {
        messagingTemplate.convertAndSend("/topic/onlineUserCount", onlineUserService.getOnlineUserCount());
    }

    /**
     * 点对点发送消息
     * <p>
     * 模拟 张三 给 李四 发送消息场景
     *
     * @param principal 当前用户
     * @param receiver  接收者
     * @param message   消息内容
     */
    @MessageMapping("/sendToUser/{receiver}")
    public void sendToUser(Principal principal, @DestinationVariable String receiver, String message) {
        // 发送人
        String sender = principal.getName();
        // 发送消息给指定用户，拼接后路径 /user/{receiver}/queue/greeting
        messagingTemplate.convertAndSendToUser(receiver, "/queue/greeting", new MessageDTO(sender, message));
    }


}
