package com.jimie.handler.sql.core;

import com.jimie.handler.sql.bo.TableModel;

import java.util.Map;

/**
 * @author litingjie
 * 2020-7-1
 */
public interface Builder {

    /**
     * 生成建表语句
     * @param model 表数据模型
     * @return 建表语句字符串
     */
    String createTable(TableModel model);

    /**
     * 返回java类名,及其内容
     * @param model 表数据模型
     * @return map
     */
    Map<String,String> createBean(TableModel model);
}
