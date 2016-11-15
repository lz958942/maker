/**
 * Copyright (c) 2016, Stupid Bird and/or its affiliates. All rights reserved.
 * STUPID BIRD PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 * @Project : maker
 * @Package : net.lizhaoweb.maker.code.java.model.excel.read
 * @author <a href="http://www.lizhaoweb.net">李召(John.Lee)</a>
 * @EMAIL 404644381@qq.com
 * @Time : 21:44
 */
package net.lizhaoweb.maker.code.java.model.excel.read;

import net.lizhaoweb.common.util.base.StringUtil;
import net.lizhaoweb.maker.code.java.model.bean.ClassInformation;
import net.lizhaoweb.maker.code.java.model.bean.FieldInformation;
import net.lizhaoweb.maker.code.java.model.read.IFileReader;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.io.FileNotFoundException;
import java.util.Set;

/**
 * @author <a href="http://www.lizhaoweb.cn">李召(John.Lee)</a>
 * @version 1.0.0.0.1
 * @notes Created on 2016年11月13日<br>
 * Revision of last commit:$Revision$<br>
 * Author of last commit:$Author$<br>
 * Date of last commit:$Date$<br>
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({
        "classpath*:/schema/spring/application.xml"
})
public class TestExcelFileReader {

    @Autowired
    private IFileReader reader;

    @Test
    public void analysis() throws FileNotFoundException {
//        InputStream inputStream = new FileInputStream("D:\\DataReport.xlsx");
//        Set<ClassInformation> result = reader.analysis(inputStream, "xls");
        Set<ClassInformation> result = reader.analysis("D:\\DataReport.xlsx");
//        System.out.println(result);
        for (ClassInformation classInformation : result) {
            String classInfo = String.format("[%s] %s", classInformation.getName(), classInformation);
            System.out.println(StringUtil.center(classInfo, 84));
            for (FieldInformation fieldInformation : classInformation.getFieldSet()) {
                String fieldInfo = String.format("[%s] %s", fieldInformation.getName(), fieldInformation);
                System.out.println(fieldInfo);
            }
        }
    }

    @Test
    public void aaa() {
        String string = "double";
        int aaa = string.lastIndexOf('.');
        String subString = string.substring(aaa + 1);
        System.out.println(subString);
    }

    @Test
    public void bbb() {
        String string = "java.lang.Double";
        boolean me = string.matches("^java\\.lang\\.[^.]+$");
        System.out.println(me);
    }
}
