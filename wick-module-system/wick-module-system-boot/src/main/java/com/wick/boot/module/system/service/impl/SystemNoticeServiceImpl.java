package com.wick.boot.module.system.service.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ObjUtil;
import com.google.common.collect.Maps;
import com.wick.boot.common.core.result.PageResult;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wick.boot.module.system.convert.SystemNoticeConvert;
import com.wick.boot.module.system.mapper.SystemNoticeMapper;
import com.wick.boot.module.system.model.vo.notice.SystemNoticeAddVO;
import com.wick.boot.module.system.model.vo.notice.SystemNoticeUpdateVO;
import com.wick.boot.module.system.model.vo.notice.SystemNoticeQueryVO;
import com.wick.boot.module.system.model.dto.notice.SystemNoticeDTO;
import com.wick.boot.module.system.model.entity.SystemNotice;
import com.wick.boot.module.system.service.SystemNoticeAbstractService;
import com.wick.boot.module.system.service.SystemNoticeService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.compress.utils.Lists;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 通知公告管理-服务实现类
 *
 * @author Wickson
 * @date 2025-03-18 18:03
 */
@Slf4j
@Service
public class SystemNoticeServiceImpl extends SystemNoticeAbstractService implements SystemNoticeService {

    @Resource
    private SystemNoticeMapper systemNoticeMapper;

    /**
     * 新增通知公告数据
     *
     * @param reqVO 新增请求参数
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Long addSystemNotice(SystemNoticeAddVO reqVO) {
        /* Step-1: 校验新增参数 */
        this.validateAddParams(reqVO);

        /* Step-2: 转换实体类型 */
        SystemNotice systemNotice = SystemNoticeConvert.INSTANCE.addVoToEntity(reqVO);

        /* Step-3: 保存通知公告信息 */
        this.systemNoticeMapper.insert(systemNotice);
        return systemNotice.getId();
    }

    /**
     * 更新通知公告数据
     *
     * @param reqVO 更新请求参数
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateSystemNotice(SystemNoticeUpdateVO reqVO) {
        /* Step-1: 校验更新参数 */
        this.validateUpdateParams(reqVO);

        /* Step-2: 转换实体类型 */
        SystemNotice systemNotice = SystemNoticeConvert.INSTANCE.updateVoToEntity(reqVO);

        /* Step-3: 更新通知公告信息 */
        this.systemNoticeMapper.updateById(systemNotice);
    }

    /**
     * 删除通知公告数据
     *
     * @param ids 主键集合
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteSystemNotice(List<Long> ids) {
        /* Step-1: 校验删除参数 */
        List<SystemNotice> systemNoticeList = this.systemNoticeMapper.selectBatchIds(ids);
        this.validateDeleteParams(systemNoticeList, ids);

        /* Step-2: 删除通知公告信息 */
        this.systemNoticeMapper.deleteBatchIds(systemNoticeList);
    }

    /**
     * 获取通知公告数据
     *
     * @param id 通知公告ID
     * @return SystemNoticeDTO 通知公告DTO
     */
    public SystemNoticeDTO getSystemNotice(Long id) {
        /* Step-1: 通过主键获取通知公告 */
        SystemNotice systemNotice = this.systemNoticeMapper.selectById(id);

        /* Step-2: 转换实体类型 */
        return SystemNoticeConvert.INSTANCE.entityToDTO(systemNotice);
    }

    /**
     * 获取通知公告分页数据
     *
     * @param queryParams 分页查询参数
     * @return SystemNoticeDTO 通知公告DTO
     */
    public PageResult<SystemNoticeDTO> getSystemNoticePage(SystemNoticeQueryVO queryParams) {
        Page<SystemNotice> pageResult = this.systemNoticeMapper.getSystemNoticePage(
                new Page<>(queryParams.getPageNumber(), queryParams.getPageSize()),
                queryParams
        );

        if (ObjUtil.isNull(pageResult)) {
            return PageResult.empty();
        }

        List<SystemNoticeDTO> systemNoticePages = SystemNoticeConvert.INSTANCE.entityToPage(pageResult.getRecords());
        return new PageResult<>(systemNoticePages, pageResult.getTotal());
    }

    @Override
    public Map<String, Object> getSystemNoticeMyPage(SystemNoticeQueryVO reqVO) {
        Page<SystemNotice> pageResult = this.systemNoticeMapper.getSystemNoticePage(
                new Page<>(reqVO.getPageNumber(), reqVO.getPageSize()),
                reqVO
        );
        List<SystemNotice> records = pageResult.getRecords();
        if (CollUtil.isEmpty(records)) {
            return Maps.newHashMap();
        }
        List<SystemNoticeDTO> list = SystemNoticeConvert.INSTANCE.entityToPage(pageResult.getRecords());
        Map<String, Object> map = Maps.newHashMap();
        map.put("list", list);
        map.put("total", pageResult.getTotal());
        return map;
    }
}