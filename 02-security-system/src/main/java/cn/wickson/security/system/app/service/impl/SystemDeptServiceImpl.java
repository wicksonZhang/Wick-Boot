package cn.wickson.security.system.app.service.impl;

import cn.hutool.core.collection.CollUtil;
import cn.wickson.security.system.app.service.ISystemDeptService;
import cn.wickson.security.system.mapper.ISystemDeptMapper;
import cn.wickson.security.system.model.dto.SystemDeptDTO;
import cn.wickson.security.system.model.entity.SystemDept;
import cn.wickson.security.system.model.vo.QueryDeptListReqVO;
import com.google.common.collect.Lists;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @description:
 * @author: Wickson
 * @create: 2024-04-05 22:32
 **/
@Service
public class SystemDeptServiceImpl implements ISystemDeptService {

    @Resource
    private ISystemDeptMapper systemDeptMapper;

    @Override
    public List<SystemDeptDTO> listDepartments(QueryDeptListReqVO reqVO) {
        List<SystemDept> deptList = systemDeptMapper.selectList(reqVO.getName(), reqVO.getStatus());
        if (CollUtil.isEmpty(deptList)) {
            return Lists.newArrayList();
        }
        // 将
        return null;
    }

}
