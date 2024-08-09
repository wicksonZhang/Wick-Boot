package com.wick.boot.module.tools.engine;

import cn.hutool.core.util.StrUtil;
import com.wick.boot.module.tools.config.ToolCodeGenConfig;
import com.wick.boot.module.tools.model.dto.ToolCodeGenPreviewDTO;
import com.wick.boot.module.tools.model.entity.ToolCodeGenTable;
import com.wick.boot.module.tools.model.entity.ToolCodeGenTableColumn;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.compress.utils.Lists;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.io.File;
import java.util.List;
import java.util.Map;

/**
 * 代码生成器引擎
 *
 * @author ZhangZiHeng
 * @date 2024-08-07
 */
@Slf4j
@Component
public class ToolCodeGenEngine {

    @Resource
    private ToolCodeGenConfig toolCodeGenConfig;

    /**
     * 生成代码
     *
     * @param table          表定义
     * @param columns        table 的字段定义数组
     * @param subTables      子表数组，当且仅当主子表时使用
     * @param subColumnsList subTables 的字段定义数组
     * @return 生成的代码，key 是路径，value 是对应代码
     */
    public List<ToolCodeGenPreviewDTO> execute(ToolCodeGenTable table, List<ToolCodeGenTableColumn> columns,
                                               List<ToolCodeGenTable> subTables, List<List<ToolCodeGenTableColumn>> subColumnsList) {
        List<ToolCodeGenPreviewDTO> list = Lists.newArrayList();

        // 遍历模板配置
        Map<String, ToolCodeGenConfig.TemplateConfig> templateConfigs = toolCodeGenConfig.getTemplateConfigs();
        for (Map.Entry<String, ToolCodeGenConfig.TemplateConfig> templateConfigEntry : templateConfigs.entrySet()) {
            ToolCodeGenPreviewDTO previewDTO = new ToolCodeGenPreviewDTO();
            /* Step-1: 生成文件名 */
            ToolCodeGenConfig.TemplateConfig templateConfig = templateConfigEntry.getValue();
            // 类名
            String className = table.getClassName();
            // 模板名称: Controller、Mapper
            String templateName = templateConfigEntry.getKey();
            // 模板后缀
            String templateExtension = templateConfig.getExtension();

            // 文件名 UserController.java
            String fileName = getFileName(className, templateName, templateExtension);
            previewDTO.setFileName(fileName);

            /* Step-2: 生成文件路径 */
            // 包名：com.wick.boot.module.system
            String packageName = table.getPackageName();
            // 模块名称
            String moduleName = table.getModuleName();
            // 子包：controller、convert
            String subpackageName = templateConfig.getSubpackageName();
            // 文件路径  src/main/java/com/youlai/system/controller
            String filePath = getFilePath(templateName, packageName, moduleName, subpackageName, className);
            previewDTO.setPath(filePath);

            /* 3. 生成文件内容 */
            // 生成文件内容
            String content = getCodeContent(templateConfig, table, columns);
            previewDTO.setContent(content);

            list.add(previewDTO);
        }
        return list;

    }

    /**
     * 获取文件名称
     *
     * @param className         类名
     * @param templateName      模板名称
     * @param templateExtension 后缀
     * @return 文件名称
     */
    private String getFileName(String className, String templateName, String templateExtension) {
        if ("entity".equals(templateName)) {
            return className + templateExtension;
        }
        if ("mapperXml".equals(templateName)) {
            return className + "Mapper" + templateExtension;
        }
        if ("api".equals(templateName)) {
            return StrUtil.toSymbolCase(className, '-') + templateExtension;
        }
        if ("view".equals(templateName)) {
            return "index.vue";
        }

        return className + templateName + templateExtension;
    }

    /**
     * 获取文件路径
     *
     * @param templateName   模板名称
     * @param packageName    包名
     * @param moduleName     模块名称
     * @param subpackageName 子包名称
     * @param className      类名
     * @return 文件路径
     */
    private String getFilePath(String templateName, String packageName, String moduleName, String subpackageName, String className) {
        String path;
        if ("mapperXml".equals(templateName)) {
            path = (toolCodeGenConfig.getBackendAppName()
                    + File.separator
                    + "src" + File.separator + "main" + File.separator + "resources"
                    + File.separator + subpackageName
            );
        } else if ("api".equals(templateName)) {
            path = (toolCodeGenConfig.getFrontendAppName()
                    + File.separator
                    + "src" + File.separator + subpackageName
            );
        } else if ("view".equals(templateName)) {
            path = (toolCodeGenConfig.getFrontendAppName()
                    + File.separator + "src"
                    + File.separator + subpackageName
                    + File.separator + moduleName
                    + File.separator + StrUtil.toSymbolCase(className, '-')
            );
        } else {
            path = (toolCodeGenConfig.getBackendAppName()
                    + File.separator
                    + "src" + File.separator + "main" + File.separator + "java"
                    + File.separator + packageName + File.separator + subpackageName
            );
        }
        // subPackageName = model.entity => model/entity
        return path.replace(".", File.separator);
    }

    private String getCodeContent(ToolCodeGenConfig.TemplateConfig templateConfig, ToolCodeGenTable table, List<ToolCodeGenTableColumn> columns) {
        return null;
    }

}
