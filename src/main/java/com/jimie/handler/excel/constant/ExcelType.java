package com.jimie.handler.excel.constant;

/**
 * @author litingjie
 * 2020-6-26
 */
public enum ExcelType {
    /**
     * Excel 97-2003 工作簿
     */
    xls("xls"),
    /**
     * 工作簿
     */
    xlsx("xlsx");

    private String suffix;

    ExcelType(String suffix) {
        this.suffix = suffix;
    }
}
