/**
 * Copyright (c) 2016, Stupid Bird and/or its affiliates. All rights reserved.
 * STUPID BIRD PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 * @Project : maker
 * @Package : net.lizhaoweb.maker.code.java.model.excel.read
 * @author <a href="http://www.lizhaoweb.net">李召(John.Lee)</a>
 * @EMAIL 404644381@qq.com
 * @Time : 19:24
 */
package net.lizhaoweb.maker.code.java.model.excel.read;

import net.lizhaoweb.common.util.base.StringUtil;
import net.lizhaoweb.maker.code.java.model.bean.AnalysisSwitch;
import net.lizhaoweb.maker.code.java.model.bean.ClassInformation;
import net.lizhaoweb.maker.code.java.model.bean.Configuration;
import net.lizhaoweb.maker.code.java.model.bean.FieldInformation;
import net.lizhaoweb.maker.code.java.model.exception.AnalysisExcetion;
import net.lizhaoweb.maker.code.java.model.read.IConfigurationFactory;
import net.lizhaoweb.maker.code.java.model.read.TranslateCallable;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.poifs.filesystem.NPOIFSFileSystem;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * <h1>实现 - Excel 文件读取器</h1>
 * <p>
 * Excel 2003 以上版本
 *
 * @author <a href="http://www.lizhaoweb.cn">李召(John.Lee)</a>
 * @version 1.0.0.0.1
 * @notes Created on 2016年11月13日<br>
 * Revision of last commit:$Revision$<br>
 * Author of last commit:$Author$<br>
 * Date of last commit:$Date$<br>
 */
public class ExcelFileReader extends AbstractFileReader {

