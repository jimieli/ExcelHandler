package com.jimie.handler.sql.bo;

import java.util.List;

/**
 * @author litingjie
 * 2020-7-1
 */
public class TableModel {

    private String tableName;

    private String beanName;

    private List<ColumnModel> column;

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public String getBeanName() {
        return beanName;
    }

    public void setBeanName(String beanName) {
        this.beanName = beanName;
    }

    public List<ColumnModel> getColumn() {
        return column;
    }

    public void setColumn(List<ColumnModel> column) {
        this.column = column;
    }

    public TableModel(String tableName, String beanName, List<ColumnModel> column) {
        this.tableName = tableName;
        this.beanName = beanName;
        this.column = column;
    }

    public TableModel() {
    }
}
