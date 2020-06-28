package com.jimie.handler.excel.core.impl;

import com.jimie.handler.excel.constant.ExcelType;
import com.jimie.handler.excel.core.WorkbookInitializer;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

/**
 * @author litingjie
 * 2020-6-27
 */
@Component
public class XlsWorkbookInitializerImpl implements WorkbookInitializer {
    @Override
    public Workbook initialWorkbook(Path excelPath, Enum<ExcelType> excelTypeEnum) throws IOException {
        return new HSSFWorkbook(Files.newInputStream(excelPath));
    }

    @Override
    public Enum<ExcelType> excelType() {
        return ExcelType.xls;
    }
}
