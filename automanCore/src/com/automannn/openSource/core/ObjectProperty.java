package com.automannn.openSource.core;

import java.util.Map;

/**
 * @author automannn@163.com
 * @time 2018/6/12 16:47
 */
public interface ObjectProperty<T>{

    public void setObjectProperty(T instance);

    public Map getObjectProperty();

    public int objectPropertySize();
}
