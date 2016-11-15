/**
 * Copyright (c) 2016, Stupid Bird and/or its affiliates. All rights reserved.
 * STUPID BIRD PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 * @Project : maker
 * @Package : net.lizhaoweb.maker.code.java.model.read
 * @author <a href="http://www.lizhaoweb.net">李召(John.Lee)</a>
 * @EMAIL 404644381@qq.com
 * @Time : 19:55
 */
package net.lizhaoweb.maker.code.java.model.read;

import net.lizhaoweb.common.translate.api.ITranslator;
import net.lizhaoweb.common.translate.model.TranslationRequest;
import net.lizhaoweb.common.translate.model.TranslationResponse;
import net.lizhaoweb.common.util.base.StringUtil;
import net.lizhaoweb.maker.code.java.model.bean.Configuration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

/**
 * @author <a href="http://www.lizhaoweb.cn">李召(John.Lee)</a>
 * @version 1.0.0.0.1
 * @notes Created on 2016年11月13日<br>
 * Revision of last commit:$Revision$<br>
 * Author of last commit:$Author$<br>
 * Date of last commit:$Date$<br>
 */
public class TranslateCallable implements ITranslateCallable {

    protected Logger logger = LoggerFactory.getLogger(this.getClass());

    /**
     * 翻译器
     */
    private ITranslator translator;

    /**
     * 配置
     */
    private Configuration configuration;

    /**
     * 索引
     */
    private Integer index;

    /**
     * 原文
     */
    private String original;

    public TranslateCallable(ITranslator translator, Configuration configuration, Integer index, String original) {
        this.translator = translator;
        this.configuration = configuration;
        this.index = index;
        this.original = original;
    }

    @Override
    public Map<Integer, String> call() throws Exception {
//        logger.debug(">>>>>>>>>>>>>>======= " + original);
        String target = this.getTarget();
        Map<Integer, String> languageTranslateMap = new HashMap<Integer, String>();
        languageTranslateMap.put(index, target);
        return languageTranslateMap;
    }

    // 获取目标字符串
    private String getTarget() {
        String original = this.original.trim();
        if (original.matches("^[a-zA-Z_]+$")) {
            return original;
        }
        String replaceOriginal = original.replaceAll("[\\pP\\p{Punct}]", "").replaceAll("\\s", "");
        if (replaceOriginal.matches("^[a-zA-Z_]+$")) {
            return replaceOriginal;
        }
        String translation = this.getTranslation();

        translation = replaceFromDictionary(translation); // 按字典替换。
        translation = StringUtil.uppercasingFirstLetterOfEachWord(translation);// 每个单词首字母大写
        translation = translation.replaceAll("[\\pP\\p{Punct}]", "");// 去除标点符号
        translation = translation.replaceAll("\\s", "");// 去除空白字符

        if (translation.matches("^\\d+$")) { // 纯数字
            translation = "_" + translation;
        } else {
            translation = translation.replaceAll("^(\\d+)([^\\d]+)$", "$2$1");// 将开头的数字放到末尾
        }
        logger.debug("========= {}", translation);
        return translation;
    }

    // 获取译文
    private String getTranslation() {
        // 调用翻译接口
        TranslationRequest request = new TranslationRequest(original, configuration.getSource(), configuration.getTarget());
        TranslationResponse translationResponse = translator.translate(request);

        String translation = translationResponse.getTranslation();// 获取译文
        if (translation == null) {
            return "";
        }
        return translation;
    }

    // 按字典替换。
    private String replaceFromDictionary(String translation) {
        if (StringUtil.isBlank(translation)) {
            return translation;
        }

        // 将 - 换成 _ 。
        while (StringUtil.include(translation, "-")) {
            translation = translation.replace('-', '_');
        }

        Map<String, String> dictionary = configuration.getReplaceDictionary();
        if (dictionary != null && dictionary.size() > 0) {
            translation = translation.toLowerCase();
            for (Map.Entry<String, String> dictionaryEntry : dictionary.entrySet()) {
                String key = dictionaryEntry.getKey();
                String value = dictionaryEntry.getValue();
                translation = translation.replaceAll(String.format("^%s ", key), value + " ");//替换头
                translation = translation.replaceAll(String.format(" %s$", key), " " + value);//替换尾

                // 替换中间的
                String search = String.format(" %s ", key);
                String replacement = String.format(" %s ", value);
                while (StringUtil.include(translation, search)) {
                    translation = translation.replace(search, replacement);
                }
            }
        }
        return translation;
    }
}
