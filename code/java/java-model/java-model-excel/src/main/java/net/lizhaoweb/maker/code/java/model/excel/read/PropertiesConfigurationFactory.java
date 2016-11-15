/**
 * Copyright (c) 2016, Stupid Bird and/or its affiliates. All rights reserved.
 * STUPID BIRD PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 * @Project : maker
 * @Package : net.lizhaoweb.maker.code.java.model.excel.read
 * @author <a href="http://www.lizhaoweb.net">李召(John.Lee)</a>
 * @EMAIL 404644381@qq.com
 * @Time : 19:19
 */
package net.lizhaoweb.maker.code.java.model.excel.read;

import net.lizhaoweb.common.translate.baidu.model.Language;
import net.lizhaoweb.common.translate.model.ILanguage;
import net.lizhaoweb.maker.code.java.model.read.AbstractPropertiesConfigurationFactory;

import java.util.Properties;

/**
 * <h>实现 - 属性文件配置工厂</h>
 *
 * @author <a href="http://www.lizhaoweb.cn">李召(John.Lee)</a>
 * @version 1.0.0.0.1
 * @notes Created on 2016年11月13日<br>
 * Revision of last commit:$Revision$<br>
 * Author of last commit:$Author$<br>
 * Date of last commit:$Date$<br>
 */
public class PropertiesConfigurationFactory extends AbstractPropertiesConfigurationFactory {

    /**
     * 构造函数
     *
     * @param location 配置属性对象
     */
    public PropertiesConfigurationFactory(Properties location) {
        super(location);
    }

    /**
     * 构造函数
     *
     * @param location   配置属性对象
     * @param dictionary 字典属性对象
     */
    public PropertiesConfigurationFactory(Properties location, Properties dictionary) {
        super(location, dictionary);
    }

    /**
     * 百度翻译语言
     *
     * @param language 语言字符串
     * @return {@link ILanguage}
     */
    @Override
    protected ILanguage getTranslateLanguage(String language) {
        return Language.fromKey(language);
    }
}
