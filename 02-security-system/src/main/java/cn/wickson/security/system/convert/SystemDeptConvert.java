package cn.wickson.security.system.convert;

import cn.wickson.security.system.model.dto.SystemDeptDTO;
import cn.wickson.security.system.model.entity.SystemDept;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.ArrayList;
import java.util.List;

/**
 * 后台管理 - 部门Convert
 *
 * @author ZhangZiHeng
 * @date 2024-04-07
 */
@Mapper
public interface SystemDeptConvert {

    SystemDeptConvert INSTANCE = Mappers.getMapper(SystemDeptConvert.class);

    /**
     * Convert entity to DTO
     *
     * @param dept Department entity
     * @return SystemDeptDTO
     */
    @Mapping(target = "children", ignore = true)
    SystemDeptDTO entityToDTO(SystemDept dept);

    /**
     * Convert entity to DTOs
     *
     * @param deptList List of department entities
     * @return List<SystemDeptDTO>
     */
    default List<SystemDeptDTO> entityToDTOS(List<SystemDept> deptList) {
        List<SystemDeptDTO> dtoList = new ArrayList<>();
        for (SystemDept dept : deptList) {
            SystemDeptDTO dto = entityToDTO(dept);
            dto.setChildren(new ArrayList<>());
            dtoList.add(dto);
        }
        return dtoList;
    }

}
