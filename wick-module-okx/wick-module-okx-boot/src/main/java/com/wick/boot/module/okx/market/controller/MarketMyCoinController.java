package com.wick.boot.module.okx.market.controller;

import com.wick.boot.common.core.result.PageResult;
import com.wick.boot.common.core.result.ResultUtil;
import com.wick.boot.module.okx.market.model.dto.mycoin.MarketMyCoinDTO;
import com.wick.boot.module.okx.market.model.vo.mycoin.MarketMyCoinAddVO;
import com.wick.boot.module.okx.market.model.vo.mycoin.MarketMyCoinQueryVO;
import com.wick.boot.module.okx.market.service.MarketMyCoinService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

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

    private final MarketMyCoinService marketMyCoinService;

    @PostMapping("/add")
    @PreAuthorize("@ss.hasPerm('market:my-coin:add')")
    @ApiOperation(value = "新增_我的自选接口", notes = "我的自选管理")
    public ResultUtil<Long> add(@Valid @RequestBody MarketMyCoinAddVO reqVO) {
        this.marketMyCoinService.addMarketMyCoin(reqVO);
        return ResultUtil.success();
    }

    @DeleteMapping("/delete/{ids}")
    @PreAuthorize("@ss.hasPerm('market:my-coin:delete')")
    @ApiOperation(value = "删除_我的自选接口", notes = "我的自选管理")
    @ApiImplicitParam(name = "ids", value = "主键id", required = true, dataType = "Long", dataTypeClass = Long.class)
    public ResultUtil<Long> remove(@PathVariable("ids") List<Long> ids) {
        this.marketMyCoinService.deleteMarketMyCoin(ids);
        return ResultUtil.success();
    }

    @GetMapping("/page")
    @PreAuthorize("@ss.hasPerm('market:my-coin:query')")
    @ApiOperation(value = "分页查询_我的自选接口", notes = "我的自选管理")
    public ResultUtil<PageResult<MarketMyCoinDTO>> getMarketMyCoinPage(@Valid MarketMyCoinQueryVO reqVO) {
        return ResultUtil.success(marketMyCoinService.getMarketMyCoinPage(reqVO));
    }

}
