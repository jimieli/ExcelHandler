package com.jimie.handler.sql.bo;

/**
 * @author litingjie
 * 2020-6-30
 */
public class ColumnModel {

    private String name;
    private String comment;
    private String dataType;
    private Integer length;
    private boolean required;
    private boolean primary;
    private Object defaultValue;

    public ColumnModel() {
    }

    public ColumnModel(String name, String comment, String dataType, Integer length, boolean required, boolean primary, Object defaultValue) {
        this.name = name;
        this.comment = comment;
        this.dataType = dataType;
        this.length = length;
        this.required = required;
        this.primary = primary;
        this.defaultValue = defaultValue;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getDataType() {
        return dataType;
    }

    public void setDataType(String dataType) {
        this.dataType = dataType;
    }

    public Integer getLength() {
        return length;
    }

    public void setLength(Integer length) {
        this.length = length;
    }

    public boolean isRequired() {
        return required;
    }

    public void setRequired(boolean required) {
        this.required = required;
    }

    public boolean isPrimary() {
        return primary;
    }

    public void setPrimary(boolean primary) {
        this.primary = primary;
    }

    public Object getDefaultValue() {
        return defaultValue;
    }

    public void setDefaultValue(Object defaultValue) {
        this.defaultValue = defaultValue;
    }
}
