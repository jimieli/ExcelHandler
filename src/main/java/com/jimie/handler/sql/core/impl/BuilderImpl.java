package com.jimie.handler.sql.core.impl;

import com.jimie.handler.sql.bo.TableModel;
import com.jimie.handler.sql.core.BodyBuilder;
import com.jimie.handler.sql.core.Builder;
import com.jimie.handler.sql.core.HeadBuilder;
import org.springframework.stereotype.Component;

/**
 * @author litingjie
 * 2020-7-1
 */
@Component
public class BuilderImpl implements Builder {

    private BodyBuilder bodyBuilder;
    private HeadBuilder headBuilder;

    public BuilderImpl(BodyBuilder bodyBuilder, HeadBuilder headBuilder) {
        this.bodyBuilder = bodyBuilder;
        this.headBuilder = headBuilder;
    }

    @Override
    public String createTable(TableModel model) {
        String baseSql = headBuilder.createSql(model);
        String columnSql = bodyBuilder.createSql(model.getColumn());

        String sql = String.format(baseSql,columnSql);


        System.out.println(sql);


        return sql;
    }

    @Override
    public String createBean(TableModel model) {

        String baseBean = headBuilder.createBean(model);
        String columnBean = bodyBuilder.createBean(model.getColumn());

        String bean = String.format(baseBean,columnBean);

        System.out.println(bean);

        return bean;
    }
}
