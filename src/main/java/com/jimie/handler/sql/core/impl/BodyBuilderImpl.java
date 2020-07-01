package com.jimie.handler.sql.core.impl;

import com.jimie.handler.sql.bo.ColumnModel;
import com.jimie.handler.sql.constant.BuilderConstant;
import com.jimie.handler.sql.core.BodyBuilder;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author litingjie
 * 2020-7-1
 */
@Component
public class BodyBuilderImpl implements BodyBuilder {

    private Map<String,String> nameSqlMap;
    private Map<String,String> nameBeanMap;
    private Set<String> ignorePropertySet;

    public BodyBuilderImpl(Map<String, String> nameSqlMap, Map<String, String> nameBeanMap, Set<String> ignorePropertySet) {
        this.nameSqlMap = nameSqlMap;
        this.nameBeanMap = nameBeanMap;
        this.ignorePropertySet = ignorePropertySet;
    }

    @Override
    public String createSql(List<ColumnModel> list) {

        StringBuilder builder = new StringBuilder();

        StringBuilder primaryKey = new StringBuilder();

        list.forEach(model->{

            builder.append("[");
            builder.append(model.getName());
            builder.append("]");

            builder.append(" ").append(nameSqlMap.get(model.getDataType()));

            if(model.getLength() != null){
                builder.append("(").append(model.getLength()).append(")");
            }

            if(model.isPrimary()){
                primaryKey.append(",").append(model.getName());
            }

            builder.append(",");
        });


        if(primaryKey.length()>0){
            primaryKey.deleteCharAt(0);
            builder.append("primary key(").append(primaryKey).append(")");
        }else{
            builder.deleteCharAt(builder.length()-1);
        }

        return builder.toString();
    }

    @Override
    public String createBean(List<ColumnModel> list) {
        StringBuilder builder = new StringBuilder();

        list.forEach(model->{
            if(ignorePropertySet.contains(model.getName())){
                return;
            }

            builder.append(BuilderConstant.BEAN_BEFORE).append("/**").append(BuilderConstant.LINE_SPLIT)
                    .append(BuilderConstant.BEAN_BEFORE).append("* ").append(model.getComment()).append(BuilderConstant.LINE_SPLIT)
                    .append(BuilderConstant.BEAN_BEFORE).append("*/").append(BuilderConstant.LINE_SPLIT)
                    .append(BuilderConstant.BEAN_BEFORE).append("private ").append(nameBeanMap.get(model.getDataType())).append(" ").append(model.getName()).append(";").append(BuilderConstant.LINE_SPLIT);
        });

        builder.deleteCharAt(builder.length()-1);

        return builder.toString();
    }
}
