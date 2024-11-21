package com.wick.boot.module.okx.market.controller;

import com.wick.boot.common.core.result.PageResult;
import com.wick.boot.common.core.result.ResultUtil;
import com.wick.boot.module.okx.market.model.vo.MarketTickersQueryVO;
import com.wick.boot.module.okx.market.service.MarketService;
import com.wick.boot.module.okx.market.model.dto.MarketTickersDTO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
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
@RequestMapping("/okx/market")
@Api(tags = "04-欧意（okx）-市场行情")
public class MarketController {

    private final MarketService marketService;

    @GetMapping("/all-coin")
    @ApiOperation(value = "分页查询_所有币种", notes = "市场行情")
    public ResultUtil<PageResult<MarketTickersDTO>> getTickersPage(@Validated MarketTickersQueryVO queryVO) {
        return ResultUtil.success(marketService.getTickersPage(queryVO));
    }



}
