package cn.wickson.security.system.app.service.impl;

import cn.hutool.core.collection.CollUtil;
import cn.wickson.security.commons.constant.GlobalSystemConstants;
import cn.wickson.security.system.app.service.ISystemDeptService;
import cn.wickson.security.system.convert.SystemDeptConvert;
import cn.wickson.security.system.mapper.ISystemDeptMapper;
import cn.wickson.security.system.model.dto.SystemDeptDTO;
import cn.wickson.security.system.model.entity.SystemDept;
import cn.wickson.security.system.model.vo.QueryDeptListReqVO;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.google.common.collect.Maps;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;
import java.util.stream.Collectors;

/**
 * 后台管理 - 部门信息
 *
 * @author Wickson
 * @date 2024-04-05 22:32
 **/
@Service
public class SystemDeptServiceImpl extends ServiceImpl<ISystemDeptMapper, SystemDept> implements ISystemDeptService {

    @Resource
    private ISystemDeptMapper systemDeptMapper;

    @Override
    public List<SystemDeptDTO> listDepartments(QueryDeptListReqVO reqVO) {
        /* Step-1: 获取部门信息 */
        List<SystemDept> deptList = systemDeptMapper.selectList(reqVO.getName(), reqVO.getStatus());
        if (CollUtil.isEmpty(deptList)) {
            return Collections.emptyList();
        }

        /* Step-2: 构建部门树 */
        return buildDeptTree(deptList);
    }

    @Override
    public Map<String, Object> listDeptOptions() {
        /* Step-1: 获取部门信息 */
        List<SystemDept> deptList = systemDeptMapper.selectDeptOptions();
        if (CollUtil.isEmpty(deptList)) {
            return Maps.newHashMap();
        }

        /* Step-2: 构建部门树 */
        List<SystemDeptDTO> deptDTOList = buildDeptTree(deptList);

        /* Step-3: 返回结果集 */
        Map<String, Object> map = Maps.newLinkedHashMap();
        deptDTOList.forEach(dept -> {
            map.put("value", dept.getId());
            map.put("label", dept.getName());
            map.put("children", dept.getChildren());
        });
        return map;
    }

    /**
     * 构建部门树信息
     *
     * @param departmentList 部门列表信息
     * @return List<SystemDeptDTO>
     */
    private List<SystemDeptDTO> buildDeptTree(List<SystemDept> departmentList) {
        Map<Long, SystemDeptDTO> deptMap = new HashMap<>();
        Long rootNodeId = GlobalSystemConstants.ROOT_NODE_ID;

        // Step-1: 构建部门树并将部门存入Map
        for (SystemDept dept : departmentList) {
            SystemDeptDTO deptDTO = SystemDeptConvert.INSTANCE.entityToDTOWithChildren(dept);
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
        return deptMap.values().stream()
                .filter(deptDTO -> Objects.equals(rootNodeId, deptDTO.getParentId()))
                .collect(Collectors.toList());
    }
}
