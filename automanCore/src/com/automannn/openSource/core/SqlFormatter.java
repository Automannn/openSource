package com.automannn.openSource.core;

/**
 * @author automannn@163.com
 * @time 2018/6/19 22:17
 */
public interface SqlFormatter {
    String addSqlFormat(ObjectProperty ob);

    String updateSqlFormat(ObjectProperty ob);

    String querySqlFormat(ObjectProperty ob);

    void setTableName(String tablename);

    String getTableName();
}
