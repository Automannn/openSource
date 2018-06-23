package com.automannn.openSource.jdbc;

import java.sql.*;
import java.util.List;

/**
 * @author automannn@163.com
 * @time 2018/6/20 1:12
 */
public interface DataBaseOperaion<T> {

    //用来连接数据库
    public default Connection createConnection() {
        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        Connection connection = null;

        PreparedStatement preparedStatement = null;

        ResultSet resultset = null;

        try {
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/javaweb?serverTimezone=UTC&characterEncoding=utf-8",
                    "root",
                    "chenkaihai");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return connection;
    }


    int add(T t);

    int update(T t);

    List query(T t);

    int delete(int id);
}
