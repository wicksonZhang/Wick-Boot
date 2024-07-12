package com.wick.boot.module.tools.app.service;

import com.wick.boot.common.core.exception.ServiceException;
import com.wick.boot.module.system.enums.ErrorCodeSystem;
import com.wick.boot.module.tools.model.vo.AddDataSourceConfigVO;

import java.sql.Connection;
import java.sql.DriverManager;

/**
 * 数据源配置 - 防腐层
 *
 * @author ZhangZiHeng
 * @date 2024-07-11
 */
public class AbstractDataSourceConfigAppService {

    /**
     * 获取链接
     *
     * @param url      数据源url
     * @param username 用户名
     * @param password 密码
     * @return
     */
    protected boolean isSuccess(String url, String username, String password) {
        try (Connection ignored = DriverManager.getConnection(url, username, password)) {
            return true;
        } catch (Exception ex) {
            return false;
        }
    }

}
