package com.automannn.openSource.json;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author automannn@163.com
 * @time 2018/6/22 14:03
 */
public class StringToJsonNode implements String2JsonNodeConverter {
    private final String jsonString;

    private final JsonNode jsonNode = new JsonNode();

    //private final String NODE_FULL_PATTERN="{\".+?\":([{]{1}[^}]*[}]{1}?)}";//取根节点，必须为标准的json格式

    private final String NODE_FULL_PATTERN = "(\"(?<nodeName>[^\"]+?)\"):(?<nodeValue>(?<nodeValueNode>[\\{]{1}.*?[\\}]{1})|(?<nodeValueArray>\\[.*?\\])|(\"(?<nodeValueString>[^\"]*?)\")|(?<nodeValueBool>[^,\"]*?)[,\\}])";//取根节点，必须为标准的json格式


    String NODE_FULL_PATTERN1 = "\\{(((\"(?<nodeName>[^\"]+?)\"):(?<nodeValue>(?<nodeValueNode>[\\{]{1}.*?[\\}]{1})|(?<nodeValueArray>\\[.*?\\])|(\"(?<nodeValueString>[^\"]*?)\")|(?<nodeValueBool>(true|false)))),{0,1})*?\\}";//取根节点，必须为标准的json格式


//    private final String NODE_INNER_PATTERN=NODE_FULL_PATTERN;
//
//    private final String NODE_STRING_NAME=NODE_FULL_PATTERN;  //获取节点中的名称，适合所有的名称,必须按照常规进行操作，否则会出错
//
//    private final String NODE_STRING_VALUE=NODE_FULL_PATTERN;  //取节点中的值,包括 空值 和普通字符串
//
//
//
//    private final String NODE_BOOLEAN_VALUE=NODE_FULL_PATTERN;//取节点中的bool值
//
//    private final String NODE_ARRAY =NODE_FULL_PATTERN;


    public StringToJsonNode(String jsonString) {
        this.jsonString = jsonString;
        parse();
    }

    public String getJsonString() {
        return jsonNode.toJsonString();
    }


    @Override
    public String nextNodeName() {
        return null;
    }

    @Override
    public String nextNodeValue() {
        return null;
    }

    @Override
    public JsonNode next() {
        return null;
    }

    @Override
    public JsonNode getNode() {
        return null;
    }

    @Override
    public JsonNodeType verifyType() {
        return null;
    }

    @Override
    public void parse() {
        if (this.jsonString == null) throw new IllegalArgumentException("未正确初始化");
        if (this.jsonString.startsWith("{")) {
            parseCore1(this.jsonString, this.jsonNode);
        } else if (this.jsonString.startsWith("[")) {
            parseCore2(this.jsonString, this.jsonNode.addChildNode(JsonNodeType.ARRAY));
        } else {
            throw new IllegalArgumentException("json数据错误，请检查:" + this.jsonString);
        }
    }

    private void parseCore(String rootJsonString, JsonNode theNode, boolean isthetype1) {

        String currentNodeName = null;
        Pattern pattern1 = Pattern.compile(this.NODE_FULL_PATTERN);
        Pattern pattern2 = Pattern.compile(this.NODE_FULL_PATTERN1);
        Pattern pattern;
        if (isthetype1) {
            pattern = pattern1;
        } else {
            pattern = pattern2;
        }
        Matcher matcher1 = pattern.matcher(rootJsonString);
        String currentNode = null;
        while (matcher1.find()) {
            currentNode = matcher1.group(0);
            if (isthetype1) {
                Matcher matcher2 = pattern1.matcher(currentNode);
                while (matcher2.find()) {
                    currentNodeName = matcher2.group("nodeName");
                    if (currentNodeName != null) theNode.setName(currentNodeName);

                    String stringValue = matcher2.group("nodeValueString");
                    String boolValue = matcher2.group("nodeValueBool");
                    String valuenode = matcher2.group("nodeValueNode");
                    String valueArray = matcher2.group("nodeValueArray");

                    if (stringValue != null) {
                        theNode.setJsonNodeType(JsonNodeType.STRING);
                        theNode.setValue(stringValue);
                    } else if (boolValue != null) {
                        theNode.setJsonNodeType(JsonNodeType.BOOLEAN);
                        theNode.setValue(boolValue);
                    } else if (valuenode != null) {
                        parseCore2(valuenode, theNode.addChildNode(JsonNodeType.OBJECT));
                    } else {
                        if (valueArray == null) throw new IllegalArgumentException("运行异常，如果出现了，那么这是一个bug");
                        parseCore2(valueArray, theNode.addChildNode(JsonNodeType.ARRAY));
                    }

                }
            } else {
                parseCore1(currentNode,theNode.addChildNode(JsonNodeType.ARRAY));
            }

        }
    }

    private void parseCore2(String valueArray, JsonNode jsonNode) {
        this.parseCore(valueArray, jsonNode, false);
    }

    private void parseCore1(String valuenode, JsonNode jsonNode) {
        this.parseCore(valuenode, jsonNode, true);
    }


}
