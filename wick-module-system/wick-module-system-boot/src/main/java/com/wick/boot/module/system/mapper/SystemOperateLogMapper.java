package com.wick.boot.module.system.mapper;

import cn.hutool.core.util.ObjUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wick.boot.common.mybatis.mapper.BaseMapperX;
import com.wick.boot.module.system.model.entity.SystemOperateLog;
import com.wick.boot.module.system.model.vo.logger.operate.SystemOperateLogQueryVO;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.ibatis.annotations.Mapper;

/**
 * 系统操作日志Mapper
 *
 * @author Wickson
 * @date 2024-06-04
 */
@Mapper
public interface SystemOperateLogMapper extends BaseMapperX<SystemOperateLog> {

    default Page<SystemOperateLog> selectOperateLogPage(SystemOperateLogQueryVO reqVO) {
        LambdaQueryWrapper<SystemOperateLog> wrapper = new LambdaQueryWrapper<>();
        Object startTime = ArrayUtils.get(reqVO.getCreateTime(), 0);
        Object endTime = ArrayUtils.get(reqVO.getCreateTime(), 1);
        wrapper.eq(ObjUtil.isNotEmpty(reqVO.getUserId()), SystemOperateLog::getUserId, reqVO.getUserId())
                .likeRight(ObjUtil.isNotEmpty(reqVO.getType()), SystemOperateLog::getType, reqVO.getType())
                .likeRight(ObjUtil.isNotEmpty(reqVO.getModule()), SystemOperateLog::getModule, reqVO.getModule())
                .between(ObjUtil.isAllNotEmpty(startTime, endTime), SystemOperateLog::getCreateTime, startTime, endTime)
                .orderByDesc(SystemOperateLog::getId);
        return selectPage(new Page<>(reqVO.getPageNumber(), reqVO.getPageSize()), wrapper);
    }

}
