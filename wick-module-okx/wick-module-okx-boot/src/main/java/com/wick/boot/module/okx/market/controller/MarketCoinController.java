package com.wick.boot.module.okx.market.controller;

import com.wick.boot.common.core.result.PageResult;
import com.wick.boot.common.core.result.ResultUtil;
import com.wick.boot.module.okx.market.model.dto.MarketCoinAddVO;
import com.wick.boot.module.okx.market.model.vo.MarketAllCoinQueryVO;
import com.wick.boot.module.okx.market.service.MarketCoinService;
import com.wick.boot.module.okx.market.model.dto.MarketCoinDTO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * 行情数据-Controller
 *
 * @author Wickson
 * @date 2024-11-19
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/okx/market")
@Api(tags = "04-欧意（okx）-市场行情")
public class MarketCoinController {

    private final MarketCoinService marketService;

    @GetMapping("/all-coin")
    @PreAuthorize("@ss.hasPerm('market:market-coin:query')")
    @ApiOperation(value = "分页查询_所有币种", notes = "市场行情-所有币种")
    public ResultUtil<PageResult<MarketCoinDTO>> getAllCoinPage(@Validated MarketAllCoinQueryVO queryVO) {
        return ResultUtil.success(marketService.getAllCoinPage(queryVO));
    }

    @PostMapping("/add-coin")
    @PreAuthorize("@ss.hasPerm('market:market-coin:add')")
    @ApiOperation(value = "新增_我的自选接口", notes = "市场行情-我的币种")
    public ResultUtil<Long> add(@Valid @RequestBody MarketCoinAddVO reqVO) {
        this.marketService.addMarketMyCoin(reqVO);
        return ResultUtil.success();
    }

}
