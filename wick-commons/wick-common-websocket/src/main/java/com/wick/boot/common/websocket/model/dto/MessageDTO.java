package com.wick.boot.common.websocket.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 消息体
 *
 * @author Wickson
 * @date 2024-10-28
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class MessageDTO {

    /**
     * 发送者
     */
    private String sender;

    /**
     * 消息内容
     */
    private String content;
}
