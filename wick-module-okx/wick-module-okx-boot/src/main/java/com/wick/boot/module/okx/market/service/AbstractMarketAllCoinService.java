package com.wick.boot.module.okx.market.service;

import cn.hutool.http.HttpStatus;
import com.dtflys.forest.http.ForestResponse;
import com.wick.boot.common.core.constant.GlobalResultCodeConstants;
import com.wick.boot.common.core.exception.ServiceException;

/**
 * 币种信息-防腐层
 *
 * @author: Wickson
 * @create: 2024-11-21 23:27
 **/
public abstract class AbstractMarketAllCoinService {

    /**
     * 验证 API 响应的状态码是否正常
     *
     * @param response Forest 响应对象
     */
    protected void validateResponse(ForestResponse<String> response) {
        if (HttpStatus.HTTP_OK != response.statusCode()) {
            throw ServiceException.getInstance(GlobalResultCodeConstants.FAIL);
        }
    }

}
