package com.wick.boot.module.system.mapper;

import cn.hutool.core.util.ArrayUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wick.boot.common.mybatis.mapper.BaseMapperX;
import com.wick.boot.module.system.model.entity.SystemLoginLog;
import com.wick.boot.module.system.model.vo.logger.login.QueryLoginLogPageReqVO;
import org.apache.ibatis.annotations.Mapper;

/**
 * 系统给登录日志Mapper
 *
 * @author Wickson
 * @date 2024-06-04
 */
@Mapper
public interface SystemLoginLogMapper extends BaseMapperX<SystemLoginLog> {


    default Page<SystemLoginLog> selectLoginLogPage(QueryLoginLogPageReqVO reqVO) {
        LambdaQueryWrapper<SystemLoginLog> queryWrapper = new LambdaQueryWrapper<>();
        String username = reqVO.getUsername();
        if (StrUtil.isNotBlank(username)) {
            queryWrapper.likeRight(SystemLoginLog::getUsername, username);
        }
        String userIp = reqVO.getUserIp();
        if (StrUtil.isNotBlank(userIp)) {
            queryWrapper.likeRight(SystemLoginLog::getUserIp, userIp);
        }
        Object startTime = ArrayUtil.get(reqVO.getCreateTime(), 0);
        Object endTime = ArrayUtil.get(reqVO.getCreateTime(), 1);
        if (startTime != null && endTime != null) {
            queryWrapper.between(SystemLoginLog::getCreateTime, startTime, endTime);
        }
        queryWrapper.orderByDesc(SystemLoginLog::getCreateTime);
        return this.selectPage(new Page<>(reqVO.getPageNumber(), reqVO.getPageSize()), queryWrapper);
    }
}
