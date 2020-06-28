package com.jimie.handler.excel.cell;

import com.jimie.handler.excel.check.NullChecker;
import org.apache.poi.ss.usermodel.Cell;

/**
 * @author litingjie
 * 2020-6-26
 */
public abstract class AbstractCellValueGetter<T> implements CellValueGetter {

    @Override
    public T getCellValue(Cell cell){
        NullChecker.isNull(cell);
        return findValueInCell(cell);
    }

    /**
     * 从cell中取出value
     * @param cell 单元格
     * @return T
     */
    protected abstract T findValueInCell(Cell cell);
}