    public ExcelFileReader(IConfigurationFactory configurationFactory) {
        super(configurationFactory);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Set<ClassInformation> analysis(File file) {
        if (file == null) {
            throw new IllegalArgumentException("The file is null");
        }
        if (!file.exists()) {
            throw new IllegalArgumentException("The file does not exist");
        }
        if (!file.isFile()) {
            throw new IllegalArgumentException("The file is not a file");
        }
        if (!file.canRead()) {
            throw new IllegalArgumentException("The file could not be read");
        }
        String fileName = file.getName();
        String fileSuffix = fileName.substring(fileName.lastIndexOf('.') + 1);
        if (fileSuffix == null) {
            throw new IllegalArgumentException("The file suffix is not found");
        }
        if (!fileSuffix.matches("^(?:xls)|(?:xlsx)|(?:xlsm)|(?:xlsb)$")) {
            throw new IllegalArgumentException("The file suffix is not supported");
        }
        Set<ClassInformation> result = null;
        Workbook workbook = null;
        try {
            // 打开工作薄
            if (fileSuffix.matches("^xls$")) {
                workbook = new HSSFWorkbook(new NPOIFSFileSystem(file));
            } else if (fileSuffix.matches("^(?:xlsx)|(?:xlsm)|(?:xlsb)$")) {
                workbook = new XSSFWorkbook(file);
            }
        } catch (IOException e) {
            throw new IllegalArgumentException(e);
        } catch (InvalidFormatException e) {
            throw new IllegalArgumentException(e);
        }
        Configuration configuration = this.getConfiguration();
        result = this.analysisWorkbook(configuration, workbook);
        return result;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Set<ClassInformation> analysis(InputStream inputStream, String fileSuffix) {
        if (inputStream == null) {
            throw new IllegalArgumentException("The excel input stream is null");
        }
        if (fileSuffix == null) {
            throw new IllegalArgumentException("The file suffix is null");
        }
        if (!fileSuffix.matches("^(?:xls)|(?:xlsx)|(?:xlsm)|(?:xlsb)$")) {
            throw new IllegalArgumentException("The file suffix is not supported");
        }
        Set<ClassInformation> result = null;
        Workbook workbook = null;
        try {
            // 打开工作薄
            if (fileSuffix.matches("^xls$")) {
                workbook = new HSSFWorkbook(new NPOIFSFileSystem(inputStream));
            } else if (fileSuffix.matches("^(?:xlsx)|(?:xlsm)|(?:xlsb)$")) {
                workbook = new XSSFWorkbook(inputStream);
            }
        } catch (IOException e) {
            throw new IllegalArgumentException(e);
        }
        Configuration configuration = this.getConfiguration();
        result = this.analysisWorkbook(configuration, workbook);
        return result;
    }

    // 解析工作薄。
    private Set<ClassInformation> analysisWorkbook(Configuration configuration, Workbook workbook) {
        if (configuration == null) {
            throw new IllegalArgumentException("The configuration is null");
        }
        if (workbook == null) {
            throw new IllegalArgumentException("The excel workbook is null");
        }
        Set<ClassInformation> result = null;
        if (AnalysisSwitch.INDEX == configuration.getAnalysisSwitch()) {
            List<Integer> sheetIndexes = configuration.getAnalysisIndexes();
            result = this.analysisBySheetIndex(configuration, sheetIndexes, workbook);
        } else if (AnalysisSwitch.NAME == configuration.getAnalysisSwitch()) {
            List<String> classNames = configuration.getAnalysisNames();
            List<Integer> sheetIndexes = null;
            if (classNames != null) {
                sheetIndexes = new ArrayList<Integer>();
                for (String className : classNames) {
                    Integer sheetIndex = workbook.getSheetIndex(className);
                    sheetIndexes.add(sheetIndex);
                }
            }
            result = this.analysisBySheetIndex(configuration, sheetIndexes, workbook);
        }
        try {
            this.getGenerator().generate(result, configuration);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        return result;
    }

    // 通过 Sheet 索引解析
    private Set<ClassInformation> analysisBySheetIndex(
            Configuration configuration,
            List<Integer> sheetIndexes,
            Workbook workbook
    ) {
        if (sheetIndexes == null) {
            throw new IllegalArgumentException("The sheet index list is null");
        }
        if (workbook == null) {
            throw new IllegalArgumentException("The excel workbook is null");
        }
        Map<Integer, ClassInformation> classDataMap = new HashMap<Integer, ClassInformation>();

        // 类名
        ExecutorService classNameExecutorService = Executors.newCachedThreadPool();
        List<Future<Map<Integer, String>>> classNameFutureList = new ArrayList<Future<Map<Integer, String>>>();

        // Sheet Map
        Map<Integer, Sheet> sheetMap = new HashMap<Integer, Sheet>();

        for (Integer sheetIndex : sheetIndexes) {
            Sheet sheet = workbook.getSheetAt(sheetIndex);
            sheetMap.put(sheetIndex, sheet);
            this.traversingSheet(
                    configuration,
                    classDataMap,
                    classNameExecutorService,
                    classNameFutureList,
                    sheetIndex,
                    sheet
            );
        }
        return this.getClassDatasFromFuture(
                configuration,
                classDataMap,
                classNameExecutorService,
                classNameFutureList,
                sheetMap
        );
    }

    // 遍历表格
    private void traversingSheet(
            Configuration configuration,// 配置对象
            Map<Integer, ClassInformation> classInformationMap,// 类数据存储器
            ExecutorService classNameExecutorService,// 类名异步生成执行服务
            List<Future<Map<Integer, String>>> classNameFutureList,// 类名异步回调
            Integer sheetIndex,// Excel表格索引
            Sheet sheet// Excel表格
    ) {
        if (configuration == null) {
            throw new IllegalArgumentException("The configuration is null");
        }
        if (classInformationMap == null) {
            throw new IllegalArgumentException("The class information map is null");
        }
        if (classNameExecutorService == null) {
            throw new IllegalArgumentException("The class name executor service is null");
        }
        if (classNameFutureList == null) {
            throw new IllegalArgumentException("The class name future list is null");
        }
        if (sheetIndex == null) {
            throw new IllegalArgumentException("The sheet index is null");
        }
        if (sheet == null) {
            throw new IllegalArgumentException("The sheet is null");
        }
//        Workbook workbook = sheet.getWorkbook();
//        Integer sheetIndex = workbook.getSheetIndex(sheet);

        ClassInformation classData = new ClassInformation();
        classData.setDescribe(sheet.getSheetName());
        classData.setTitle(sheet.getSheetName());
        classData.setPackageName(configuration.getPackageName(sheetIndex));
        classInformationMap.put(sheetIndex, classData);

        // 异步获取类名
        String sheetName = sheet.getSheetName();
        Future<Map<Integer, String>> classNameFuture = classNameExecutorService.submit(
                new TranslateCallable(this.getTranslator(), configuration, sheetIndex, sheetName)
        );
        classNameFutureList.add(classNameFuture);
    }

    // 从异步线程构建类数据
    private Set<ClassInformation> getClassDatasFromFuture(
            Configuration configuration,// 配置对象
            Map<Integer, ClassInformation> classInformationMap,// 类数据存储器
            ExecutorService classNameExecutorService,// 类名生成异步执行服务
            List<Future<Map<Integer, String>>> classNameFutureList,// 类名异步回调
            Map<Integer, Sheet> sheetMap// Sheet Map
    ) {
        if (configuration == null) {
            throw new IllegalArgumentException("The configuration is null");
        }
        if (classInformationMap == null) {
            throw new IllegalArgumentException("The class information map is null");
        }
        if (classNameExecutorService == null) {
            throw new IllegalArgumentException("The class name executor service is null");
        }
        if (classNameFutureList == null) {
            throw new IllegalArgumentException("The class name future list is null");
        }
        if (sheetMap == null) {
            throw new IllegalArgumentException("The sheet map is null");
        }
        try {
            // 解析 Sheet
            Set<Map.Entry<Integer, Sheet>> sheetMapEntrySet = sheetMap.entrySet();
            for (Map.Entry<Integer, Sheet> sheetMapEntry : sheetMapEntrySet) {
                Integer sheetIndex = sheetMapEntry.getKey();
                Sheet sheet = sheetMapEntry.getValue();
                Set<FieldInformation> fieldDataSet = this.analysisSheet(configuration, sheet);
                classInformationMap.get(sheetIndex).setFieldSet(fieldDataSet);
            }

            // 遍历 classNameFutureList
            for (Future<Map<Integer, String>> future : classNameFutureList) {
                Map<Integer, String> classNameMap = future.get();
                for (Map.Entry<Integer, String> classNameMapEntry : classNameMap.entrySet()) {
                    Integer key = classNameMapEntry.getKey();
                    String value = classNameMapEntry.getValue();
                    classInformationMap.get(key).setName(StringUtil.capitalize(value));
                }
            }
        } catch (Exception e) {
            throw new AnalysisExcetion(e);
        } finally {
            classNameExecutorService.shutdown();
        }

        Set<ClassInformation> result = new HashSet<ClassInformation>(classInformationMap.values());
        return result;
    }

    // 解析表格
    private Set<FieldInformation> analysisSheet(Configuration configuration, Sheet sheet) {
        if (configuration == null) {
            throw new IllegalArgumentException("The configuration is null");
        }
        if (sheet == null) {
            throw new IllegalArgumentException("The sheet is null");
        }

        // Sheet 的行数
        int rowSize = sheet.getLastRowNum();
        if (rowSize < 1) {
            throw new IllegalArgumentException("The sheet rows number less than 1");
        }

        // Sheet 的标题行
        Row titleRow = sheet.getRow(0);
        if (titleRow == null) {
            throw new IllegalArgumentException("The sheet title row is not found");
        }

        // Sheet 的列数
        int columnSize = titleRow.getLastCellNum();
        if (columnSize < 1) {
            throw new IllegalArgumentException("The sheet columns number less than 1");
        }

        // 字段数据存储器
        Map<Integer, FieldInformation> fieldInformationMap = new HashMap<Integer, FieldInformation>();

        // 字段名
        ExecutorService fieldNameExecutorService = Executors.newCachedThreadPool();
        List<Future<Map<Integer, String>>> fieldNameFutureList = new ArrayList<Future<Map<Integer, String>>>();

        // 解析 Sheet 标题行
        for (int columnIndex = 0; columnIndex < columnSize; columnIndex++) {
            FieldInformation fieldInformation = new FieldInformation();
            Cell cell = titleRow.getCell(columnIndex);
            String titleContent = cell.getStringCellValue();
            fieldInformation.setTitle(titleContent);
            fieldInformation.setDescribe(titleContent);
            fieldInformation.setType("String");
            fieldInformationMap.put(columnIndex, fieldInformation);

            // 异步获取字段名
            Future<Map<Integer, String>> classNameFuture = fieldNameExecutorService.submit(
                    new TranslateCallable(this.getTranslator(), configuration, columnIndex, titleContent)
            );
            fieldNameFutureList.add(classNameFuture);
        }

        // 解析 Sheet 数据行
        for (int rowIndex = 1; rowIndex <= rowSize; rowIndex++) {
            Row dataRow = sheet.getRow(rowIndex);
            if (dataRow == null) {// 无效行
                continue;
            }
            if (dataRow.getLastCellNum() < columnSize) {// 列数不足
                continue;
            }
            for (int columnIndex = 0; columnIndex < columnSize; columnIndex++) {
                FieldInformation fieldInformation = fieldInformationMap.get(columnIndex);
                Cell cell = dataRow.getCell(columnIndex);
                CellType cellType = cell.getCellTypeEnum();
                if (CellType.BOOLEAN == cellType) {
                    fieldInformation.setType("java.lang.Boolean");
                } else if (CellType.NUMERIC == cellType) {
                    try {
                        Date dateValue = cell.getDateCellValue();
                        if (dateValue == null) {
                            fieldInformation.setType("java.lang.Double");
                        } else {
                            fieldInformation.setType("java.util.Date");
                        }
                    } catch (Exception e) {
                        fieldInformation.setType("java.lang.Double");
                    }
                }
            }
        }

        // 组装字段名
        for (Future<Map<Integer, String>> future : fieldNameFutureList) {
            try {
                Map<Integer, String> fieldNameMap = future.get();
                for (Map.Entry<Integer, String> fieldNameMapEntry : fieldNameMap.entrySet()) {
                    Integer key = fieldNameMapEntry.getKey();
                    String value = fieldNameMapEntry.getValue();
                    fieldInformationMap.get(key).setName(StringUtil.uncapitalize(value));
                }
            } catch (Exception e) {
                logger.error(e.getMessage(), e);
            }
        }
        fieldNameExecutorService.shutdown();

        Set<FieldInformation> result = new HashSet<FieldInformation>(fieldInformationMap.values());
        return result;
    }
}
