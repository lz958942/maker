/**
 * Copyright (c) 2016, Stupid Bird and/or its affiliates. All rights reserved.
 * STUPID BIRD PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 * @Project : maker
 * @Package : net.lizhaoweb.maker.code.java.model.generate
 * @author <a href="http://www.lizhaoweb.net">李召(John.Lee)</a>
 * @EMAIL 404644381@qq.com
 * @Time : 15:24
 */
package net.lizhaoweb.maker.code.java.model.generate;

import net.lizhaoweb.common.util.base.Constant;
import net.lizhaoweb.common.util.base.IOUtil;
import net.lizhaoweb.common.util.base.StringUtil;
import net.lizhaoweb.common.util.base.date.DateConstant;
import net.lizhaoweb.common.util.base.date.DateUtil;
import net.lizhaoweb.maker.code.java.model.bean.ClassInformation;
import net.lizhaoweb.maker.code.java.model.bean.Configuration;
import net.lizhaoweb.maker.code.java.model.bean.FieldInformation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * <h1>实现 - 生成器</h1>
 *
 * @author <a href="http://www.lizhaoweb.cn">李召(John.Lee)</a>
 * @version 1.0.0.0.1
 * @notes Created on 2016年11月15日<br>
 * Revision of last commit:$Revision$<br>
 * Author of last commit:$Author$<br>
 * Date of last commit:$Date$<br>
 */
public class Generator implements IGenerator {

    protected Logger logger = LoggerFactory.getLogger(this.getClass());

    /**
     * {@inheritDoc}
     */
    @Override
    public void generate(Set<ClassInformation> classInformationSet, Configuration configuration) {
        if (classInformationSet == null) {
            throw new IllegalArgumentException("The class information set is null");
        }
        if (configuration == null) {
            throw new IllegalArgumentException("The configuration is null");
        }
        for (ClassInformation classInformation : classInformationSet) {
            this.generate(classInformation, configuration);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String generate(ClassInformation classInformation, Configuration configuration) {
        if (classInformation == null) {
            throw new IllegalArgumentException("The class information is null");
        }
        if (classInformation == null) {
            throw new IllegalArgumentException("The class information is null");
        }
        String javaFileContent = this.classContent(classInformation, configuration);

        FileOutputStream fileOutputStream = null;
        try {
            String packageName = classInformation.getPackageName();
            String packagePath = packageName.replace('.', '/');

            File saveFilePath = new File(configuration.getSavePath(), packagePath);
            if (!saveFilePath.exists()) {
                saveFilePath.mkdirs();
            }
            if (!saveFilePath.isDirectory()) {
                saveFilePath.mkdirs();
            }
            String fileName = String.format("%s.java", classInformation.getName());
            File saveFile = new File(saveFilePath, fileName);
            fileOutputStream = new FileOutputStream(saveFile);
            IOUtil.write(javaFileContent, fileOutputStream, Constant.Charset.UTF8);
        } catch (FileNotFoundException e) {
            logger.error(e.getMessage(), e);
        } catch (IOException e) {
            logger.error(e.getMessage(), e);
        } finally {
            IOUtil.close(fileOutputStream);
        }
        return javaFileContent;
    }

    // 组装类内容
    private String classContent(
            ClassInformation classInformation,
            Configuration configuration
    ) {
        if (classInformation == null) {
            throw new IllegalArgumentException("The class information is null");
        }
        if (configuration == null) {
            throw new IllegalArgumentException("The configuration is null");
        }
        String classTemplate = configuration.getClassTemplate();
        if (StringUtil.isBlank(classTemplate)) {
            throw new IllegalArgumentException("Invalid class template");
        }

        // import 存储器
        Map<String, String> importMap = new HashMap<String, String>();

        // 获取字段内容块
        String fieldContent = this.allFieldContent(classInformation.getFieldSet(), configuration, importMap);

        // 组装 import 内容块
        StringBuffer importContent = new StringBuffer();
        for (String importClass : importMap.values()) {
            if (StringUtil.isBlank(importClass)) {
                continue;
            }
            if (importClass.indexOf('.') < 0) {
                logger.trace("[Import Type] {}", importClass);
            } else if (importClass.matches("^java\\.lang\\.[^.]+$")) {
                logger.trace("[Import Type] {}", importClass);
            } else {
                importContent.append("import ").append(importClass).append("\n");
            }
        }

        String classInfo = classTemplate.replace("$${name}$$", classInformation.getName());
        classInfo = classInfo.replace("$${TIME}$$", DateUtil.getSystemDateString("HH:mm:ss"));
        classInfo = classInfo.replace("$${DATE}$$", DateUtil.getSystemDateString(DateConstant.Format.Intact.DATE_1));
        classInfo = classInfo.replace("$${IMPORTS}$$", importContent);
        classInfo = classInfo.replace("$${FIELDS}$$", fieldContent);
        classInfo = classInfo.replace("$${packageName}$$", classInformation.getPackageName());
        classInfo = classInfo.replace("$${title}$$", classInformation.getTitle());
        classInfo = classInfo.replace("$${describe}$$", classInformation.getDescribe());
        return classInfo;
    }

    // 组装字段内容块
    private String allFieldContent(
            Set<FieldInformation> fieldInformationSet,
            Configuration configuration,
            Map<String, String> importMap
    ) {
        if (fieldInformationSet == null) {
            throw new IllegalArgumentException("The field information is null");
        }
        if (configuration == null) {
            throw new IllegalArgumentException("The configuration is null");
        }
        if (importMap == null) {
            throw new IllegalArgumentException("The import map is null");
        }
        String fieldTemplate = configuration.getFieldTemplate();
        if (StringUtil.isBlank(fieldTemplate)) {
            throw new IllegalArgumentException("Invalid field template");
        }
        StringBuffer fieldContent = new StringBuffer();
        for (FieldInformation fieldInformation : fieldInformationSet) {
            if (fieldInformation == null) {
                continue;
            }
            String fieldInfo = fieldTemplate.replace("$${name}$$", fieldInformation.getName());
            fieldInfo = fieldInfo.replace("$${title}$$", fieldInformation.getTitle());
            fieldInfo = fieldInfo.replace("$${describe}$$", fieldInformation.getDescribe());

            String typeName = fieldInformation.getType();
            String typeSimpleName = typeName.substring(typeName.lastIndexOf('.') + 1);
            if (importMap.get(typeSimpleName) == null) {
                importMap.put(typeSimpleName, typeName);
            } else if (!importMap.get(typeSimpleName).equals(typeName)) {
                typeSimpleName = typeName;
            }
            fieldInfo = fieldInfo.replace("$${type}$$", typeSimpleName);
            fieldContent.append("\n").append(fieldInfo).append("\n");
        }
        return fieldContent.toString();
    }
}
