/**
 * Copyright (c) 2016, Stupid Bird and/or its affiliates. All rights reserved.
 * STUPID BIRD PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 * @Project : maker
 * @Package : net.lizhaoweb.maker.code.java.model.exception
 * @author <a href="http://www.lizhaoweb.net">李召(John.Lee)</a>
 * @EMAIL 404644381@qq.com
 * @Time : 11:47
 */
package net.lizhaoweb.maker.code.java.model.exception;

/**
 * <h1>异常 - 解析</h1>
 *
 * @author <a href="http://www.lizhaoweb.cn">李召(John.Lee)</a>
 * @version 1.0.0.0.1
 * @notes Created on 2016年11月11日<br>
 * Revision of last commit:$Revision$<br>
 * Author of last commit:$Author$<br>
 * Date of last commit:$Date$<br>
 */
public class AnalysisExcetion extends RuntimeException {

    public AnalysisExcetion() {
        super();
    }

    public AnalysisExcetion(String message) {
        super(message);
    }

    public AnalysisExcetion(String message, Throwable cause) {
        super(message, cause);
    }

    public AnalysisExcetion(Throwable cause) {
        super(cause);
    }

    protected AnalysisExcetion(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
