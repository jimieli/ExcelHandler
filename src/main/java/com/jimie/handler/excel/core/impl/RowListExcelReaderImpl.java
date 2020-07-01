package com.jimie.handler.excel.core.impl;

import com.jimie.handler.excel.cell.CellValueGetter;
import com.jimie.handler.excel.constant.ExcelType;
import com.jimie.handler.excel.core.ExcelReader;
import com.jimie.handler.excel.core.WorkbookInitializer;
import org.apache.poi.ss.usermodel.*;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.file.Path;
import java.util.*;

/**
 * @author litingjie
 * 2020-6-27
 */
@Component
public class RowListExcelReaderImpl implements ExcelReader<Map<String,List<List<Object>>>> {

    private Map<CellType, CellValueGetter> getterMap;
    private Map<Enum<ExcelType>, WorkbookInitializer> initializerMap;

    public RowListExcelReaderImpl(List<CellValueGetter> getterList, List<WorkbookInitializer> initializerList) {

        initializerMap = new HashMap<>(initializerList.size());
        initializerList.forEach(initializer->initializerMap.put(initializer.excelType(),initializer));

        getterMap = new HashMap<>(getterList.size());
        getterList.forEach(getter->getterMap.put(getter.cellType(),getter));
    }

    @Override
    public Map<String,List<List<Object>>> readFromExcel(Path excelPath, Enum<ExcelType> excelTypeEnum) throws IOException {
        Workbook workbook;
        workbook = initWorkbookUserExcelType(excelPath,excelTypeEnum);
        Map<String,List<List<Object>>> result = new HashMap<>(workbook.getNumberOfSheets());
        //获取每一个sheet中的元素,单线程版本,可以优化为并行版本提高效率
        workbook.forEach(sheet->result.put(sheet.getSheetName(),sheetFinder(sheet)));
        return result;
    }

    /**
     * 解析sheet返回所有行数据的集合
     */
    private List<List<Object>> sheetFinder(Sheet sheet){
        List<List<Object>> result = new ArrayList<>(sheet.getLastRowNum() + 1);
        sheet.forEach(row-> result.add(rowFinder(row)));
        return result;
    }

    /**
     * 将行数据转换为行所有单元格的数据的集合
     */
    private List<Object> rowFinder(Row row){
        int capacity = row.getLastCellNum();

        if(capacity<1){
            return Collections.emptyList();
        }

        List<Object> result = new ArrayList<>(capacity);
        for (int i = 0; i < row.getLastCellNum(); i++) {
            Cell cell = row.getCell(i);

            if(cell == null){
                result.add("");
                continue;
            }

            CellValueGetter cellValueGetter = getterMap.get(cell.getCellType());
            if(cellValueGetter == null){
                throw new RuntimeException("未找到cellType的实现类:"+cell.getCellType());
            }
            Object cellValue = cellValueGetter.getCellValue(cell);
            result.add(cellValue);
        }

        return result;
    }

    /**
     * 初始化工作簿
     */
    private Workbook initWorkbookUserExcelType(Path excelPath, Enum<ExcelType> excelTypeEnum) throws IOException {
        WorkbookInitializer workbookInitializer = initializerMap.get(excelTypeEnum);
        if(workbookInitializer == null){
            throw new RuntimeException("没有对应的工作簿初始化构造器");
        }
        return workbookInitializer.initialWorkbook(excelPath,excelTypeEnum);
    }
}
