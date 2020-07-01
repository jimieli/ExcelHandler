package com.jimie.handler.sql.core.impl;

import com.jimie.handler.sql.bo.ColumnModel;
import com.jimie.handler.sql.constant.BuilderConstant;
import com.jimie.handler.sql.core.BodyBuilder;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

/**
 * @author litingjie
 * 2020-7-1
 */
@Component
public class BodyBuilderImpl implements BodyBuilder {

    private Map<String,String> nameSqlMap;
    private Map<String,String> nameBeanMap;

    public BodyBuilderImpl(Map<String, String> nameSqlMap, Map<String, String> nameBeanMap) {
        this.nameSqlMap = nameSqlMap;
        this.nameBeanMap = nameBeanMap;
    }

    @Override
    public String createSql(List<ColumnModel> list) {

        StringBuilder builder = new StringBuilder();

        list.forEach(model->
            builder.append(model.getName()).append(" ").append(nameSqlMap.get(model.getDataType())).append("(")
                    .append(model.getLength()).append(")").append(",")
        );

        builder.deleteCharAt(builder.length()-1);

        return builder.toString();
    }

    @Override
    public String createBean(List<ColumnModel> list) {
        StringBuilder builder = new StringBuilder();

        list.forEach(model->
            builder.append("/**").append(BuilderConstant.LINE_SPLIT)
                .append("* ").append(model.getComment()).append(BuilderConstant.LINE_SPLIT)
                .append("*/").append(BuilderConstant.LINE_SPLIT)
                .append("private ").append(nameBeanMap.get(model.getDataType())).append(" ").append(model.getName()).append(";").append(BuilderConstant.LINE_SPLIT)
        );

        builder.deleteCharAt(builder.length()-1);

        return builder.toString();
    }
}
