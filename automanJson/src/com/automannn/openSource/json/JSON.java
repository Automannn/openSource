package com.automannn.openSource.json;

/**
 * @author automannn@163.com
 * @time 2018/6/22 13:06
 */
public interface JSON {
    int virifyType(JsonNode jsonNode);

    void JavaToJson(Object bean);

    void JsonToJava(JSON json, Object bean);


    String getJson();

    Object getJava();
}
