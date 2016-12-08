/**
 * Copyright (c) 2016, Stupid Bird and/or its affiliates. All rights reserved.
 * STUPID BIRD PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 * @Project : maker
 * @Package : net.lizhaoweb.maker.code.java.model.read
 * @author <a href="http://www.lizhaoweb.net">李召(John.Lee)</a>
 * @EMAIL 404644381@qq.com
 * @Time : 18:14
 */
package net.lizhaoweb.maker.code.java.model.read;

import net.lizhaoweb.maker.code.java.model.bean.Configuration;

/**
 * <h1>接口 - 配置工厂</h1>
 *
 * @author <a href="http://www.lizhaoweb.cn">李召(John.Lee)</a>
 * @version 1.0.0.0.1
 * @notes Created on 2016年11月13日<br>
 * Revision of last commit:$Revision$<br>
 * Author of last commit:$Author$<br>
 * Date of last commit:$Date$<br>
 */
public interface IConfigurationFactory {

    /**
     * 获取配置对象。
     *
     * @return {@link Configuration}
     */
    Configuration getConfiguration();
}
