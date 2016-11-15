/**
 * Copyright (c) 2016, Stupid Bird and/or its affiliates. All rights reserved.
 * STUPID BIRD PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 * @Project : maker
 * @Package : net.lizhaoweb.maker.code.java.model.generate
 * @author <a href="http://www.lizhaoweb.net">李召(John.Lee)</a>
 * @EMAIL 404644381@qq.com
 * @Time : 15:18
 */
package net.lizhaoweb.maker.code.java.model.generate;

import net.lizhaoweb.maker.code.java.model.bean.ClassInformation;
import net.lizhaoweb.maker.code.java.model.bean.Configuration;

import java.util.Set;

/**
 * <h>接口 - 生成器</h>
 *
 * @author <a href="http://www.lizhaoweb.cn">李召(John.Lee)</a>
 * @version 1.0.0.0.1
 * @notes Created on 2016年11月15日<br>
 * Revision of last commit:$Revision$<br>
 * Author of last commit:$Author$<br>
 * Date of last commit:$Date$<br>
 */
public interface IGenerator {

    /**
     * 生成
     *
     * @param classInformation 类信息
     * @param configuration    配置对象
     * @return 生成的 java 类字符串。
     */
    String generate(ClassInformation classInformation, Configuration configuration);

    /**
     * 生成
     *
     * @param classInformationSet 类信息列表
     * @param configuration       配置对象
     */
    void generate(Set<ClassInformation> classInformationSet, Configuration configuration);
}
