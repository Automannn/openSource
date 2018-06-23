package com.automannn.openSource.json;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author automannn@163.com
 * @time 2018/5/2 20:21
 */
public class JsonStringUtil {

    public static String getJsonString(String source,String target){
        String regix = ".+"+target+"\":([{]{1}[^}]*[}]{1}?)";
        Pattern p = Pattern.compile(regix);
        Matcher m = p.matcher(source);
        if (m.find()){
            return m.group(1);
        }else {
            String regix1 = ".+"+target+"\":\"(.*?)\"";
            Pattern p1 = Pattern.compile(regix1);
            Matcher m1 = p1.matcher(source);
            if (m1.find()){
                return m1.group(1);
            }else {
                String regix2 = ".+"+target+"\":(.*?)}";
                Pattern p2 = Pattern.compile(regix2);
                Matcher m2 = p2.matcher(source);
                if (m2.find()){
                    return m2.group(1);
                }
                return null;
            }
        }
    }

//    public static void main(String[] args) {
//        String test = "{\"user\":{\"test222\":\"test11\"},\"user1\":{\"test121\":\"test1\",\"success\":false}}";
//        System.out.println(getJsonString(test,"success"));
//    }
}
