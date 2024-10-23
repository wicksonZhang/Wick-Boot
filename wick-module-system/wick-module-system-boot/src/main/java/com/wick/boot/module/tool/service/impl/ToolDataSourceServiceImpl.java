package com.wick.boot.module.tool.service.impl;

import cn.hutool.core.util.ObjUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wick.boot.common.core.result.PageResult;
import com.wick.boot.module.tool.convert.ToolDataSourceConvert;
import com.wick.boot.module.tool.mapper.ToolDataSourceMapper;
import com.wick.boot.module.tool.model.dto.datasource.ToolDataSourceDTO;
import com.wick.boot.module.tool.model.entity.ToolDataSource;
import com.wick.boot.module.tool.model.vo.datasource.ToolDataSourceAddVO;
import com.wick.boot.module.tool.model.vo.datasource.ToolDataSourceQueryVO;
import com.wick.boot.module.tool.model.vo.datasource.ToolDataSourceUpdateVO;
import com.wick.boot.module.tool.service.ToolDataSourceAbstractService;
import com.wick.boot.module.tool.service.ToolDataSourceService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

/**
 * 数据源配置管理-服务实现类
 *
 * @author Wickson
 * @date 2024-10-12 16:34
 */
@Slf4j
@Service
public class ToolDataSourceServiceImpl extends ToolDataSourceAbstractService implements ToolDataSourceService {

    @Resource
    private ToolDataSourceMapper toolDataSourceMapper;

    /**
     * 新增数据源配置数据
     *
     * @param reqVO 新增请求参数
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Long addToolDataSource(ToolDataSourceAddVO reqVO) {
        /* Step-1: 校验新增参数 */
        this.validateAddParams(reqVO);

        /* Step-2: 转换实体类型 */
        ToolDataSource toolDataSource =ToolDataSourceConvert.INSTANCE.addVoToEntity(reqVO);

        /* Step-3: 保存数据源配置信息 */
        this.toolDataSourceMapper.insert(toolDataSource);
        return toolDataSource.getId();
    }

    /**
     * 更新数据源配置数据
     *
     * @param reqVO 更新请求参数
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateToolDataSource(ToolDataSourceUpdateVO reqVO) {
        /* Step-1: 校验更新参数 */
        this.validateUpdateParams(reqVO);

        /* Step-2: 转换实体类型 */
        ToolDataSource toolDataSource = ToolDataSourceConvert.INSTANCE.updateVoToEntity(reqVO);

        /* Step-3: 更新数据源配置信息 */
        this.toolDataSourceMapper.updateById(toolDataSource);
    }

    /**
     * 删除数据源配置数据
     *
     * @param ids 主键集合
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteToolDataSource(List<Long> ids) {
        /* Step-1: 校验删除参数 */
        List<ToolDataSource> toolDataSourceList = this.toolDataSourceMapper.selectBatchIds(ids);
        this.validateDeleteParams(toolDataSourceList, ids);

        /* Step-2: 删除数据源配置信息 */
        this.toolDataSourceMapper.deleteBatchIds(toolDataSourceList);
    }

    /**
     * 获取数据源配置数据
     *
     * @param id 数据源配置ID
     * @return ToolDataSourceDTO 数据源配置DTO
     */
    public ToolDataSourceDTO getToolDataSource(Long id) {
        /* Step-1: 通过主键获取数据源配置 */
        ToolDataSource toolDataSource =this.toolDataSourceMapper.selectById(id);

        /* Step-2: 转换实体类型 */
        return ToolDataSourceConvert.INSTANCE.entityToDTO(toolDataSource);
    }

    /**
     * 获取数据源配置分页数据
     *
     * @param queryParams 分页查询参数
     * @return ToolDataSourceDTO 数据源配置DTO
     */
    public PageResult<ToolDataSourceDTO> getToolDataSourcePage(ToolDataSourceQueryVO queryParams) {
        Page<ToolDataSource> pageResult = this.toolDataSourceMapper.getToolDataSourcePage(
                new Page<>(queryParams.getPageNumber(), queryParams.getPageSize()),
                queryParams
        );

        if (ObjUtil.isNull(pageResult)) {
            return PageResult.empty();
        }

        List<ToolDataSourceDTO> toolDataSourcePages = ToolDataSourceConvert.INSTANCE.entityToPage(pageResult.getRecords());
        return new PageResult<>(toolDataSourcePages, pageResult.getTotal());
    }

    @Override
    public List<ToolDataSourceDTO> list() {
        List<ToolDataSource> list = this.toolDataSourceMapper.selectIdAndNameList();
        return ToolDataSourceConvert.INSTANCE.toDTOList(list);
    }

    @Override
    public Boolean testConnection(ToolDataSourceAddVO reqVO) {
        return this.validateTestConnection(reqVO.getUrl(), reqVO.getUsername(), reqVO.getPassword());
    }
}