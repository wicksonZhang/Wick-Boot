package com.wick.boot.module.system.app.service;

import cn.hutool.core.util.ObjUtil;
import com.wick.boot.common.core.constant.GlobalConstants;
import com.wick.boot.common.core.exception.ServiceException;
import com.wick.boot.module.system.enums.ErrorCodeSystem;
import com.wick.boot.module.system.enums.MenuTypeEnum;
import com.wick.boot.module.system.mapper.ISystemMenuMapper;
import com.wick.boot.module.system.model.entity.SystemMenu;
import com.wick.boot.module.system.model.vo.menu.AddMenuReqVO;

/**
 * 字典信息 - 防腐层
 */
public abstract class AbstractSystemMenuAppService {

    protected ISystemMenuMapper menuMapper;

    // ============================================== 新增参数校验 ==============================================

    protected void validateAddParams(AddMenuReqVO reqVO) {
        // 验证父级菜单是否存在
        this.validateMenuByParentId(reqVO.getParentId());
        // 校验部门名称是否存在
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
                this.validateByCatalog();
                break;
            case MENU:
                this.validateByMenu();
                break;
            case BUTTON:
                this.validateByButton();
                break;
            case EXT_LINK:
                this.validateByLink();
                break;
        }
    }

    /**
     * 校验目录参数
     */
    private void validateByCatalog() {

    }

    /**
     * 校验菜单参数
     */
    private void validateByMenu() {

    }

    /**
     * 校验按钮参数
     */
    private void validateByButton() {

    }

    /**
     * 检验外部链接参数
     */
    private void validateByLink() {

    }
}
