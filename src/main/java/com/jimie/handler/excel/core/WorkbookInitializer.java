package com.jimie.handler.excel.core;

import com.jimie.handler.excel.constant.ExcelType;
import org.apache.poi.ss.usermodel.Workbook;

import java.io.IOException;
import java.nio.file.Path;

/**
 * @author litingjie
 * 2020-6-27
 */
public interface WorkbookInitializer {

    /**
     * 工作簿构建器
     * @param excelPath 文件path
     * @param excelTypeEnum Excel文件类型
     * @return 工作簿
     */
    Workbook initialWorkbook(Path excelPath, Enum<ExcelType> excelTypeEnum) throws IOException;

    /**
     * Excel文件类型枚举
     * @return enum
     */
    Enum<ExcelType> excelType();
}
