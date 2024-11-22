package com.wick.boot.module.okx.market.controller;

import com.wick.boot.common.core.result.PageResult;
import com.wick.boot.common.core.result.ResultUtil;
import com.wick.boot.module.okx.market.model.dto.allcoin.MarketAllCoinDTO;
import com.wick.boot.module.okx.market.model.vo.allcoin.MarketAllCoinQueryVO;
import com.wick.boot.module.okx.market.service.MarketCoinService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
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
@RequestMapping("/okx/market/all-coin")
@Api(tags = "04-欧意（okx）-市场行情-所有币种")
public class MarketAllCoinController {

    private final MarketCoinService marketService;

    @GetMapping("/page")
    @PreAuthorize("@ss.hasPerm('market:all-coin:query')")
    @ApiOperation(value = "分页查询_所有币种", notes = "市场行情-所有币种")
    public ResultUtil<PageResult<MarketAllCoinDTO>> getAllCoinPage(@Validated MarketAllCoinQueryVO queryVO) {
        return ResultUtil.success(marketService.getAllCoinPage(queryVO));
    }

}
