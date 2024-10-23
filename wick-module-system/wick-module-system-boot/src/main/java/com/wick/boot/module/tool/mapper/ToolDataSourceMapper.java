package com.wick.boot.module.tool.mapper;

import cn.hutool.core.util.ObjUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wick.boot.common.mybatis.mapper.BaseMapperX;
import com.wick.boot.module.tool.model.entity.ToolDataSource;
import com.wick.boot.module.tool.model.vo.datasource.ToolDataSourceQueryVO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 数据源配置管理-Mapper接口
 *
 * @date 2024-10-12 16:34
 */
@Mapper
public interface ToolDataSourceMapper extends BaseMapperX<ToolDataSource> {

    /**
     * 分页查询数据表
     *
     * @param page    分页集合
     * @param queryVO 请求参数
     * @return 数据表分页集合
     */
    default Page<ToolDataSource> getToolDataSourcePage(Page<ToolDataSource> page, ToolDataSourceQueryVO queryVO) {
        LambdaQueryWrapper<ToolDataSource> wrapper = new LambdaQueryWrapper<>();
        wrapper
                .likeRight(ObjUtil.isNotEmpty(queryVO.getName()), ToolDataSource::getName, queryVO.getName())
                .likeRight(ObjUtil.isNotEmpty(queryVO.getUsername()), ToolDataSource::getUsername, queryVO.getUsername())
                .orderByDesc(ToolDataSource::getId);

        return selectPage(page, wrapper);
    }

    /**
     * 获取数据源列表
     *
     * @return
     */
    default List<ToolDataSource> selectIdAndNameList() {
        LambdaQueryWrapper<ToolDataSource> wrapper = new LambdaQueryWrapper<>();
        wrapper.select(ToolDataSource::getId, ToolDataSource::getName);
        return selectList(wrapper);
    }
}
