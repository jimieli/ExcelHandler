package com.jimie.handler.excel.check;

/**
 * @author litingjie
 * 2020-6-26
 */
public class NullChecker {

    /**
     * 检查元素是否为null
     * @param object
     */
    public static void isNull(Object object){
        if(object == null){
            throw new NullPointerException();
        }
    }

}
