package com.wick.boot.module.okx.api.config.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.wick.boot.common.core.model.entity.BaseDO;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

/**
 * Api配置-实体类
 *
 * @author Wickson
 * @date 2024-11-18 10:42
 */
@Getter
@Setter
@Accessors(chain = true)
@TableName("okx_api_config")
public class ApiConfig extends BaseDO {

    /**
     * 主键id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * api_key
     */
    private String apiKey;

    /**
     * secret_key
     */
    private String secretKey;

    /**
     * 密码
     */
    private String passPhrase;

    /**
     * api备注名称
     */
    private String title;

}