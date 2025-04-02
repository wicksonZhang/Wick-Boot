package com.wick.boot.module.system.mapper;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wick.boot.common.mybatis.mapper.BaseMapperX;
import com.wick.boot.module.system.model.dto.notice.SystemNoticeDTO;
import com.wick.boot.module.system.model.dto.notice.SystemNoticeDetailDTO;
import com.wick.boot.module.system.model.entity.SystemNotice;
import com.wick.boot.module.system.model.vo.notice.SystemNoticeQueryVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;


/**
 * 通知公告管理-Mapper接口
 *
 * @date 2025-03-18 18:03
 */
@Mapper
public interface SystemNoticeMapper extends BaseMapperX<SystemNotice> {

    /**
     * 分页查询数据表
     *
     * @param page    分页集合
     * @param queryVO 请求参数
     * @return 数据表分页集合
     */
    Page<SystemNoticeDTO> getSystemNoticePage(Page<SystemNotice> page, @Param("reqVO") SystemNoticeQueryVO queryVO);

    /**
     * 根据ID查询数据表详情
     *
     * @param id id
     * @return
     */
    SystemNoticeDetailDTO getSystemNoticeDetailById(Long id);

    default Page<SystemNotice> selectMyPage(Page<SystemNotice> page, SystemNoticeQueryVO reqVO) {
        LambdaQueryWrapper<SystemNotice> wrapper = new LambdaQueryWrapper<>();
        wrapper.select(
                        SystemNotice::getId,
                        SystemNotice::getTitle,
                        SystemNotice::getContent,
                        SystemNotice::getType,
                        SystemNotice::getLevel,
                        SystemNotice::getTargetType,
                        SystemNotice::getTargetUserIds,
                        SystemNotice::getPublisherId,
                        SystemNotice::getPublishStatus,
                        SystemNotice::getPublishTime,
                        SystemNotice::getRevokeTime
                )
                .orderByDesc(SystemNotice::getId);
        return selectPage(page, wrapper);
    }

}
