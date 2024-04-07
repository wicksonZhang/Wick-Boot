package cn.wickson.security.system.mapper;

import cn.hutool.core.util.ObjUtil;
import cn.wickson.security.system.model.entity.SystemMenu;
import cn.wickson.security.system.model.vo.QueryMenuListReqVO;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @author ZhangZiHeng
 * @date 2024-04-07
 */
@Mapper
public interface ISystemMenuMapper extends BaseMapper<SystemMenu> {

    default List<SystemMenu> selectList(QueryMenuListReqVO queryParams) {
        return selectList(new LambdaQueryWrapper<SystemMenu>()
                .likeRight(ObjUtil.isNotNull(queryParams.getName()), SystemMenu::getName, queryParams.getName())
                .eq(ObjUtil.isNotNull(queryParams.getStatus()), SystemMenu::getVisible, queryParams.getStatus())
                .orderByAsc(SystemMenu::getSort));
    }

}
