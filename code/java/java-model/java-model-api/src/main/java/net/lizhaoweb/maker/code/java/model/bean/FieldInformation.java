/**
 * Copyright (c) 2016, Stupid Bird and/or its affiliates. All rights reserved.
 * STUPID BIRD PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 * @Project : maker
 * @Package : net.lizhaoweb.maker.code.java.model.bean
 * @author <a href="http://www.lizhaoweb.net">李召(John.Lee)</a>
 * @EMAIL 404644381@qq.com
 * @Time : 11:50
 */
package net.lizhaoweb.maker.code.java.model.bean;

import lombok.Data;

import java.io.Serializable;

/**
 * <h1>模型 - 字段数据</h1>
 *
 * @author <a href="http://www.lizhaoweb.cn">李召(John.Lee)</a>
 * @version 1.0.0.0.1
 * @notes Created on 2016年11月11日<br>
 * Revision of last commit:$Revision$<br>
 * Author of last commit:$Author$<br>
 * Date of last commit:$Date$<br>
 */
@Data
public class FieldInformation implements Serializable {
    /**
     * 字段名。
     */
    private String name;

    /**
     * 字段类型。
     */
    private String type;

    /**
     * 字段标题。根据此属性来生成字段名。
     */
    private String title;

    /**
     * 字段描述。
     */
    private String describe;
}
