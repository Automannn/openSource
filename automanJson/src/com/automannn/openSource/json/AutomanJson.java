package com.automannn.openSource.json;

import com.automannn.javawebWork.reflection.DefaultProperty;

import java.lang.reflect.Field;
import java.util.List;

/**
 * @author automannn@163.com
 * @time 2018/6/22 13:38
 */
public class AutomanJson implements JsonHelper, JSON {
    private static List<JsonNode> jsonNodeArray;

    private static Object currentBean;


    public static List<JsonNode> getJsonNodeArray() {
        return jsonNodeArray;
    }

    public static void setJsonNodeArray(List<JsonNode> jsonNodeArray) {
        AutomanJson.jsonNodeArray = jsonNodeArray;
    }

    public static Object getCurrentBean() {
        return currentBean;
    }

    public static void setCurrentBean(Object currentBean) {
        AutomanJson.currentBean = currentBean;
    }

    public boolean isRoot() {
        return currentBean == null;
    }

    @Override
    public boolean isArray() {
        return jsonNodeArray.size() != 1;
    }

    @Override
    public int virifyType(JsonNode jsonNode) {
        return 0;
    }

    @Override
    public void JavaToJson(Object bean) {

    }

    @Override
    public void JsonToJava(JSON json, Object o) {



        DefaultProperty defaultProperty = new DefaultProperty(o);
        Field[] fields = defaultProperty.getClassProperty();


        try {
            for (Field f : fields) {
                f.setAccessible(true);
                f.get(o);
            }
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    @Override
    public String getJson() {
        return jsonNodeArray.toString();
    }

    @Override
    public Object getJava() {
        return currentBean;
    }
}
