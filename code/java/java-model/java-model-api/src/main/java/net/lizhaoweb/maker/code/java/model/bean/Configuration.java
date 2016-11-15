/**
 * Copyright (c) 2016, Stupid Bird and/or its affiliates. All rights reserved.
 * STUPID BIRD PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 * @Project : maker
 * @Package : net.lizhaoweb.maker.code.java.model.bean
 * @author <a href="http://www.lizhaoweb.net">李召(John.Lee)</a>
 * @EMAIL 404644381@qq.com
 * @Time : 18:10
 */
package net.lizhaoweb.maker.code.java.model.bean;

import lombok.AccessLevel;
import lombok.Data;
import lombok.Getter;
import net.lizhaoweb.common.translate.model.ILanguage;
import net.lizhaoweb.common.util.base.StringUtil;
import net.lizhaoweb.maker.code.java.model.Constant;

import java.io.File;
import java.util.List;
import java.util.Map;
import java.util.Properties;

/**
 * <h1>模型 - 配置</h1>
 *
 * @author <a href="http://www.lizhaoweb.cn">李召(John.Lee)</a>
 * @version 1.0.0.0.1
 * @notes Created on 2016年11月13日<br>
 * Revision of last commit:$Revision$<br>
 * Author of last commit:$Author$<br>
 * Date of last commit:$Date$<br>
 */
@Data
public class Configuration {

    @Getter(AccessLevel.PRIVATE)
    private Properties configureProperties;

    /**
     * 解析开关
     */
    private AnalysisSwitch analysisSwitch;

    /**
     * 指定文件的索引列表
     */
    private List<Integer> analysisIndexes;

    /**
     * 指定文件的名字列表
     */
    private List<String> analysisNames;

    /**
     * 替换字典。
     */
    private Map<String, String> replaceDictionary;

    /**
     * 源语言。
     */
    private ILanguage source;

    /**
     * 目标语言。
     */
    private ILanguage target;

    /**
     * 获取生成类的模板。
     *
     * @return 生成类的模板。
     */
    public String getClassTemplate() {
        return configureProperties.getProperty(Constant.Configuration.Key.Template.CLASS);
    }

    /**
     * 获取生成字段的模板。
     *
     * @return 生成字段的模板。
     */
    public String getFieldTemplate() {
        return configureProperties.getProperty(Constant.Configuration.Key.Template.FIELD);
    }

    /**
     * 获取默认包名。
     *
     * @return 包名。
     */
    public String getPackageName() {
        return configureProperties.getProperty(Constant.Configuration.Key.Package.DEFAULT, "");
    }

    /**
     * 获取指定索引的包名。
     *
     * @param index 索引
     * @return 包名。
     */
    public String getPackageName(int index) {
        String key = String.format("%s.%d", Constant.Configuration.Key.Package.PREFIX, index);
        return configureProperties.getProperty(key, this.getPackageName());
    }

    /**
     * 获取指定索引的基类。
     *
     * @param index 索引
     * @return 基类名。
     */
    public String getExtendsName(int index) {
        String key = String.format("%s.%d", Constant.Configuration.Key.Extends.PREFIX, index);
        return configureProperties.getProperty(key, "");
    }

    /**
     * 获取指定索引的接口。
     *
     * @param index 索引
     * @return 接口名字符串。
     */
    public String getImplementsNames(int index) {
        String key = String.format("%s.%d", Constant.Configuration.Key.Implements.PREFIX, index);
        return configureProperties.getProperty(key, this.getPackageName());
    }

    /**
     * 获取指定索引的接口。
     *
     * @param index 索引
     * @return 接口名列表。
     */
    public List<String> getImplementsNameList(int index) {
        String names = this.getImplementsNames(index);
        List<String> list = null;
        if (StringUtil.isNotBlank(names)) {
            list = StringUtil.toStringList(names, Constant.Array.Regex.STRING_SEPARATOR);
        }
        return list;
    }

    /**
     * 获取保存路径。
     *
     * @return 保存路径字符串。
     */
    public String getSavePath() {
        return configureProperties.getProperty(Constant.Configuration.Key.JavaFileSave.PATH);
    }

    /**
     * 获取保存路径文件对象。
     *
     * @return 获取保存路径文件对象。
     */
    public File getSavePathFile() {
        String savePath = this.getSavePath();
        File savePathFile = new File(savePath);
        if (!savePathFile.exists()) {
            savePathFile.mkdirs();
        }
        if (!savePathFile.isDirectory()) {
            savePathFile.mkdirs();
        }
        return savePathFile;
    }
}
