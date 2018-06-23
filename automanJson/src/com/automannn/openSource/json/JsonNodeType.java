package com.automannn.openSource.json;

/**
 * @author automannn@163.com
 * @time 2018/6/22 13:01
 */
public enum JsonNodeType {
    BOOLEAN(0,"布尔类型"),
    STRING(1,"字符串类型"),
    NUMBER(2,"序数"),
    NULL(3,"空类型"),
    OBJECT(4,"对象"),
    ARRAY(5,"数组");
    private int code;

    private String info;

    JsonNodeType(int code, String info) {
        this.code = code;
        this.info = info;
    }
}
