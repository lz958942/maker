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

import net.lizhaoweb.maker.code.java.model.bean.ClassInformation;
import net.lizhaoweb.maker.code.java.model.read.IConfigurationFactory;

import java.io.File;
import java.io.InputStream;
import java.util.Set;

/**
 * <h1>实现 - Excel 文件读取器</h1>
 *
 * @author <a href="http://www.lizhaoweb.cn">李召(John.Lee)</a>
 * @version 1.0.0.0.1
 * @notes Created on 2016年11月13日<br>
 * Revision of last commit:$Revision$<br>
 * Author of last commit:$Author$<br>
 * Date of last commit:$Date$<br>
 */
public class ExcelFileReader4JXL extends AbstractFileReader {

    public ExcelFileReader4JXL(IConfigurationFactory configurationFactory) {
        super(configurationFactory);
    }

//    private static final String OS_CHARSET_ENCODING = System.getProperty("sun.jnu.encoding");
//
//    private Configuration configuration;
//    private WorkbookSettings workbookSettings;
//
//    /**
//     * 翻译器
//     */
//    @Setter
//    private ITranslator translator;
//
//    public ExcelFileReader4JXL(IConfigurationFactory configurationFactory) {
//        super();
//        configuration = configurationFactory.getConfiguration();
//
//        workbookSettings = new WorkbookSettings();
//        workbookSettings.setEncoding(OS_CHARSET_ENCODING);
//    }
//
//    /**
//     * {@inheritDoc}
//     */
//    @Override
//    public Set<ClassInformation> analysis(File file) {
//        Set<ClassInformation> result = null;
//        try {
////            Workbook workbook = Workbook.getWorkbook(file);// 打开工作薄
//            Workbook workbook = Workbook.getWorkbook(file, workbookSettings);// 打开工作薄
//            result = this.analysisWorkbook(configuration, workbook);
//        } catch (Exception e) {
//            throw new AnalysisExcetion(e);
//        }
//        return result;
//    }
//
//    /**
//     * {@inheritDoc}
//     */
//    @Override
//    public Set<ClassInformation> analysis(InputStream inputStream) {
//        Set<ClassInformation> result = null;
//        try {
////            Workbook workbook = Workbook.getWorkbook(inputStream); // 打开工作薄
//            Workbook workbook = Workbook.getWorkbook(inputStream, workbookSettings); // 打开工作薄
//            result = this.analysisWorkbook(configuration, workbook);
//        } catch (Exception e) {
//            throw new AnalysisExcetion(e);
//        }
//        return result;
//    }
//
//    // 解析工作薄。
//    private Set<ClassInformation> analysisWorkbook(Configuration configuration, Workbook workbook) {
//        Set<ClassInformation> result = null;
//        if (AnalysisSwitch.INDEX == configuration.getAnalysisSwitch()) {
//            result = this.analysisBySheetIndex(configuration, workbook);
//        } else if (AnalysisSwitch.NAME == configuration.getAnalysisSwitch()) {
//            result = this.analysisBySheetName(configuration, workbook);
//        }
//        return result;
//    }
//
//    // 通过 Sheet 索引解析
//    private Set<ClassInformation> analysisBySheetIndex(Configuration configuration, Workbook workbook) {
//        List<Integer> classIndexes = configuration.getAnalysisIndexes();
//        if (classIndexes == null) {
//            return null;
//        }
//        Map<Integer, ClassInformation> classDataMap = new HashMap<Integer, ClassInformation>();
//
//        // 类名
//        ExecutorService classNameExecutorService = Executors.newCachedThreadPool();
//        List<Future<Map<Integer, String>>> classNameFutureList = new ArrayList<Future<Map<Integer, String>>>();
//
//        // Sheet Map
//        Map<Integer, Sheet> sheetMap = new HashMap<Integer, Sheet>();
//
//        for (Integer sheetIndex : classIndexes) {
//            Sheet sheet = workbook.getSheet(sheetIndex);
////            System.out.println(sheet);
//            sheetMap.put(sheetIndex, sheet);
//            this.traversingSheet(
//                    configuration,
//                    classDataMap,
//                    classNameExecutorService,
//                    classNameFutureList,
//                    sheetIndex,
//                    sheet
//            );
//        }
//        return this.getClassDatasFromFuture(
//                configuration,
//                classDataMap,
//                classNameExecutorService,
//                classNameFutureList,
//                sheetMap
//        );
//    }
//
//    // 通过 Sheet 名解析
//    private Set<ClassInformation> analysisBySheetName(Configuration configuration, Workbook workbook) {
//        Set<ClassInformation> result = null;
//        List<String> classNames = configuration.getAnalysisNames();
//        if (classNames == null) {
//            return null;
//        }
//        Map<Integer, ClassInformation> classDataMap = new HashMap<Integer, ClassInformation>();
//
//        // 类名
//        ExecutorService classNameExecutorService = Executors.newCachedThreadPool();
//        List<Future<Map<Integer, String>>> classNameFutureList = new ArrayList<Future<Map<Integer, String>>>();
//
//        // Sheet Map
//        Map<Integer, Sheet> sheetMap = new HashMap<Integer, Sheet>();
//
//        // 计算 Sheet 的名字和索引的对应关系
//        String[] sheetNames = workbook.getSheetNames();
//        Map<String, Integer> sheetNameAndIndexMap = new HashMap<String, Integer>();
//        for (int sheetIndex = 0; sheetIndex < sheetNames.length; sheetIndex++) {
//            sheetNameAndIndexMap.put(sheetNames[sheetIndex], sheetIndex);
//        }
//
//        for (String sheetName : classNames) {
//            Sheet sheet = workbook.getSheet(sheetName);
//            int sheetIndex = sheetNameAndIndexMap.get(sheetName);
//            sheetMap.put(sheetIndex, sheet);
//            this.traversingSheet(
//                    configuration,
//                    classDataMap,
//                    classNameExecutorService,
//                    classNameFutureList,
//                    sheetIndex,
//                    sheet
//            );
//        }
//        return this.getClassDatasFromFuture(
//                configuration,
//                classDataMap,
//                classNameExecutorService,
//                classNameFutureList,
//                sheetMap
//        );
//    }
//
//    // 遍历表格
//    private void traversingSheet(
//            Configuration configuration,// 配置对象
//            Map<Integer, ClassInformation> classDataMap,// 类数据存储器
//            ExecutorService classNameExecutorService,// 类名异步生成执行服务
//            List<Future<Map<Integer, String>>> classNameFutureList,// 类名异步回调
//            Integer sheetIndex,// Excel表格索引
//            Sheet sheet// Excel表格
//    ) {
//        ClassInformation classData = new ClassInformation();
//        classData.setDescribe(sheet.getName());
//        classData.setTitle(sheet.getName());
//        classData.setPackageName(configuration.getPackageName(sheetIndex));
//        classDataMap.put(sheetIndex, classData);
//
//        String sheetName = sheet.getName();
//
//        // 异步获取类名
//        Future<Map<Integer, String>> classNameFuture = classNameExecutorService.submit(
//                new TranslateCallable(translator, configuration, sheetIndex, sheetName)
//        );
//        classNameFutureList.add(classNameFuture);
//    }
//
//    // 从异步线程构建类数据
//    private Set<ClassInformation> getClassDatasFromFuture(
//            Configuration configuration,// 配置对象
//            Map<Integer, ClassInformation> classDataMap,// 类数据存储器
//            ExecutorService classNameExecutorService,// 类名生成异步执行服务
//            List<Future<Map<Integer, String>>> classNameFutureList,// 类名异步回调
//            Map<Integer, Sheet> sheetMap// Sheet Map
//    ) {
//        try {
//            // 解析 Sheet
//            Set<Map.Entry<Integer, Sheet>> sheetMapEntrySet = sheetMap.entrySet();
//            for (Map.Entry<Integer, Sheet> sheetMapEntry : sheetMapEntrySet) {
//                Integer sheetIndex = sheetMapEntry.getKey();
//                Sheet sheet = sheetMapEntry.getValue();
//                Set<FieldInformation> fieldDataSet = this.analysisSheet(configuration, sheet);
//                classDataMap.get(sheetIndex).setFieldSet(fieldDataSet);
//            }
//
//            // 遍历 classNameFutureList
//            for (Future<Map<Integer, String>> future : classNameFutureList) {
//                Map<Integer, String> classNameMap = future.get();
//                for (Map.Entry<Integer, String> classNameMapEntry : classNameMap.entrySet()) {
//                    Integer key = classNameMapEntry.getKey();
//                    String value = classNameMapEntry.getValue();
//                    classDataMap.get(key).setName(StringUtil.uncapitalize(value));
//                }
//            }
//        } catch (Exception e) {
//            throw new RuntimeException(e);
//        } finally {
//            classNameExecutorService.shutdown();
//        }
//
//        Set<ClassInformation> result = new HashSet<ClassInformation>(classDataMap.values());
//        return result;
//    }
//
//    // 解析表格
//    private Set<FieldInformation> analysisSheet(Configuration configuration, Sheet sheet) {
//        int columnSize = sheet.getColumns();
//        int rowSize = sheet.getRows();
//        Cell[] titleRow = sheet.getRow(0);
//
//        // 判断标题是否存在
//        if (titleRow == null) {
//            throw new AnalysisExcetion("Field title is not found");
//        }
//        if (rowSize < 1 || titleRow.length < 1) {
//            throw new AnalysisExcetion("Field title is not found");
//        }
//
//        // 字段数据存储器
//        Map<Integer, FieldInformation> fieldDataMap = new HashMap<Integer, FieldInformation>();
//
//        // 字段名
//        ExecutorService fieldNameExecutorService = Executors.newCachedThreadPool();
//        List<Future<Map<Integer, String>>> fieldNameFutureList = new ArrayList<Future<Map<Integer, String>>>();
//
//        // 解析 Sheet
//        for (int columnIndex = 0; columnIndex < columnSize; columnIndex++) {
//            Cell[] column = sheet.getColumn(columnIndex);
//            if (column[0] == null) {//标题不存在
//                continue;
//            }
//            FieldInformation fieldData = new FieldInformation();
//            for (int cellIndex = 0; cellIndex < column.length; cellIndex++) {
//                Cell cell = column[cellIndex];
//                if (cellIndex == 0) {
//                    String titleContent = cell.getContents();
//
//                    fieldData.setTitle(titleContent);
//                    fieldData.setDescribe(titleContent);
//                    fieldData.setType("String");
//
//                    // 异步获取字段名
//                    Future<Map<Integer, String>> classNameFuture = fieldNameExecutorService.submit(
//                            new TranslateCallable(translator, configuration, columnIndex, titleContent)
//                    );
//                    fieldNameFutureList.add(classNameFuture);
//                } else if (CellType.BOOLEAN == cell.getType()) {
//                    fieldData.setType(CellType.BOOLEAN.toString());
//                } else if (CellType.NUMBER == cell.getType()) {
//                    fieldData.setType("Double");
//                } else if (CellType.DATE == cell.getType()) {
//                    fieldData.setType(CellType.DATE.toString());
//                }
//            }
//            fieldDataMap.put(columnIndex, fieldData);
//        }
//
//        // 组装字段名
//        for (Future<Map<Integer, String>> future : fieldNameFutureList) {
//            try {
//                Map<Integer, String> fieldNameMap = future.get();
//                for (Map.Entry<Integer, String> fieldNameMapEntry : fieldNameMap.entrySet()) {
//                    Integer key = fieldNameMapEntry.getKey();
//                    String value = fieldNameMapEntry.getValue();
//                    fieldDataMap.get(key).setName(StringUtil.uncapitalize(value));
//                }
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            } catch (ExecutionException e) {
//                e.printStackTrace();
//            }
//        }
//        fieldNameExecutorService.shutdown();
//
//        Set<FieldInformation> result = new HashSet<FieldInformation>(fieldDataMap.values());
//        return result;
//    }
//
//    public static String cutOneByte(String str) {
//        if (str != null && !str.equals("")) {
//            byte[] a = str.getBytes();// 转化为字节流处理,去掉最前面一个字节，以防止第一个字母乱码
//            byte[] b = new byte[a.length - 1];
//            for (int i = 1; i < a.length; i++) {
//                b[i - 1] = a[i];
//            }
//            str = new String(b);
//        }
//        return str;
//    }

    @Override
    public Set<ClassInformation> analysis(File file) {
        return null;
    }

    @Override
    public Set<ClassInformation> analysis(InputStream inputStream, String fileSuffix) {
        return null;
    }
}
