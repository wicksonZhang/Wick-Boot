package com.wick.boot.module.tool.service;

import cn.hutool.core.util.ObjUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.wick.boot.common.core.exception.ServiceException;
import com.wick.boot.module.system.enums.tool.ErrorCodeTool;
import com.wick.boot.module.tool.mapper.ToolDataSourceMapper;
import com.wick.boot.module.tool.model.entity.ToolDataSource;
import com.wick.boot.module.tool.model.vo.datasource.ToolDataSourceAddVO;
import com.wick.boot.module.tool.model.vo.datasource.ToolDataSourceUpdateVO;

import javax.annotation.Resource;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.List;

/**
 * 数据源配置管理-防腐层
 *
 * @author Wickson
 * @date 2024-10-12 16:34
 */
public abstract class ToolDataSourceAbstractService {

    @Resource
    private ToolDataSourceMapper toolDataSourceMapper;

    /**
     * 校验新增参数
     *
     * @param reqVO 新增参数
     */
    protected void validateAddParams(ToolDataSourceAddVO reqVO) {
        // 判断用户名是否已经存在
        this.validateName(reqVO.getName());

        // 判断当前是否可以连接
        this.validateTestConnection(reqVO.getUrl(), reqVO.getUsername(), reqVO.getPassword());
    }

    private void validateName(String name) {
        boolean exists = this.toolDataSourceMapper.exists(
                new LambdaQueryWrapper<ToolDataSource>().eq(ToolDataSource::getName, name)
        );
        if (exists) {
            throw ServiceException.getInstance(ErrorCodeTool.TOOL_DATA_SOURCE_NAME_EXIST);
        }
    }

    /**
     * 校验更新参数
     *
     * @param reqVO 新增参数
     */
    protected void validateUpdateParams(ToolDataSourceUpdateVO reqVO) {
        // 验证数据源是否存在
        ToolDataSource dataSource = this.getToolDataSource(reqVO.getId());

        // 判断用户名是否已经存在
        this.validateName(dataSource.getName(), reqVO.getName());

        // 判断当前是否可以连接
        this.validateTestConnection(reqVO.getUrl(), reqVO.getUsername(), reqVO.getPassword());
    }

    private ToolDataSource getToolDataSource(Long id) {
        ToolDataSource dataSource = this.toolDataSourceMapper.selectById(id);
        if (ObjUtil.isNull(dataSource)) {
            throw ServiceException.getInstance(ErrorCodeTool.TOOL_DATA_SOURCE_NOT_EXIST);
        }
        return dataSource;
    }

    private void validateName(String sourceName, String targetName) {
        if (sourceName.equals(targetName)) {
            return;
        }
        this.validateName(targetName);
    }

    /**
     * 校验删除参数
     *
     * @param toolDataSourceList 新增参数
     * @param ids                主键集合
     */
    protected void validateDeleteParams(List<ToolDataSource> toolDataSourceList, List<Long> ids) {

    }

    /**
     * 测试连接
     *
     * @param url      数据源URL
     * @param username 数据库账号
     * @param password 数据库密码
     * @return true or false
     */
    protected Boolean validateTestConnection(String url, String username, String password) {
        try (Connection ignored = DriverManager.getConnection(url, username, password)) {
            return true;
        } catch (Exception ex) {
            return false;
        }
    }
}