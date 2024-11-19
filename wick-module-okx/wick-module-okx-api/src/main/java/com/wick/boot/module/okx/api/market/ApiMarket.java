package com.wick.boot.module.okx.api.market;

import com.dtflys.forest.annotation.*;
import com.dtflys.forest.http.ForestResponse;
import com.wick.boot.module.okx.model.vo.market.TickersQueryVO;

/**
 * 行情数据API
 *
 * @author Wickson
 * @date 2024-11-19
 */
@BaseRequest(
        baseURL = "${okxHttpUrl}",
        contentType = "application/json;charset=UTF-8"
)
public interface ApiMarket {

    /**
     * 获取产品行情信息
     *
     * @param queryVO 分页查询参数
     * @return
     */
    @Get("/api/v5/market/tickers")
    @HTTPProxy(host = "127.0.0.1", port = "7890")
    ForestResponse<String> getTickers(@Query TickersQueryVO queryVO);
}
