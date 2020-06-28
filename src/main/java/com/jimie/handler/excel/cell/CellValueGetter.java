package com.jimie.handler.excel.cell;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;

/**
 * @author litingjie
 * 2020-6-26
 */
public interface CellValueGetter<T> {
    /**
     * 获取单元格中数据
     * @param cell 单元格
     * @return t 可能字符串、数值、日期等
     */
    T getCellValue(Cell cell);

    /**
     * 单元格类型
     * @return CellType.xxx
     */
    CellType cellType();
}
