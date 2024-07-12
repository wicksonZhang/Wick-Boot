package com.wick.boot.module.tools.app.service.impl;

import com.wick.boot.module.tools.app.service.AbstractDataSourceConfigAppService;
import com.wick.boot.module.tools.app.service.IDataSourceService;
import com.wick.boot.module.tools.convert.DataSourceConfigConvert;
import com.wick.boot.module.tools.mapper.IDataSourceConfigMapper;
import com.wick.boot.module.tools.model.dto.DataSourceConfigDTO;
import com.wick.boot.module.tools.model.entity.DataSourceConfig;
import com.wick.boot.module.tools.model.vo.AddDataSourceConfigVO;
import com.wick.boot.module.tools.model.vo.UpdateDataSourceConfigVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

/**
 * 数据源配置 - 服务实现层
 *
 * @author ZhangZiHeng
 * @date 2024-07-11
 */
@Slf4j
@Service
public class DataSourceServiceImpl extends AbstractDataSourceConfigAppService implements IDataSourceService {

    @Resource
    private IDataSourceConfigMapper dataSourceConfigMapper;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Long addDataSourceConfig(AddDataSourceConfigVO reqVO) {
        /* Step-1: 类型转换 */
        DataSourceConfig dataSourceConfig = DataSourceConfigConvert.INSTANCE.addVoToEntity(reqVO);

        /* Step-2: 新增参数*/
        this.dataSourceConfigMapper.insert(dataSourceConfig);
        return dataSourceConfig.getId();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateDataSourceConfig(UpdateDataSourceConfigVO reqVO) {
        /* Step-1: 类型转换 */
        DataSourceConfig dataSourceConfig = DataSourceConfigConvert.INSTANCE.updateVoToEntity(reqVO);
        /* Step-2： 更新参数 */
        this.dataSourceConfigMapper.updateById(dataSourceConfig);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteDataSourceConfig(Long id) {
        this.dataSourceConfigMapper.deleteById(id);
    }

    @Override
    public Boolean testConnection(AddDataSourceConfigVO reqVO) {
        return this.isSuccess(reqVO.getUrl(), reqVO.getUsername(), reqVO.getPassword());
    }

    @Override
    public List<DataSourceConfigDTO> listDataSourceConfig() {
        List<DataSourceConfig> dataSourceConfigs = this.dataSourceConfigMapper.selectList(null);
        return DataSourceConfigConvert.INSTANCE.entityToDTOList(dataSourceConfigs);
    }

    @Override
    public DataSourceConfigDTO getDataSource(Long id) {
        DataSourceConfig dataSourceConfig = this.dataSourceConfigMapper.selectById(id);
        return DataSourceConfigConvert.INSTANCE.entityToDTO(dataSourceConfig);
    }
}
