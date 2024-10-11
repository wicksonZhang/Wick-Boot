package com.wick.boot.module.tool.service;

import com.wick.boot.common.core.result.PageResult;
import com.wick.boot.module.tool.model.dto.ToolCodeGenDetailDTO;
import com.wick.boot.module.tool.model.dto.ToolCodeGenPreviewDTO;
import com.wick.boot.module.tool.model.dto.table.ToolCodeGenTablePageReqsDTO;
import com.wick.boot.module.tool.model.vo.table.ToolCodeGenTableQueryVO;
import com.wick.boot.module.tool.model.vo.table.ToolCodeGenTableUpdateVO;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 代码自动生成器 - 服务层
 *
 * @author Wickson
 * @date 2024-07-23
 */
public interface ToolCodeGenTableService {

    /**
     * 获取数据源分页数据信息
     *
     * @param queryVO 查询参数VO
     * @return 分页数据
     */
    PageResult<ToolCodeGenTablePageReqsDTO> selectDbTableList(ToolCodeGenTableQueryVO queryVO);

    /**
     * 导入数据表
     *
     * @param tableNames 表名
     */
    void importTable(List<String> tableNames);

    /**
     * 获取代码生成器分页数据
     *
     * @param queryVO 查询参数
     * @return 分页数据
     */
    PageResult<ToolCodeGenTablePageReqsDTO> selectCodeGenTableList(ToolCodeGenTableQueryVO queryVO);

    /**
     * 通过数据表Id查询数据表详细信息
     *
     * @param tableId 数据表Id
     * @return
     */
    ToolCodeGenDetailDTO getDetails(Long tableId);

    /**
     * 更新数据库的表和字段定义
     *
     * @param updateVO 更新VO
     */
    void update(ToolCodeGenTableUpdateVO updateVO);

    /**
     * 删除代码生成信息
     *
     * @param ids 主键集合
     */
    void deleteToolCodeGenTable(List<Long> ids);

    /**
     * 执行指定表的代码生成
     *
     * @param tableId 表编号
     * @return 生成结果。key 为文件路径，value 为对应的代码内容
     */
    List<ToolCodeGenPreviewDTO> previewCode(Long tableId);

    /**
     * 同步数据表
     *
     * @param tableId 数据表id
     */
    void syncDb(Long tableId);

    /**
     * 下载代码
     *
     * @param response 响应结果集
     * @param tableId  表Id
     */
    void download(HttpServletResponse response, Long tableId);
}
