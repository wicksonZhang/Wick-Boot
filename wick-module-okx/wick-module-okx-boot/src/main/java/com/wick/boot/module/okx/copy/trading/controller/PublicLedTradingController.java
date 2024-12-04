package com.wick.boot.module.okx.copy.trading.controller;

import com.wick.boot.common.core.result.PageResult;
import com.wick.boot.common.core.result.ResultUtil;
import com.wick.boot.module.okx.copy.trading.model.dto.PublicLedTradingDTO;
import com.wick.boot.module.okx.copy.trading.model.vo.PublicLedTradingQueryVO;
import com.wick.boot.module.okx.copy.trading.service.PublicLedTradingService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

/**
 * 交易员排名管理-控制类
 *
 * @author Wickson
 * @date 2024-12-04 09:26
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/okx/copy-trading")
@Api(tags = "03-交易广场-交易员排名")
public class PublicLedTradingController {

    private final PublicLedTradingService publicLedTradingService;

    @GetMapping("/page")
    @PreAuthorize("@ss.hasPerm('okx:copy-trading:query')")
    @ApiOperation(value = "分页查询_交易员排名接口", notes = "交易员排名管理")
    public ResultUtil<PageResult<PublicLedTradingDTO>> getPublicLedTradingPage(@Valid PublicLedTradingQueryVO reqVO) {
        return ResultUtil.success(publicLedTradingService.getPublicLedTradingPage(reqVO));
    }

}
