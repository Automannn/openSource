package com.automannn.openSource.core;

import java.util.Iterator;
import java.util.Map;

/**
 * @author automannn@163.com
 * @time 2018/6/19 22:22
 */
public class SimpleSqlFormatter implements SqlFormatter {
    ADD addClazz = new ADD();
    UPDATE updateClazz = new UPDATE();
    QUERY queryClazz = new QUERY();
    public String tablename;

    public SimpleSqlFormatter(String tablename) {
        addClazz.TABLE_NAME = Object2TableNameTransUtil.object2tablename(tablename);
       this.tablename=updateClazz.TABLE_NAME = queryClazz.TABLE_NAME = addClazz.TABLE_NAME;
    }

    @Override
    public String addSqlFormat(ObjectProperty ob) {
        if (ob.getObjectProperty().isEmpty()) throw new IllegalArgumentException("这里默认不能所有属性都插入空值");
        addClazz.TABLE_BODY.append("(");
        Map map = ob.getObjectProperty();
        Iterator iterator = map.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry entry = (Map.Entry) iterator.next();
            String key = (String) entry.getKey();
            addClazz.TABLE_BODY.append(Object2TableNameTransUtil.object2tablename(key));
            if (iterator.hasNext()) addClazz.TABLE_BODY.append(",");
        }
        addClazz.TABLE_BODY.append(")");
        int templen = ob.objectPropertySize();

        addClazz.ADD_VALUES_BODY.append("(");
        for (int i = 0; i < templen; i++) {
            addClazz.ADD_VALUES_BODY.append("?");
            if (templen - i > 1) addClazz.ADD_VALUES_BODY.append(",");

        }
        addClazz.ADD_VALUES_BODY.append(")");
        return addClazz.ADD_SQL_STARTING + addClazz.TABLE_NAME +
                addClazz.TABLE_BODY + addClazz.ADD_VALUES_STARTING + addClazz.ADD_VALUES_BODY;
    }

    @Override
    public String updateSqlFormat(ObjectProperty ob) {
        if (ob.getObjectProperty().isEmpty()) throw new IllegalArgumentException("这里默认不能所有属性都编辑为空值");
        Map map = ob.getObjectProperty();
        Iterator iterator = map.entrySet().iterator();
        if (map.get("id") == null) throw new IllegalArgumentException("默认只允许通过id修改对象");
        updateClazz.UPDATE_BODY.append(" set ");
        while (iterator.hasNext()) {
            Map.Entry entry = (Map.Entry) iterator.next();
            updateClazz.UPDATE_BODY.append( Object2TableNameTransUtil.object2tablename((String) entry.getKey())+"=?");
            if (iterator.hasNext()) updateClazz.UPDATE_BODY.append(",");
        }
        String s= updateClazz.UPDATE_BODY.substring(0,updateClazz.UPDATE_BODY.lastIndexOf(","));

        updateClazz.UPDATE_CONDITION_BODY.append(" id=?");

        return updateClazz.UPDATE_SQL_STARTING + updateClazz.TABLE_NAME +
                s + updateClazz.UPDATE_CONDITION_STARTING +
                updateClazz.UPDATE_CONDITION_BODY;
    }

    @Override
    public String querySqlFormat(ObjectProperty ob) {
        //todo: 留待扩展
        queryClazz.QUERY_BODY.append("*");
        if (ob.getObjectProperty().isEmpty()) throw new IllegalArgumentException("这里默认不能所有属性都为空值去查询");
        Map map = ob.getObjectProperty();
        Iterator iterator = map.entrySet().iterator();

        //todo:留待扩展
//        if (map.get("id")!=null) {
//            queryClazz.QUERY_CONDITION_BODY.append(" id=? ");
//        }else {
            while (iterator.hasNext()){
                Map.Entry entry = (Map.Entry) iterator.next();
                queryClazz.QUERY_CONDITION_BODY.append(Object2TableNameTransUtil.object2tablename((String) entry.getKey())+"=?");
                if (iterator.hasNext()) queryClazz.QUERY_CONDITION_BODY.append(" and ");
//            }
        }

        return queryClazz.QUERY_SQL_STARTING + queryClazz.QUERY_BODY+" from "+queryClazz.TABLE_NAME+
                queryClazz.QUERY_CONDITION_STARTING+queryClazz.QUERY_CONDITION_BODY;
    }

    @Override
    public void setTableName(String tablename) {
        this.tablename = tablename;
    }

    @Override
    public String getTableName() {
        return this.tablename;
    }

    class ADD {
        private final String ADD_SQL_STARTING = "insert into ";

        private String TABLE_NAME;

        private StringBuilder TABLE_BODY = new StringBuilder();

        private final String ADD_VALUES_STARTING = " values";

        private StringBuilder ADD_VALUES_BODY = new StringBuilder();
    }

    class UPDATE {
        private final String UPDATE_SQL_STARTING = " update ";

        private String TABLE_NAME;

        private StringBuilder UPDATE_BODY = new StringBuilder();

        private final String UPDATE_CONDITION_STARTING = " where ";

        private StringBuilder UPDATE_CONDITION_BODY = new StringBuilder();
    }

    class QUERY {
        private final String QUERY_SQL_STARTING = " select ";

        private StringBuilder QUERY_BODY = new StringBuilder();

        private String TABLE_NAME;


        private final String QUERY_CONDITION_STARTING = " where ";

        private StringBuilder QUERY_CONDITION_BODY = new StringBuilder();

    }
}
