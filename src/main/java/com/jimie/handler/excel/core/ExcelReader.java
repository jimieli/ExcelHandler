package com.jimie.handler.excel.core;

import com.jimie.handler.excel.constant.ExcelType;

import java.io.IOException;
import java.nio.file.Path;

/**
 * @author litingjie
 * 2020-6-26
 */
public interface  ExcelReader<T> {
    /**
     * 从Excel文件中读取数据
     * @param excelPath 文件path
     * @param excelTypeEnum 文件类型
     * @return 实现的返回数据类型
     * @throws IOException 获取工作簿时可能出现的异常
     */
    T readFromExcel(Path excelPath, Enum<ExcelType> excelTypeEnum) throws IOException;
}
