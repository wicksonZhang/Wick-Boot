package com.wick.boot.module.system.mapper;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wick.boot.common.mybatis.mapper.BaseMapperX;
import com.wick.boot.module.system.model.dto.SystemLoginLogDTO;
import com.wick.boot.module.system.model.entity.SystemDictType;
import com.wick.boot.module.system.model.entity.SystemLoginLog;
import com.wick.boot.module.system.model.vo.logger.login.QueryLoginLogPageReqVO;
import org.apache.ibatis.annotations.Mapper;

import java.time.LocalDateTime;

/**
 * 系统给登录日志Mapper
 *
 * @author ZhangZiHeng
 * @date 2024-06-04
 */
@Mapper
public interface ISystemLoginLogMapper extends BaseMapperX<SystemLoginLog> {


    default Page<SystemLoginLog> selectLoginLogPage(Page<SystemLoginLog> page, QueryLoginLogPageReqVO reqVO) {
        LambdaQueryWrapper<SystemLoginLog> queryWrapper = new LambdaQueryWrapper<>();
        String username = reqVO.getUsername();
        if (StrUtil.isNotBlank(username)) {
            queryWrapper.likeRight(SystemLoginLog::getUsername, username);
        }
        String userIp = reqVO.getUserIp();
        if (StrUtil.isNotBlank(userIp)) {
            queryWrapper.likeRight(SystemLoginLog::getUserIp, userIp);
        }
        LocalDateTime[] createTime = reqVO.getCreateTime();
        if (createTime != null) {
            LocalDateTime startTime = createTime[0];
            LocalDateTime endTime = createTime[1];
            if (startTime != null && endTime != null) {
                queryWrapper.between(SystemLoginLog::getCreateTime, startTime, endTime);
            }
        }
        queryWrapper.orderByDesc(SystemLoginLog::getCreateTime);
        return this.selectPage(page, queryWrapper);
    }
}
