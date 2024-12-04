package com.wick.boot.module.okx.copy.trading.job;

import cn.hutool.http.HttpStatus;
import com.dtflys.forest.http.ForestResponse;
import com.wick.boot.common.core.constant.GlobalResultCodeConstants;
import com.wick.boot.common.core.exception.ServiceException;
import com.wick.boot.module.okx.api.copy.trading.ApiCopyTrading;
import com.wick.boot.module.okx.copy.trading.mapper.PublicLedTradingMapper;
import com.xxl.job.core.handler.annotation.XxlJob;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

/**
 * 交易员信息(每十分钟同步一次)
 *
 * @author Wickson
 * @date 2024-12-03
 */
@Slf4j
@Component
public class PublicLeadTradersJob {

    @Resource
    private ApiCopyTrading copyTrading;

    @Resource
    private PublicLedTradingMapper tradingMapper;


    @XxlJob("publicLeadTradersBy10Min")
    @Transactional(rollbackFor = Exception.class)
    public void execute() {

        // Step-2： 处理数据
    }



    private void validateResponse(ForestResponse<String> response) {
        if (HttpStatus.HTTP_OK != response.statusCode()) {
            throw ServiceException.getInstance(GlobalResultCodeConstants.FAIL);
        }
    }

}
