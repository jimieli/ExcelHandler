package com.jimie.handler.sql.core;

import com.jimie.handler.sql.bo.ColumnModel;

import java.util.List;

/**
 * @author litingjie
 * 2020-7-1
 */
public interface BodyBuilder {

    String createSql(List<ColumnModel> list);

    String createBean(List<ColumnModel> list);
}
