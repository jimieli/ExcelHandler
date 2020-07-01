package com.jimie.handler.sql.core;

import com.jimie.handler.sql.bo.TableModel;

/**
 * @author litingjie
 * 2020-7-1
 */
public interface HeadBuilder {

    String createSql(TableModel model);

    String createBean(TableModel model);
}
