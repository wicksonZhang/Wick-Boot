package cn.wickson.security.system.app.service.impl;

import cn.hutool.core.collection.CollUtil;
import cn.wickson.security.commons.constant.SystemConstants;
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
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Objects;
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
     * @param deptList 部门列表信息
     * @return List<SystemDeptDTO>
     */
    private List<SystemDeptDTO> buildDeptTree(List<SystemDept> deptList) {
        /* Step-1: 使用Map存储转换后的DTO对象, 并将其存入Map */
        List<SystemDeptDTO> deptDTOS = SystemDeptConvert.INSTANCE.entityToDTOS(deptList);
        Map<Long, SystemDeptDTO> deptMap = deptDTOS.stream().collect(Collectors.toMap(SystemDeptDTO::getId, dto -> dto));

        /* Step-2: 将子部门添加到父部门的children属性中 */
        Long rootNodeId = SystemConstants.ROOT_NODE_ID;
        deptDTOS.stream().filter(dept -> !Objects.equals(rootNodeId, dept.getParentId())).forEach(dept -> {
            SystemDeptDTO parentDeptDTO = deptMap.get(dept.getParentId());
            if (parentDeptDTO != null) {
                parentDeptDTO.getChildren().add(dept);
            }
        });

        /* Step-3: 返回根节点结果集 */
        return deptMap.values()
                .stream()
                .filter(deptDTO -> Objects.equals(rootNodeId, deptDTO.getParentId()))
                .collect(Collectors.toList());
    }
}
