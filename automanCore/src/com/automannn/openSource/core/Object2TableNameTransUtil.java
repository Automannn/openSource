package com.automannn.openSource.core;

/**
 * @author automannn@163.com
 * @time 2018/6/19 22:31
 */
public abstract class Object2TableNameTransUtil {

    public static String object2tablename(String var1) {
        if (var1 == null || var1.equals("")) return var1;
        StringBuilder sb = new StringBuilder();
        try {
            String start = var1.substring(0, 1);
            sb.append(start);
            var1 = var1.substring(1);
            byte[] b = var1.getBytes();
            int i = 0;
            int len = b.length;
            while (len > 0) {
                char tbuff = (char) b[i++];
                if (tbuff >= 65 && tbuff <= 90) {
                    sb.append("_");
                    sb.append((char)(tbuff+32));
                } else {

                    sb.append(tbuff);
                }
                len--;
            }
        } catch (Exception e) {
            return var1;
        }
        return sb.toString();

    }
}
