/**
 * Copyright (c) 2016, Stupid Bird and/or its affiliates. All rights reserved.
 * STUPID BIRD PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 * @Project : maker
 * @Package : net.lizhaoweb.maker.code.java.model.read
 * @author <a href="http://www.lizhaoweb.net">李召(John.Lee)</a>
 * @EMAIL 404644381@qq.com
 * @Time : 15:31
 */
package net.lizhaoweb.maker.code.java.model.read;

import net.lizhaoweb.maker.code.java.model.bean.ClassInformation;

import java.io.File;
import java.util.Set;

/**
 * <h>接口 - 文件读取器</h>
 *
 * @author <a href="http://www.lizhaoweb.cn">李召(John.Lee)</a>
 * @version 1.0.0.0.1
 * @notes Created on 2016年11月13日<br>
 * Revision of last commit:$Revision$<br>
 * Author of last commit:$Author$<br>
 * Date of last commit:$Date$<br>
 */
public interface IFileReader extends IReader {

    /**
     * 解析文件内容，获取字段数据。
     *
     * @param fileName 文件名。
     * @return 类数据。
     */
    Set<ClassInformation> analysis(String fileName);

    /**
     * 解析文件内容，获取字段数据。
     *
     * @param file 文件。
     * @return 类数据。
     */
    Set<ClassInformation> analysis(File file);
}
