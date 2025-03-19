package com.wick.boot.module.system.convert;

import cn.hutool.core.util.ObjUtil;
import com.wick.boot.module.system.model.entity.SystemNotice;
import com.wick.boot.module.system.model.vo.notice.SystemNoticeAddVO;
import com.wick.boot.module.system.model.vo.notice.SystemNoticeQueryVO;
import com.wick.boot.module.system.model.vo.notice.SystemNoticeUpdateVO;
import com.wick.boot.module.system.model.dto.notice.SystemNoticeDTO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * 通知公告管理-转换类
 *
 * @author Wickson
 * @date 2025-03-18 18:03
 */
@Mapper
public interface SystemNoticeConvert {

    SystemNoticeConvert INSTANCE = Mappers.getMapper(SystemNoticeConvert.class);

    /**
     * Convert addVo To entity
     *
     * @param reqVO 新增请求参数VO
     * @return SystemNotice 通知公告
     */
     SystemNotice addVoToEntity(SystemNoticeAddVO reqVO);

    /**
     * Convert updateVo To entity
     *
     * @param reqVO 更新请求参数VO
     * @return SystemNotice 通知公告实体
     */
     SystemNotice updateVoToEntity(SystemNoticeUpdateVO reqVO);

    /**
     * Convert entity to DTO
     *
     * @param systemNotice 通知公告实体
     * @return SystemNoticeDTO
     */
     SystemNoticeDTO entityToDTO(SystemNotice systemNotice);

    /**
     * Convert entity to DTOList
     *
     * @param systemNoticeList 通知公告实体集合
     * @return List<SystemNoticeDTO> SystemNoticeDTO集合对象
     */
    List<SystemNoticeDTO> entityToPage(List<SystemNotice> systemNoticeList);

}