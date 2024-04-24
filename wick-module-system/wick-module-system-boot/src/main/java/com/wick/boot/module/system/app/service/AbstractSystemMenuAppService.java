package com.wick.boot.module.system.app.service;

import cn.hutool.core.util.ObjUtil;
import cn.hutool.core.util.StrUtil;
import com.wick.boot.common.core.constant.GlobalConstants;
import com.wick.boot.common.core.constant.GlobalResultCodeConstants;
import com.wick.boot.common.core.exception.ParameterException;
import com.wick.boot.common.core.exception.ServiceException;
import com.wick.boot.module.system.enums.ErrorCodeSystem;
import com.wick.boot.module.system.enums.MenuTypeEnum;
import com.wick.boot.module.system.mapper.ISystemMenuMapper;
import com.wick.boot.module.system.model.entity.SystemMenu;
import com.wick.boot.module.system.model.vo.menu.AddMenuReqVO;

import javax.annotation.Resource;

/**
 * 字典信息 - 防腐层
 */
public abstract class AbstractSystemMenuAppService {

    @Resource
    protected ISystemMenuMapper menuMapper;

    // ============================================== 新增参数校验 ==============================================

    protected void validateAddParams(AddMenuReqVO reqVO) {
        // 验证父级菜单是否存在
        this.validateMenuByParentId(reqVO.getParentId());
        // 校验菜单名称是否存在
        this.validateMenuByName(reqVO.getParentId(), reqVO.getName());
        // 通过菜单类型验证其他参数信息
        this.validateMenuByType(reqVO);
    }

    /**
     * 校验上级部门
     *
     * @param parentId 父级ID
     */
    private void validateMenuByParentId(Long parentId) {
        // 根节点不进行校验
        if (GlobalConstants.ROOT_NODE_ID.equals(parentId)) {
            return;
        }
        // 通过 parentId 获取部门信息
        SystemMenu systemMenu = this.menuMapper.selectById(parentId);
        if (ObjUtil.isNull(systemMenu)) {
            throw ServiceException.getInstance(ErrorCodeSystem.MENU_NOT_EXIST);
        }
    }

    /**
     * 同一个父级菜单下不能存在两个相同的菜单名称
     *
     * @param parentId 父级部门ID
     * @param name     部门名称
     */
    private void validateMenuByName(Long parentId, String name) {
        Long count = this.menuMapper.selectCountByParentIdAndName(parentId, name);
        if (count > 0) {
            throw ServiceException.getInstance(ErrorCodeSystem.MENU_NAME_ALREADY_EXIST);
        }
    }

    /**
     * 验证菜单类型
     *
     * @param reqVO 新增菜单请求菜单
     */
    private void validateMenuByType(AddMenuReqVO reqVO) {
        MenuTypeEnum type = reqVO.getType();
        switch (type) {
            case CATALOG:
            case EXT_LINK:
                this.validateByCatalogAndLink(reqVO.getPath(), reqVO.getVisible());
                break;
            case MENU:
                this.validateByMenu(reqVO.getPath(), reqVO.getComponent(), reqVO.getVisible());
                break;
        }
    }

    /**
     * 校验目录参数
     */
    private void validateByCatalogAndLink(String path, Integer visible) {
        // 校验路由路径不为空
        if (StrUtil.isBlankIfStr(path)) {
            throw ParameterException.getInstance(GlobalResultCodeConstants.PARAM_IS_INVALID, "路由路径不能为空");
        }
        // 校验显示状态不为空
        if (StrUtil.isBlankIfStr(visible)) {
            throw ParameterException.getInstance(GlobalResultCodeConstants.PARAM_IS_INVALID, "显示状态不能为空");
        }
    }

    /**
     * 校验菜单参数
     */
    private void validateByMenu(String path, String component, Integer visible) {
        // 校验路由路径不为空
        if (StrUtil.isBlankIfStr(path)) {
            throw ParameterException.getInstance(GlobalResultCodeConstants.PARAM_IS_INVALID, "路由路径不能为空");
        }
        // 校验页面路径不为空
        if (StrUtil.isBlankIfStr(component)) {
            throw ParameterException.getInstance(GlobalResultCodeConstants.PARAM_IS_INVALID, "页面路径不能为空");
        }
        // 校验显示状态不为空
        if (StrUtil.isBlankIfStr(visible)) {
            throw ParameterException.getInstance(GlobalResultCodeConstants.PARAM_IS_INVALID, "显示状态不能为空");
        }
    }

}
