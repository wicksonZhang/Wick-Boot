package com.wick.common.mybatis.handler;

import cn.hutool.core.convert.Convert;
import cn.hutool.core.util.ObjUtil;
import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import com.wick.common.core.model.entity.BaseDO;
import com.wick.common.security.util.SecurityUtils;
import com.wick.module.system.model.dto.LoginUserInfoDTO;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class MyMetaObjectHandler implements MetaObjectHandler {

    /**
     * 新增填充创建时间
     *
     * @param metaObject 元数据信息
     */
    @Override
    public void insertFill(MetaObject metaObject) {
        if (ObjUtil.isNotNull(metaObject) && metaObject.getOriginalObject() instanceof BaseDO) {
            // 创建时间和更新时间
            BaseDO baseDO = (BaseDO) metaObject.getOriginalObject();

            // 判断创建时间是否为空
            if (ObjUtil.isNull(baseDO.getCreateTime())) {
                baseDO.setCreateTime(LocalDateTime.now());
            }
            // 判断更新时间是否为空
            if (ObjUtil.isNull(baseDO.getUpdateTime())) {
                baseDO.setUpdateTime(LocalDateTime.now());
            }
            // 判断创建者是否为空
            LoginUserInfoDTO userDetails = SecurityUtils.getUserDetails();
            if (ObjUtil.isNotNull(userDetails) && ObjUtil.isNull(baseDO.getCreateBy())) {
                baseDO.setCreateBy(Convert.toStr(userDetails.getUserId()));
            }
            // 判断更新者是否为空
            if (ObjUtil.isNotNull(userDetails) && ObjUtil.isNull(baseDO.getUpdateBy())) {
                baseDO.setUpdateBy(Convert.toStr(userDetails.getUserId()));
            }
        }
    }

    /**
     * 更新填充更新时间
     *
     * @param metaObject 元数据信息
     */
    @Override
    public void updateFill(MetaObject metaObject) {
        // 更新时间
        this.setFieldValByName("updateTime", LocalDateTime.now(), metaObject);

        // 如果当前更新者不为空，则进行更新
        LoginUserInfoDTO userDetails = SecurityUtils.getUserDetails();
        if (ObjUtil.isNotNull(userDetails)) {
            this.setFieldValByName("updateBy", userDetails.getUserId(), metaObject);
        }
    }

}
