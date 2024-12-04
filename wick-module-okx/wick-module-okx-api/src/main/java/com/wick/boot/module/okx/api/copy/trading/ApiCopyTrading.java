package com.wick.boot.module.okx.api.copy.trading;

import com.dtflys.forest.annotation.BaseRequest;
import com.dtflys.forest.annotation.Get;
import com.dtflys.forest.annotation.HTTPProxy;
import com.dtflys.forest.annotation.Query;
import com.dtflys.forest.http.ForestResponse;
import com.wick.boot.module.okx.model.vo.copy.trading.PublicLeadTradersQueryVO;

/**
 * 跟单交易
 *
 * @author Wickson
 * @date 2024-12-02
 */
@BaseRequest(
        baseURL = "${okxHttpUrl}",
        contentType = "application/json;charset=UTF-8"
)
public interface ApiCopyTrading {

    /**
     * 获取交易员排名
     *
     * @param queryVO 查询参数
     * @return 响应信息
     */
    @Get("/api/v5/copytrading/public-lead-traders")
    @HTTPProxy(host = "127.0.0.1", port = "7890")
    ForestResponse<String> getPublicLeadTraders(@Query PublicLeadTradersQueryVO queryVO);

}
