package com.wick.boot.module.tool.engine;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.text.NamingCase;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.extra.template.Template;
import cn.hutool.extra.template.TemplateConfig;
import cn.hutool.extra.template.TemplateEngine;
import cn.hutool.extra.template.TemplateUtil;
import com.wick.boot.module.tool.config.ToolCodeGenConfig;
import com.wick.boot.module.tool.constant.ToolCodeGenConstants;
import com.wick.boot.module.tool.model.dto.ToolCodeGenPreviewDTO;
import com.wick.boot.module.tool.model.entity.ToolCodeGenTable;
import com.wick.boot.module.tool.model.entity.ToolCodeGenTableColumn;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.compress.utils.Lists;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.io.File;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

/**
 * 代码生成器引擎
 *
 * @author Wickson
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
            // 业务名称
            String businessName = table.getBusinessName();
            // 模板名称: Controller、Mapper
            String templateName = templateConfigEntry.getKey();
            // 模板后缀
            String templateExtension = templateConfig.getExtension();

            // 文件名 UserController.java
            String fileName = getFileName(className, businessName, templateName, templateExtension);
            previewDTO.setFileName(fileName);

            /* Step-2: 生成文件路径 */
            // 包名：com.wick.boot.module.system
            String packageName = table.getPackageName();
            // 模块名称
            String moduleName = table.getModuleName();
            // 针对VO的包名
            // 子包：controller、convert
            String templatePackageName = templateConfig.getPackageName();
            // 文件路径  src/main/java/com/wick/system/controller
            String filePath = getFilePath(templateName, packageName, moduleName, businessName, templatePackageName, className);
            previewDTO.setPath(filePath);

            /* Step-3: 包路径 */
            String packagePath = getPackagePath(table.getPackageName());
            previewDTO.setPackagePath(packagePath);

            /* Step-4. 生成文件内容 */
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
     * @param businessName      业务名称
     * @param templateName      模板名称
     * @param templateExtension 后缀
     * @return 文件名称
     */
    private String getFileName(String className, String businessName, String templateName, String templateExtension) {
        // 后端项目
        if ("Entity".equals(templateName)) {
            return className + templateExtension;
        }
        if ("MapperXml".equals(templateName)) {
            return className + "Mapper" + templateExtension;
        }
        // 前端项目
        if ("Api".equals(templateName)) {
            return businessName + templateExtension;
        }
        if ("View".equals(templateName)) {
            return "index.vue";
        }
        // 执行SQL信息
        if ("Sql".equals(templateName)) {
            DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("yyyyMMddHHmm");
            return "V1.0.0_" + timeFormatter.format(LocalDateTime.now()) + "__DML" + templateExtension;
        }
        return className + templateName + templateExtension;
    }

    /**
     * 获取文件路径
     *
     * @param templateName        模板名称
     * @param packageName         包名
     * @param moduleName          模块名称
     * @param templatePackageName 子包名称
     * @param className           类名
     * @return 文件路径
     */
    private String getFilePath(String templateName, String packageName, String moduleName, String businessName, String templatePackageName, String className) {
        String path;
        if ("MapperXml".equals(templateName)) {
            path = (toolCodeGenConfig.getBackendAppName()
                    + File.separator
                    + "src" + File.separator + "main" + File.separator + "resources"
                    + File.separator + templatePackageName
                    + File.separator + moduleName
            );
        } else if ("Api".equals(templateName)) {
            path = (toolCodeGenConfig.getFrontendAppName()
                    + File.separator
                    + "src" + File.separator + templatePackageName
                    + File.separator + moduleName
            );
        } else if ("View".equals(templateName)) {
            path = (toolCodeGenConfig.getFrontendAppName()
                    + File.separator + "src"
                    + File.separator + templatePackageName
                    + File.separator + moduleName
                    + File.separator + businessName
            );
        } else if (StrUtil.equalsAny(templateName, "AddVO", "UpdateVO", "QueryVO", "DTO")) {
            path = (toolCodeGenConfig.getBackendAppName()
                    + File.separator
                    + "src" + File.separator + "main" + File.separator + "java"
                    + File.separator + packageName
                    + File.separator + templatePackageName
                    + File.separator + StrUtil.replace(businessName, "-", "")
            );
        } else if ("Sql".equals(templateName)) {
            path = (toolCodeGenConfig.getSyncDbFlywayAppName()
                    + File.separator
                    + "src" + File.separator + "main" + File.separator + "resources"
                    + File.separator + "db"
                    + File.separator + "migration"
            );
        } else {
            path = (toolCodeGenConfig.getBackendAppName()
                    + File.separator
                    + "src" + File.separator + "main" + File.separator + "java"
                    + File.separator + packageName + File.separator + templatePackageName
            );
        }
        // subPackageName = model.entity => model/entity
        return path.replace(".", File.separator);
    }

    private String getPackagePath(String path) {
        return path.replace(".", File.separator);
    }

    /**
     * 获取代码内容
     *
     * @param templateConfig 模板配置
     * @param table          表名
     * @param columns        字段名称
     * @return 代码内容
     */
    private String getCodeContent(ToolCodeGenConfig.TemplateConfig templateConfig, ToolCodeGenTable table, List<ToolCodeGenTableColumn> columns) {
        Map<String, Object> bindMap = new HashMap<>();

        String className = table.getClassName();

        bindMap.put("packageName", table.getPackageName());
        bindMap.put("packageVOName", table.getBusinessName().replace("-", ""));
        bindMap.put("tableName", table.getTableName());
        bindMap.put("author", table.getFunctionAuthor());
        bindMap.put("subPackage", templateConfig.getPackageName());
        bindMap.put("date", DateUtil.format(new Date(), "yyyy-MM-dd HH:mm"));
        bindMap.put("className", className);
        bindMap.put("parentMenuId", table.getParentMenuId());
        bindMap.put("parentMenuName", table.getParentMenuName());
        bindMap.put("lowerClassName", StrUtil.lowerFirst(className));
        bindMap.put("functionName", table.getFunctionName());
        bindMap.put("moduleName", table.getModuleName());
        bindMap.put("businessName", table.getBusinessName());
        bindMap.put("routerName", NamingCase.toPascalCase(table.getBusinessName().replace("-", "")));
        bindMap.put("baseEntity", Arrays.asList(ToolCodeGenConstants.BASE_ENTITY));
        bindMap.put("permissionPrefix", getPermissionPrefix(table.getModuleName(), table.getBusinessName()));
        bindMap.put("fieldConfigs", columns);

        boolean hasLocalDateTime = false;
        boolean hasBigDecimal = false;
        boolean hasRequiredField = false;

        for (ToolCodeGenTableColumn fieldConfig : columns) {
            if ("LocalDateTime".equals(fieldConfig.getJavaType())) {
                hasLocalDateTime = true;
            }
            if ("BigDecimal".equals(fieldConfig.getJavaType())) {
                hasBigDecimal = true;
            }
            if (ObjectUtil.equals(fieldConfig.getRequired(), "1")) {
                hasRequiredField = true;
            }
        }

        bindMap.put("hasLocalDateTime", hasLocalDateTime);
        bindMap.put("hasBigDecimal", hasBigDecimal);
        bindMap.put("hasRequiredField", hasRequiredField);

        TemplateEngine templateEngine = TemplateUtil.createEngine(new TemplateConfig("templates", TemplateConfig.ResourceMode.CLASSPATH));
        Template template = templateEngine.getTemplate(templateConfig.getTemplatePath());

        return template.render(bindMap);
    }

    private String getPermissionPrefix(String moduleName, String businessName) {
        return moduleName + ":" + businessName;
    }

}
