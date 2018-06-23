package com.automannn.openSource.core;

import java.lang.reflect.Field;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @author automannn@163.com
 * @time 2018/6/12 16:57
 */
public class DefaultProperty implements ClassProperty<Class>,ObjectProperty<Object>,Processor {
    protected  Field[] classProperties;


    protected final Map objectPropertyMaps = new LinkedHashMap();

    public DefaultProperty(Class clazz, Object instance) {
        setClassProperty(clazz);
        setObjectProperty(instance);
        process();
    }

    public DefaultProperty(Object instance){
        setClassProperty(instance.getClass());
        setObjectProperty(instance);
        process();
    }

    @Override
    public Field[] getClassProperty() {
        return this.classProperties;
    }

    @Override
    public void setClassProperty(Class aClass) {
        this.classProperties = aClass.getDeclaredFields();
    }

    @Override
    public int classPropertySize() {
        return classProperties.length;
    }


    @Override
    public void setObjectProperty(Object instance) {
        if (this.classProperties.length == 0) throw new IllegalArgumentException("未正确初始化参数！");
        try {
            for (Field f:classProperties){
                f.setAccessible(true);
                if (f.get(instance)!=null && !"".equals(f.get(instance))) this.objectPropertyMaps.put(f.getName(),f.get(instance));
            }
        } catch (IllegalAccessException e) {
            e.printStackTrace();
            System.out.println("类与实例对象不符合，这里不做处理");
        }
    }

    @Override
    public Map getObjectProperty() {
        return  this.objectPropertyMaps;
    }

    @Override
    public int objectPropertySize() {
        return objectPropertyMaps.size();
    }

    @Override
    public void process() {
        //将id放在map的最后面
        if (this.objectPropertyMaps.get("id")==null) return;
        String id = String.valueOf(this.objectPropertyMaps.get("id"));
        this.objectPropertyMaps.remove("id");
        this.objectPropertyMaps.put("id",id);

    }
}
