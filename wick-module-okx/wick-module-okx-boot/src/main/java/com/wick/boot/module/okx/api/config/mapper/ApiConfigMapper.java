package com.wick.boot.module.okx.api.config.mapper;

import cn.hutool.core.util.ObjUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wick.boot.common.mybatis.mapper.BaseMapperX;
import com.wick.boot.module.okx.api.config.model.entity.ApiConfig;
import com.wick.boot.module.okx.api.config.model.vo.apiconfig.ApiConfigQueryVO;
import org.apache.ibatis.annotations.Mapper;


/**
 * Api配置管理-Mapper接口
 *
 * @date 2024-11-18 10:42
 */
@Mapper
public interface ApiConfigMapper extends BaseMapperX<ApiConfig> {

    /**
     * 分页查询数据表
     *
     * @param page    分页集合
     * @param queryVO 请求参数
     * @return 数据表分页集合
     */
    default Page<ApiConfig> getApiConfigPage(Page<ApiConfig> page, ApiConfigQueryVO queryVO) {
        LambdaQueryWrapper<ApiConfig> wrapper = new LambdaQueryWrapper<>();
        wrapper.select(
                        ApiConfig::getId,
                        ApiConfig::getApiKey,
                        ApiConfig::getSecretKey,
                        ApiConfig::getCreateTime,
                        ApiConfig::getTitle
                )
                .eq(ObjUtil.isNotEmpty(queryVO.getTitle()), ApiConfig::getTitle, queryVO.getTitle())
                .orderByDesc(ApiConfig::getId);

        return selectPage(page, wrapper);
    }

    default ApiConfig selectByCreateBy(Long userId) {
        return this.selectOne(new LambdaQueryWrapper<ApiConfig>().eq(ApiConfig::getCreateBy, userId));
    }
}
