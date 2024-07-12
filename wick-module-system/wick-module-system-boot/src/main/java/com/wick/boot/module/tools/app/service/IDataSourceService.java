package com.wick.boot.module.tools.app.service;

import com.wick.boot.module.tools.model.dto.DataSourceConfigDTO;
import com.wick.boot.module.tools.model.vo.AddDataSourceConfigVO;
import com.wick.boot.module.tools.model.vo.UpdateDataSourceConfigVO;

import java.util.List;

/**
 * 数据源管理-服务层
 *
 * @author ZhangZiHeng
 * @date 2024-07-11
 */
public interface IDataSourceService {

    /**
     * 新增数据源配置
     *
     * @param reqVO 数据源配置VO
     * @return 数据源主键
     */
    Long addDataSourceConfig(AddDataSourceConfigVO reqVO);

    /**
     * 更新数据源
     *
     * @param reqVO 数据源更新VO
     */
    void updateDataSourceConfig(UpdateDataSourceConfigVO reqVO);

    /**
     * 删除数据源配置
     *
     * @param id 数据源配置id
     */
    void deleteDataSourceConfig(Long id);

    /**
     * 测试连接
     *
     * @param reqVO 连接参数
     * @return false Or True
     */
    Boolean testConnection(AddDataSourceConfigVO reqVO);

    /**
     * 获取数据源配置列表
     *
     * @return List<DataSourceConfig>
     */
    List<DataSourceConfigDTO> listDataSourceConfig();

    /**
     * 获取数据源信息
     *
     * @param id 数据源id
     * @return 数据源
     */
    DataSourceConfigDTO getDataSource(Long id);

}
