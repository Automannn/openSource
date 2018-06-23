package com.automannn.openSource.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 * @author automannn@163.com
 * @time 2018/6/12 12:22
 */
public abstract class DataBaseHelper<T> implements DataBaseOperaion<T>{
    protected  final Connection connection;

    protected PreparedStatement preparedStatement =null;
    protected ResultSet resultSet=null;

    public DataBaseHelper() {
        this.connection = createConnection();

    }
}
