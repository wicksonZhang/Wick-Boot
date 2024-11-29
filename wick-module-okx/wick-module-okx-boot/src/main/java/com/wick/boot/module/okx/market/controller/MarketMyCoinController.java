package com.wick.boot.module.okx.market.controller;

import com.wick.boot.common.core.result.ResultUtil;
import com.wick.boot.module.okx.market.model.vo.mycoin.MarketMyCoinAddVO;
import com.wick.boot.module.okx.market.service.MarketMyCoinService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

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

    private final MarketMyCoinService marketService;

    @PostMapping("/add")
    @PreAuthorize("@ss.hasPerm('market:my-coin:add')")
    @ApiOperation(value = "新增_我的自选接口", notes = "我的自选管理")
    public ResultUtil<Long> add(@Valid @RequestBody MarketMyCoinAddVO reqVO) {
        this.marketService.addMarketMyCoin(reqVO);
        return ResultUtil.success();
    }

}
