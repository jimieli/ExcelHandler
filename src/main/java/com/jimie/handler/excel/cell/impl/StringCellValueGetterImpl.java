package com.jimie.handler.excel.cell.impl;

import com.jimie.handler.excel.cell.AbstractCellValueGetter;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.springframework.stereotype.Component;

/**
 * @author litingjie
 * 2020-6-27
 */
@Component
public class StringCellValueGetterImpl extends AbstractCellValueGetter<String> {

    @Override
    protected String findValueInCell(Cell cell) {
        return cell.getStringCellValue();
    }

    @Override
    public CellType cellType() {
        return CellType.STRING;
    }
}
