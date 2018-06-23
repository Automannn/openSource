package com.automannn.openSource.core;

import java.lang.reflect.Field;

/**
 * @author automannn@163.com
 * @time 2018/6/12 16:47
 */
public interface ClassProperty<T> {

    public Field[] getClassProperty();

    public void setClassProperty(T t);



    public int classPropertySize();


}
