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

import lombok.Getter;

/**
 * <h1>模型 - 类生成开关</h1>
 * <p>
 * 指定按索引(index)或名字(name)生成类。
 *
 * @author <a href="http://www.lizhaoweb.cn">李召(John.Lee)</a>
 * @version 1.0.0.0.1
 * @notes Created on 2016年11月13日<br>
 * Revision of last commit:$Revision$<br>
 * Author of last commit:$Author$<br>
 * Date of last commit:$Date$<br>
 */
public enum AnalysisSwitch {
    INDEX("index"),
    NAME("name");

    @Getter
    private String key;

    private AnalysisSwitch(String key) {
        this.key = key;
    }

    public static AnalysisSwitch fromKey(String key) {
        AnalysisSwitch result = null;
        for (AnalysisSwitch lang : AnalysisSwitch.values()) {
            if (lang.getKey().equalsIgnoreCase(key)) {
                result = lang;
                break;
            }
        }
        if (result == null) {
            throw new IllegalArgumentException("Invalid AnalysisSwitch key: " + key);
        }
        return result;
    }
}
