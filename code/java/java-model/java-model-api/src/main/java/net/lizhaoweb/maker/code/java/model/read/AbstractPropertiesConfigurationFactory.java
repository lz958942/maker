/**
 * Copyright (c) 2016, Stupid Bird and/or its affiliates. All rights reserved.
 * STUPID BIRD PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 * @Project : maker
 * @Package : net.lizhaoweb.maker.code.java.model.read
 * @author <a href="http://www.lizhaoweb.net">李召(John.Lee)</a>
 * @EMAIL 404644381@qq.com
 * @Time : 20:00
 */
package net.lizhaoweb.maker.code.java.model.read;

import lombok.Getter;
import net.lizhaoweb.common.translate.model.ILanguage;
import net.lizhaoweb.common.util.base.StringUtil;
import net.lizhaoweb.maker.code.java.model.Constant;
import net.lizhaoweb.maker.code.java.model.bean.AnalysisSwitch;
import net.lizhaoweb.maker.code.java.model.bean.Configuration;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

/**
 * <h1>实现[抽象] - 属性文件配置工厂</h1>
 *
 * @author <a href="http://www.lizhaoweb.cn">李召(John.Lee)</a>
 * @version 1.0.0.0.1
 * @notes Created on 2016年11月13日<br>
 * Revision of last commit:$Revision$<br>
 * Author of last commit:$Author$<br>
 * Date of last commit:$Date$<br>
 */
public abstract class AbstractPropertiesConfigurationFactory implements IConfigurationFactory {

    private Properties location;

    private Properties dictionary;

    @Getter
    private Configuration configuration;

    /**
     * 构造函数
     *
     * @param location 配置属性对象
     */
    public AbstractPropertiesConfigurationFactory(Properties location) {
        this(location, null);
    }

    /**
     * 构造函数
     *
     * @param location   配置属性对象
     * @param dictionary 字典属性对象
     */
    public AbstractPropertiesConfigurationFactory(Properties location, Properties dictionary) {
        super();
        this.location = location;
        this.dictionary = dictionary;
        this.analysisConfiguration();
    }

    // 解析配置文件
    private void analysisConfiguration() {
        Configuration configuration = new Configuration();

        // 解析开关
        String analysisSwitch = location.getProperty(Constant.Analysis.SWITCH);
        configuration.setAnalysisSwitch(AnalysisSwitch.fromKey(analysisSwitch));

        // 指定表格的解析索引
        String analysisIndexString = location.getProperty(Constant.Analysis.INDEXES);
        List<Integer> analysisIndexList = StringUtil.toIntegerList(analysisIndexString, Constant.Array.Regex.STRING_SEPARATOR);
        configuration.setAnalysisIndexes(analysisIndexList);

        // 指定表格的解析名字
        String analysisNameString = location.getProperty(Constant.Analysis.NAMES);
        List<String> analysisNameList = StringUtil.toStringList(analysisNameString, Constant.Array.Regex.STRING_SEPARATOR);
        configuration.setAnalysisNames(analysisNameList);

        // 替换字典
        Map<String, String> dictionaryMap = new HashMap<String, String>((Map) dictionary);
        configuration.setReplaceDictionary(dictionaryMap);

        // 源语言
        String sourceLanguageString = location.getProperty(Constant.Translate.Language.SOURCE);
        configuration.setSource(this.getTranslateLanguage(sourceLanguageString));

        // 目标语言
        String targetLanguageString = location.getProperty(Constant.Translate.Language.TARGET);
        configuration.setTarget(this.getTranslateLanguage(targetLanguageString));

        // 配置属性
        configuration.setConfigureProperties(location);

        this.configuration = configuration;
    }

    protected abstract ILanguage getTranslateLanguage(String language);
}
