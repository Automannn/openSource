package com.automannn.openSource.json;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

/**
 * @author automannn@163.com
 * @time 2018/6/22 12:59
 */
public class JsonNode {

    private String name;

    private List<JsonNode> innerNodes;


    private JsonNodeType jsonNodeType;

    private String value;

    public JsonNode(String name, List<JsonNode> innerNodes, JsonNodeType jsonNodeType) {
        this.name = name;
        this.innerNodes = innerNodes;
        this.jsonNodeType = jsonNodeType;
    }

    public JsonNode(String name, List<JsonNode> innerNodes, JsonNodeType jsonNodeType, String value) {
        this.name = name;
        this.innerNodes = innerNodes;
        this.jsonNodeType = jsonNodeType;
        this.value = value;
    }

    public JsonNode(String name, JsonNodeType jsonNodeType, String value) {
        this.name = name;
        this.jsonNodeType = jsonNodeType;
        this.value = value;
    }

    public JsonNodeType getJsonNodeType() {
        return jsonNodeType;
    }

    public void setJsonNodeType(JsonNodeType jsonNodeType) {
        this.jsonNodeType = jsonNodeType;
    }

    public JsonNode() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<JsonNode> getInnerNodes() {
        return innerNodes;
    }

    public void setInnerNodes(List<JsonNode> innerNodes) {
        this.innerNodes = innerNodes;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
        if (this.innerNodes == null) innerNodes = new LinkedList<>();
        this.innerNodes.add(new JsonNode(name, jsonNodeType, value));
        refresh1();
    }

    public JsonNode addChildNode(JsonNodeType jsonNodeTypeval) {
        if (this.innerNodes == null) innerNodes = new LinkedList<>();
        JsonNode jsonNode = new JsonNode(name, jsonNodeTypeval, value);
        innerNodes.add(jsonNode);
        refresh();
        return jsonNode;
    }


    public String toJsonString() {
        StringBuilder stringBuilder = new StringBuilder();

        if ((innerNodes == null && value == null) || (innerNodes != null && value != null))
            throw new IllegalArgumentException("解析错误！");
        if (value != null) {
            if (getJsonNodeType() == null || name == null) {
                throw new IllegalArgumentException("解析异常，这时一个bug");
            }
            if (getJsonNodeType().equals(JsonNodeType.STRING) ||
                    getJsonNodeType().equals(JsonNodeType.NUMBER)) {
                stringBuilder.append("\"" + name + "\":");
                stringBuilder.append("\"" + value + "\"");
            } else if (getJsonNodeType().equals(JsonNodeType.OBJECT)) {
                stringBuilder.append("\"" + name + "\":");
                stringBuilder.append("{");
                stringBuilder.append(toJsonString());
                stringBuilder.append("}");
            } else {
                throw new IllegalArgumentException("解析错误！");
            }
        } else {
            Iterator iterator = innerNodes.iterator();
            while (iterator.hasNext()) {
                JsonNode node = (JsonNode) iterator.next();
                if (node.getJsonNodeType() == null) {
                    if (node.name == null) {
                        stringBuilder.append("{");
                        stringBuilder.append(node.toJsonString());
                        stringBuilder.append("}");
                    } else {
                        stringBuilder.append("{");
                        stringBuilder.append("\"" + node.name + "\":");
                        stringBuilder.append("\"" + node.value + "\"");
                        stringBuilder.append("}");
                    }
                } else {

                    if (node.getJsonNodeType().equals(JsonNodeType.STRING) ||
                            node.getJsonNodeType().equals(JsonNodeType.NUMBER)) {
                        stringBuilder.append("\"" + node.name + "\":");
                        stringBuilder.append("\"" + node.value + "\"");
                    } else if (node.getJsonNodeType().equals(JsonNodeType.ARRAY)) {
                        stringBuilder.append("\"" + node.name + "\":");
                        stringBuilder.append("[");
                        stringBuilder.append(node.toJsonString());
                        stringBuilder.append("]");
                    } else if (node.getJsonNodeType().equals(JsonNodeType.BOOLEAN)) {
                        stringBuilder.append("\"" + node.name + "\":");
                        stringBuilder.append("" + node.value);
                    } else if (node.getJsonNodeType().equals(JsonNodeType.OBJECT)) {
                        stringBuilder.append("\"" + node.name + "\":");
                        stringBuilder.append(node.toJsonString());
                    }
                }
                if (iterator.hasNext()) stringBuilder.append(",");
            }
        }
        return stringBuilder.toString();
    }


    private void refresh() {
        this.value = null;
    }

    private void refresh1() {
        this.value = null;
        this.name = null;
        setJsonNodeType(null);
    }

}
