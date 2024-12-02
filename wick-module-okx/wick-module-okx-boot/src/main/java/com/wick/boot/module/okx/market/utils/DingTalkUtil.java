package com.wick.boot.module.okx.market.utils;

import com.dingtalk.api.DefaultDingTalkClient;
import com.dingtalk.api.DingTalkClient;
import com.dingtalk.api.request.OapiRobotSendRequest;
import com.dingtalk.api.response.OapiRobotSendResponse;
import com.wick.boot.module.okx.market.model.dto.tickers.MarketTickersDTO;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.List;

/**
 * 钉钉机器人推送
 *
 * @author Wickson
 * @date 2024-11-27
 */
@Slf4j
@Component
public class DingTalkUtil {

    @Value("${aliyun.ding-talk}")
    private String dingTalk;

    @Value("${aliyun.secret}")
    private String secret;

    public void sendMarketTickers(String time, String title, List<MarketTickersDTO> msg) {
        try {
            long timestamp = System.currentTimeMillis();
            String sign = sign(timestamp);
            String url = dingTalk + "&timestamp=" + timestamp + "&sign=" + sign;
            //创建钉钉客户端
            DingTalkClient client = new DefaultDingTalkClient(url);
            //构建自定义机器人请求
            OapiRobotSendRequest request = new OapiRobotSendRequest();
            //设置固定为文字版信息类型
            request.setMsgtype("markdown");
            //构建自定义机器人文字类型请求
            OapiRobotSendRequest.Markdown markdown = new OapiRobotSendRequest.Markdown();
            markdown.setTitle(title);
            //调用封装文本信息
            String markdownTable = convertJsonToText(time, msg);
            markdown.setText(markdownTable);
            request.setMarkdown(markdown);
            OapiRobotSendResponse response = client.execute(request);
            log.info("发送完成，返回值：{}", response.getBody());
        } catch (Exception e) {
            log.error("钉钉接口调用异常：", e);
        }
    }

    private String convertJsonToText(String time, List<MarketTickersDTO> msg) {
        // 使用纯文本格式展示
        StringBuilder textBuilder = new StringBuilder();
        textBuilder.append("#### ").append(time).append("分钟涨跌幅：\n\n");

        // 循环输出每个币种的信息，使用 Markdown 格式竖排显示
        int index = 1;
        for (MarketTickersDTO marketTickersDTO : msg) {
            String instId = marketTickersDTO.getInstId();
            String last = marketTickersDTO.getLast();
            String high24h = marketTickersDTO.getHigh24h();
            String low24h = marketTickersDTO.getLow24h();
            String ts = marketTickersDTO.getTs();
            String threeChangePercent = String.format("%.2f%%", marketTickersDTO.getThreeChangePercent());
            String dayChangePercent = String.format("%.2f%%", marketTickersDTO.getDayChangePercent());

            // 输出每个币种的信息，Markdown 格式，添加颜色
            textBuilder.append("\n---\n");
            textBuilder.append("**").append(index).append("、").append(instId).append("**").append("\n\n");
            textBuilder.append("- **当前涨跌幅**：").append(threeChangePercent).append("\n\n");
            textBuilder.append("- **当前成交价**：").append(last).append("\n\n");
            textBuilder.append("- **今日涨跌幅**：").append(dayChangePercent).append("\n\n");
            textBuilder.append("- **今日最高价**：").append(high24h).append("\n\n");
            textBuilder.append("- **今日最低价**：").append(low24h).append("\n\n");
            textBuilder.append("- **产生时间**：").append(ts).append("\n\n");
            index++;
        }

        return textBuilder.toString();
    }

    private String sign(Long timestamp) throws Exception {
        String stringToSign = timestamp + "\n" + secret;
        Mac mac = Mac.getInstance("HmacSHA256");
        mac.init(new SecretKeySpec(secret.getBytes(StandardCharsets.UTF_8), "HmacSHA256"));
        byte[] signData = mac.doFinal(stringToSign.getBytes(StandardCharsets.UTF_8));
        return URLEncoder.encode(new String(Base64.encodeBase64(signData)), "UTF-8");
    }

}
