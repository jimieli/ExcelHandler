package com.jimie.handler.excel.cell.impl;

import com.jimie.handler.excel.cell.AbstractCellValueGetter;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.springframework.stereotype.Component;

/**
 * @author litingjie
 * 2020-7-1
 */
@Component
public class NumberCellValueGetterImpl extends AbstractCellValueGetter<Integer> {
    @Override
    protected Integer findValueInCell(Cell cell) {
        Double numericCellValue = cell.getNumericCellValue();
        return numericCellValue.intValue();
    }

    @Override
    public CellType cellType() {
        return CellType.NUMERIC;
    }
}
