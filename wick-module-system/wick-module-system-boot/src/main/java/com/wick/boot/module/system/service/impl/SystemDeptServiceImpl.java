package com.wick.boot.module.system.service.impl;

import cn.hutool.core.collection.CollUtil;
import com.google.common.collect.Lists;
import com.wick.boot.common.core.constant.GlobalConstants;
import com.wick.boot.module.system.model.vo.dept.SystemDeptUpdateVO;
import com.wick.boot.module.system.service.SystemDeptAbstractService;
import com.wick.boot.module.system.service.SystemDeptService;
import com.wick.boot.module.system.convert.SystemDeptConvert;
import com.wick.boot.module.system.mapper.SystemDeptMapper;
import com.wick.boot.module.system.model.dto.dept.SystemDeptDTO;
import com.wick.boot.module.system.model.dto.dept.SystemDeptOptionsDTO;
import com.wick.boot.module.system.model.entity.SystemDept;
import com.wick.boot.module.system.model.vo.dept.SystemDeptAddVO;
import com.wick.boot.module.system.model.vo.dept.SystemDeptQueryVO;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.*;
import java.util.stream.Collectors;

/**
 * 部门管理-服务实现层
 *
 * @author Wickson
 * @date 2024-04-05 22:32
 **/
@Service
public class SystemDeptServiceImpl extends SystemDeptAbstractService implements SystemDeptService {

    @Resource
    private SystemDeptMapper systemDeptMapper;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Long addSystemDept(SystemDeptAddVO reqVO) {
        /* Step-1: 验证新增字典数据 */
        this.validateAddParams(reqVO);

        /* Step-2: 类型转换，新增部门数据信息 */
        SystemDept systemDept = SystemDeptConvert.INSTANCE.convertAddVoToEntity(reqVO);
        // 获取 treePath(父节点id路径) 用作后续删除
        String treePath = getTreePath(reqVO.getParentId());
        systemDept.setTreePath(treePath);

        // 保存部门信息
        this.systemDeptMapper.insert(systemDept);
        return systemDept.getId();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateSystemDept(SystemDeptUpdateVO reqVO) {
        /* Step-1: 验证更新部门信息是否正确 */
        this.validateUpdateParams(reqVO);

        /* Step-2: 类型转换，更新部门数据信息 */
        SystemDept systemDept = SystemDeptConvert.INSTANCE.convertUpdateVoToEntity(reqVO);
        // 获取 treePath(父节点id路径) 用作后续删除
        String treePath = getTreePath(reqVO.getParentId());
        systemDept.setTreePath(treePath);

        // 更新部门信息
        this.systemDeptMapper.updateById(systemDept);
    }

    private String getTreePath(Long parentId) {
        // 如果父级节点是根节点直接返回
        if (GlobalConstants.ROOT_NODE_ID.equals(parentId)) {
            return GlobalConstants.ROOT_NODE_ID.toString();
        }
        SystemDept systemDept = this.systemDeptMapper.selectById(parentId);
        return systemDept.getTreePath() + "," + parentId;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteSystemDept(List<Long> ids) {
        /* Step-1: 验证删除参数 */
        List<SystemDept> systemDeptList = this.systemDeptMapper.selectBatchIds(ids);
        this.validateDeleteParams(systemDeptList, ids);

        /* Step-2: 批量删除数据, 包含该部门或者子级部门 */
        // 查询部门以及子部门信息
        Set<SystemDept> removeDeptList = this.systemDeptMapper.selectDeptByIdOrTreePath(ids);
        this.systemDeptMapper.deleteBatchIds(removeDeptList);
    }

    @Override
    public SystemDeptDTO getSystemDept(Long id) {
        /* Step-1: 获取部门数据 */
        SystemDept systemDept = this.systemDeptMapper.selectById(id);
        /* Step-2: Convert entity to DTO */
        return SystemDeptConvert.INSTANCE.convertEntityToDTO(systemDept);
    }

    @Override
    public List<SystemDeptDTO> getSystemDeptList(SystemDeptQueryVO reqVO) {
        /* Step-1: 获取部门信息 */
        List<SystemDept> deptList = systemDeptMapper.selectList(reqVO.getName(), reqVO.getStatus());
        if (CollUtil.isEmpty(deptList)) {
            return Collections.emptyList();
        }

        /* Step-2: 构建部门树 */
        return buildDeptTree(deptList);
    }

    @Override
    public List<SystemDeptOptionsDTO> getSystemDeptOptionsList() {
        /* Step-1: 获取部门信息 */
        List<SystemDept> deptList = systemDeptMapper.selectDeptOptions();
        if (CollUtil.isEmpty(deptList)) {
            return Lists.newArrayList();
        }

        /* Step-2: 构建部门树 */
        List<SystemDeptDTO> deptDTOList = buildDeptTree(deptList);

        /* Step-3: 返回结果集 */
        return SystemDeptConvert.INSTANCE.convertDtoListToOptionsList(deptDTOList);
    }

    /**
     * 构建部门树信息
     *
     * @param departmentList 部门列表信息
     * @return List<SystemDeptDTO>
     */
    private List<SystemDeptDTO> buildDeptTree(List<SystemDept> departmentList) {
        Map<Long, SystemDeptDTO> deptMap = new HashMap<>();
        Long rootNodeId = GlobalConstants.ROOT_NODE_ID;

        // Step-1: 构建部门树并将部门存入Map
        for (SystemDept dept : departmentList) {
            SystemDeptDTO deptDTO = SystemDeptConvert.INSTANCE.convertEntityToDtoWithChildren(dept);
            deptMap.put(deptDTO.getId(), deptDTO);
        }

        // 将子部门添加到父部门的children属性中
        for (SystemDeptDTO deptDTO : deptMap.values()) {
            if (Objects.equals(rootNodeId, deptDTO.getParentId())) {
                continue;
            }
            SystemDeptDTO parentDeptDTO = deptMap.get(deptDTO.getParentId());
            if (parentDeptDTO != null) {
                parentDeptDTO.getChildren().add(deptDTO);
            }
        }

        // Step-2: 返回根节点结果集
        List<SystemDeptDTO> rootDeptList = deptMap.values().stream()
                .filter(deptDTO -> Objects.equals(rootNodeId, deptDTO.getParentId()))
                .collect(Collectors.toList());
        if (CollUtil.isEmpty(rootDeptList)) {
            return new ArrayList<>(deptMap.values());
        }
        return rootDeptList;
    }

    @Override
    public List<SystemDeptOptionsDTO> getSystemDeptOptionsListAll() {
        List<SystemDept> systemDeptList = this.systemDeptMapper.selectList(null);
        return SystemDeptConvert.INSTANCE.convertEntitiesToOptionsList(systemDeptList);
    }
}
