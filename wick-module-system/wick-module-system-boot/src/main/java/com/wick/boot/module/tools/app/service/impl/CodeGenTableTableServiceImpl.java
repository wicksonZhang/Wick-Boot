package com.wick.boot.module.tools.app.service.impl;

import cn.hutool.core.util.ObjUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wick.boot.common.core.result.PageResult;
import com.wick.boot.module.tools.app.service.AbstractCodeGenTableAppService;
import com.wick.boot.module.tools.app.service.ICodeGenTableService;
import com.wick.boot.module.tools.convert.CodeGenTableConvert;
import com.wick.boot.module.tools.mapper.ICodeGenTableMapper;
import com.wick.boot.module.tools.model.dto.CodeGenTableDTO;
import com.wick.boot.module.tools.model.entity.CodeGenTable;
import com.wick.boot.module.tools.model.vo.QueryCodeGenTablePageReqVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

/**
 * 代码自动生成器-服务实现类
 *
 * @author ZhangZiHeng
 * @date 2024-07-23
 */
@Slf4j
@Service
public class CodeGenTableTableServiceImpl extends AbstractCodeGenTableAppService implements ICodeGenTableService {

    @Resource
    private ICodeGenTableMapper codeGenTableMapper;

    @Override
    public PageResult<CodeGenTableDTO> selectDbTableList(QueryCodeGenTablePageReqVO queryVO) {
        /* Step-1: 根据数据源获取对应的表信息 */
        Page<CodeGenTable> pageResult = this.codeGenTableMapper.selectCodeGenPage(
                new Page<>(queryVO.getPageNumber(), queryVO.getPageSize()), queryVO
        );

        if (ObjUtil.isNull(pageResult)) {
            return PageResult.empty();
        }

        /* Step-4: 返回分页结果 */
        List<CodeGenTableDTO> codeGenDTOS = CodeGenTableConvert.INSTANCE.entityToCodeGenDTOS(pageResult.getRecords());
        return new PageResult<>(codeGenDTOS, pageResult.getTotal());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void importTable(List<String> tableNames) {
        /* Step-1：参数验证 */
        List<CodeGenTableDTO> codeGenTables = this.codeGenTableMapper.selectByTableNames(tableNames);
        this.validateAddParams(codeGenTables, tableNames);

        /* Step-2: 导入数据表 */
        importGenTable(codeGenTables);
    }

    /**
     * 导入数据表
     *
     * @param codeGenTableDTOS 数据表信息
     */
    private void importGenTable(List<CodeGenTableDTO> codeGenTableDTOS) {
        /* Step-1: Convert DTO To Entities */
        List<CodeGenTable> codeGenTables = CodeGenTableConvert.INSTANCE.dtoToEntity(codeGenTableDTOS);
    }


}
