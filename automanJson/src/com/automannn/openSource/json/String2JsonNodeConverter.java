package com.automannn.openSource.json;

/**
 * @author automannn@163.com
 * @time 2018/6/22 14:04
 */
public interface String2JsonNodeConverter {

    String nextNodeName();

    String nextNodeValue();

    JsonNode next();

    JsonNode getNode();

    JsonNodeType verifyType();

    void parse();
}
