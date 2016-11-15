/**
 * Copyright (c) 2016, Stupid Bird and/or its affiliates. All rights reserved.
 * STUPID BIRD PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 * @Project : maker
 * @Package : net.lizhaoweb.maker.code.java.model.excel.read
 * @author <a href="http://www.lizhaoweb.net">李召(John.Lee)</a>
 * @EMAIL 404644381@qq.com
 * @Time : 19:22
 */
package net.lizhaoweb.maker.code.java.model.excel.read;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import net.lizhaoweb.common.translate.api.ITranslator;
import net.lizhaoweb.maker.code.java.model.bean.ClassInformation;
import net.lizhaoweb.maker.code.java.model.bean.Configuration;
import net.lizhaoweb.maker.code.java.model.generate.IGenerator;
import net.lizhaoweb.maker.code.java.model.read.IConfigurationFactory;
import net.lizhaoweb.maker.code.java.model.read.IFileReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.util.Set;

/**
 * <h1>实现 - 文件读取器抽象类</h1>
 *
 * @author <a href="http://www.lizhaoweb.cn">李召(John.Lee)</a>
 * @version 1.0.0.0.1
 * @notes Created on 2016年11月13日<br>
 * Revision of last commit:$Revision$<br>
 * Author of last commit:$Author$<br>
 * Date of last commit:$Date$<br>
 */
public abstract class AbstractFileReader implements IFileReader {

    protected Logger logger = LoggerFactory.getLogger(this.getClass());

    private static final String OS_CHARSET_ENCODING = System.getProperty("sun.jnu.encoding");

    @Getter(AccessLevel.PROTECTED)
    private Configuration configuration;

    /**
     * 翻译器
     */
    @Setter
    @Getter(AccessLevel.PROTECTED)
    private ITranslator translator;

    /**
     * 生成器
     */
    @Setter
    @Getter(AccessLevel.PROTECTED)
    private IGenerator generator;

    public AbstractFileReader(IConfigurationFactory configurationFactory) {
        super();
        configuration = configurationFactory.getConfiguration();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Set<ClassInformation> analysis(String fileName) {
        if (fileName == null) {
            throw new IllegalArgumentException("The excel file name is null");
        }
        File file = new File(fileName);
        return this.analysis(file);
    }
}
