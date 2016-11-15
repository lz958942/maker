/**
 * Copyright (c) 2016, Stupid Bird and/or its affiliates. All rights reserved.
 * STUPID BIRD PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 * @Project : maker
 * @Package : net.lizhaoweb.maker.code.java.model
 * @author <a href="http://www.lizhaoweb.net">李召(John.Lee)</a>
 * @EMAIL 404644381@qq.com
 * @Time : 15:47
 */
package net.lizhaoweb.maker.code.java.model;

/**
 * <h1>常量 </h1>
 *
 * @author <a href="http://www.lizhaoweb.cn">李召(John.Lee)</a>
 * @version 1.0.0.0.1
 * @notes Created on 2016年11月13日<br>
 * Revision of last commit:$Revision$<br>
 * Author of last commit:$Author$<br>
 * Date of last commit:$Date$<br>
 */
public class Constant {

    /**
     * 配置
     */
    public static class Configuration {
        /**
         * 键
         */
        public static class Key {
            /**
             * 模板
             */
            public static class Template {
                public static final String CLASS = "net.lizhaoweb.maker.code.model.template.class";
                public static final String FIELD = "net.lizhaoweb.maker.code.model.template.field";
            }

            /**
             * 包名
             */
            public static class Package {
                public static final String DEFAULT = "net.lizhaoweb.maker.code.model.package.default";
                public static final String PREFIX = "net.lizhaoweb.maker.code.model.package";
            }

            /**
             * 继承
             */
            public static class Extends {
                public static final String PREFIX = "net.lizhaoweb.maker.code.model.supper.extends";
            }

            /**
             * 实现
             */
            public static class Implements {
                public static final String PREFIX = "net.lizhaoweb.maker.code.model.implements";
            }

            /**
             * 保存
             */
            public static class JavaFileSave {
                public static final String PATH = "net.lizhaoweb.maker.code.model.file.java.save.path";
            }
        }
    }

    /**
     * 数组
     */
    public static class Array {
        /**
         * 正则
         */
        public static class Regex {
            /**
             * 字符串分割
             */
            public static final String STRING_SEPARATOR = " *, *";
        }
    }

    /**
     * 解析
     */
    public static class Analysis {
        public static final String SWITCH = "net.lizhaoweb.maker.code.model.analysis.switch";
        public static final String INDEXES = "net.lizhaoweb.maker.code.model.analysis.indexes";
        public static final String NAMES = "net.lizhaoweb.maker.code.model.analysis.names";
    }

    /**
     * 翻译
     */
    public static class Translate {
        /**
         * 语言
         */
        public static class Language {
            public static final String SOURCE = "net.lizhaoweb.maker.code.model.translate.lang.source";
            public static final String TARGET = "net.lizhaoweb.maker.code.model.translate.lang.target";
        }
    }
}
