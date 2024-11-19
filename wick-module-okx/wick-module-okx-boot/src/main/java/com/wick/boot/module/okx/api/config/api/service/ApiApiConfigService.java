package com.wick.boot.module.okx.api.config.api.service;


import cn.hutool.core.util.ObjUtil;
import com.google.common.collect.Maps;
import com.wick.boot.common.core.exception.ServiceException;
import com.wick.boot.common.security.util.SecurityUtils;
import com.wick.boot.module.okx.api.api.config.ApiApiConfig;
import com.wick.boot.module.okx.api.config.model.entity.ApiConfig;
import com.wick.boot.module.okx.api.config.service.ApiConfigService;
import com.wick.boot.module.okx.enums.api.config.ErrorCodeApiConfig;
import com.wick.boot.module.system.enums.system.ErrorCodeSystem;
import com.wick.boot.module.system.model.dto.LoginUserInfoDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Map;

/**
 * ApiApiConfig 实现类
 *
 * @author Wickson
 * @date 2024-11-18
 */
@Slf4j
@Service
public class ApiApiConfigService implements ApiApiConfig {

    @Resource
    private ApiConfigService apiConfigService;

    @Override
    public Map<String, Object> getApiConfig() {
        LoginUserInfoDTO userDetails = SecurityUtils.getUserDetails();
        if (userDetails == null) {
            throw ServiceException.getInstance(ErrorCodeSystem.USER_INVALID);
        }
        Long userId = userDetails.getUserId();
        ApiConfig apiConfig = apiConfigService.getApiConfigByUserId(userId);
        if (ObjUtil.isNull(apiConfig)) {
            throw ServiceException.getInstance(ErrorCodeApiConfig.API_CONFIG_NOT_EXIST);
        }
        Map<String, Object> map = Maps.newHashMap();
        map.put("api_key", apiConfig.getApiKey());
        map.put("secret_key", apiConfig.getSecretKey());
        map.put("passphrase", apiConfig.getPassPhrase());
        return map;
    }

}
