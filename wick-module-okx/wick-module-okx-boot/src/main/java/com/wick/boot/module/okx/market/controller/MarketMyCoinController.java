package com.wick.boot.module.okx.market.controller;

import com.wick.boot.module.okx.market.service.MarketAllCoinService;
import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 行情数据-Controller
 *
 * @author Wickson
 * @date 2024-11-19
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/okx/market/my-coin")
@Api(tags = "04-欧意（okx）-市场行情-我的币种")
public class MarketMyCoinController {

    private final MarketAllCoinService marketService;


}
