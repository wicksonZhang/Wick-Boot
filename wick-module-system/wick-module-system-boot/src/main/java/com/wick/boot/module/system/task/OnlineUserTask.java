package com.wick.boot.module.system.task;

import com.wick.boot.module.system.service.SystemLoginLogService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Slf4j
@Component
public class OnlineUserTask {

    @Resource
    private SystemLoginLogService loginLogService;

    @Resource
    private SimpMessagingTemplate messagingTemplate;

    // 每分钟统计一次在线用户数
//    @Scheduled(cron = "0/1 * * * * ?")
//    public void execute() {
//        log.info("定时任务：统计在线用户数");
//        // 推送在线用户人数
//        messagingTemplate.convertAndSend("/topic/onlineUserCount", loginLogService.getVisitStats());
//    }

}
