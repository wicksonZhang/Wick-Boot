package com.wick.boot.module.system.service;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.ObjUtil;
import cn.hutool.core.util.StrUtil;
import com.wick.boot.common.core.constant.GlobalConstants;
import com.wick.boot.common.core.constant.GlobalResultCodeConstants;
import com.wick.boot.common.core.exception.ParameterException;
import com.wick.boot.common.core.exception.ServiceException;
import com.wick.boot.module.system.enums.system.ErrorCodeSystem;
import com.wick.boot.module.system.enums.MenuTypeEnum;
import com.wick.boot.module.system.mapper.SystemMenuMapper;
import com.wick.boot.module.system.model.entity.SystemMenu;
import com.wick.boot.module.system.model.vo.menu.SystemMenuAddVO;
import com.wick.boot.module.system.model.vo.menu.SystemMenuUpdateVO;

import javax.annotation.Resource;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 字典信息 - 防腐层
 */
public abstract class SystemMenuAbstractService {

    @Resource
    protected SystemMenuMapper menuMapper;

    // ============================================== 新增参数校验 ==============================================

    protected void validateAddParams(SystemMenuAddVO reqVO) {
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
    private void validateMenuByType(SystemMenuAddVO reqVO) {
        MenuTypeEnum type = reqVO.getType();
        switch (type) {
            case CATALOG:
                this.validateByCatalog(reqVO);
                reqVO.setPerm(null);
                reqVO.setComponent("Layout");
                break;
            case EXT_LINK:
                this.validateByLink(reqVO);
                reqVO.setPerm(null);
                reqVO.setComponent(null);
                break;
            case MENU:
                this.validateByMenu(reqVO);
                reqVO.setPerm(null);
                break;
        }
    }

    /**
     * 校验目录参数
     *
     * @param reqVO 新增请求参数
     */
    private void validateByCatalog(SystemMenuAddVO reqVO) {
        // 校验路由路径不为空
        if (StrUtil.isBlankIfStr(reqVO.getRoutePath())) {
            throw ParameterException.getInstance(GlobalResultCodeConstants.PARAM_IS_INVALID, "路由路径不能为空");
        }
        // 校验显示状态不为空
        if (StrUtil.isBlankIfStr(reqVO.getVisible())) {
            throw ParameterException.getInstance(GlobalResultCodeConstants.PARAM_IS_INVALID, "显示状态不能为空");
        }
        // 验证目录参数的路由路径
        if (GlobalConstants.ROOT_NODE_ID.equals(reqVO.getParentId()) && !reqVO.getRoutePath().startsWith("/")) {
            reqVO.setRoutePath("/" + reqVO.getRoutePath()); // 一级目录需以 / 开头
        }
    }

    /**
     * 校验外链参数
     *
     * @param reqVO 新增请求参数
     */
    private void validateByLink(SystemMenuAddVO reqVO) {
        // 校验路由路径不为空
        if (StrUtil.isBlankIfStr(reqVO.getRoutePath())) {
            throw ParameterException.getInstance(GlobalResultCodeConstants.PARAM_IS_INVALID, "路由路径不能为空");
        }
        // 校验显示状态不为空
        if (StrUtil.isBlankIfStr(reqVO.getVisible())) {
            throw ParameterException.getInstance(GlobalResultCodeConstants.PARAM_IS_INVALID, "显示状态不能为空");
        }
    }

    /**
     * 校验菜单参数
     *
     * @param reqVO 新增请求参数
     */
    private void validateByMenu(SystemMenuAddVO reqVO) {
        // 校验路由路径不为空
        if (StrUtil.isBlankIfStr(reqVO.getRoutePath())) {
            throw ParameterException.getInstance(GlobalResultCodeConstants.PARAM_IS_INVALID, "路由路径不能为空");
        }
        // 校验页面路径不为空
        if (StrUtil.isBlankIfStr(reqVO.getComponent())) {
            throw ParameterException.getInstance(GlobalResultCodeConstants.PARAM_IS_INVALID, "页面路径不能为空");
        }
        // 校验显示状态不为空
        if (StrUtil.isBlankIfStr(reqVO.getVisible())) {
            throw ParameterException.getInstance(GlobalResultCodeConstants.PARAM_IS_INVALID, "显示状态不能为空");
        }
    }

    // ============================================== 更新参数校验 ==============================================

    protected void validateUpdateParams(SystemMenuUpdateVO reqVO) {
        // 校验菜单信息是否存在
        SystemMenu systemMenu = this.validateMenuByMenuId(reqVO.getId());
        // 验证父级Id是否存在
        this.validateMenuByParentId(systemMenu.getParentId(), reqVO.getParentId());
        // 校验菜单名称是否存在
        this.validateMenuByName(reqVO.getParentId(), systemMenu.getName(), reqVO.getName());
        // 通过菜单类型验证其他参数信息
        this.validateMenuByType(reqVO);
    }

    /**
     * 验证菜单Id
     *
     * @param id 菜单Id
     * @return 系统菜单
     */
    private SystemMenu validateMenuByMenuId(Long id) {
        SystemMenu systemMenu = this.menuMapper.selectById(id);
        if (ObjUtil.isNull(systemMenu)) {
            throw ParameterException.getInstance(GlobalResultCodeConstants.PARAM_IS_INVALID, "当前菜单不存在");
        }
        return systemMenu;
    }

    /**
     * 校验菜单父级Id
     *
     * @param oldParentId 旧菜单Id
     * @param newParentId 新菜单Id
     */
    private void validateMenuByParentId(Long oldParentId, Long newParentId) {
        if (oldParentId.equals(newParentId)) {
            return;
        }
        this.validateMenuByParentId(newParentId);
    }

    /**
     * 校验菜单名称
     *
     * @param parentId 父级菜单名称
     * @param oldName  旧菜单名称
     * @param newName  新菜单名称
     */
    private void validateMenuByName(Long parentId, String oldName, String newName) {
        if (oldName.equals(newName)) {
            return;
        }
        this.validateMenuByName(parentId, newName);
    }

    // ============================================== 删除参数校验 ==============================================

    protected void validateDeleteParams(List<SystemMenu> systemMenuList, List<Long> ids) {
        // 验证菜单是否存在
        this.validateMenuList(systemMenuList);
        // 验证菜单集合和 ids 是否匹配
        this.validateMenuByIds(systemMenuList, ids);
    }

    /**
     * 验证菜单是否存在
     *
     * @param systemMenuList 菜单集合
     */
    private void validateMenuList(List<SystemMenu> systemMenuList) {
        // 校验菜单集合是否存在
        if (CollUtil.isEmpty(systemMenuList)) {
            throw ServiceException.getInstance(ErrorCodeSystem.MENU_NOT_EXIST);
        }
    }

    /**
     * 验证菜单集合和 ids 是否匹配
     *
     * @param systemMenuList 菜单集合
     * @param ids            ids
     */
    private void validateMenuByIds(List<SystemMenu> systemMenuList, List<Long> ids) {
        // 校验不存在的菜单ID
        List<Long> menuIds = systemMenuList.stream().map(SystemMenu::getId).collect(Collectors.toList());
        Collection<Long> errorIds = CollectionUtil.subtract(ids, menuIds);
        if (CollUtil.isNotEmpty(errorIds)) {
            String errorMsg = "请确认菜单主键 " + errorIds + " 是否存在";
            throw ServiceException.getInstance(ErrorCodeSystem.MENU_NOT_EXIST.getCode(), errorMsg);
        }
    }
}
