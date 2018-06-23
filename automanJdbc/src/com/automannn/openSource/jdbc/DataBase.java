package com.automannn.openSource.jdbc;

import java.sql.*;
import java.util.List;

public interface DataBase<T> {
    //用来连接数据库
    public default Connection createConnection() {
        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        Connection connection = null;

        Statement statement = null;

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

    //用来查询的
    public List<T> queryFor(T t);
    //用来修改的
    public Boolean updateFor(T t);
    //用来删除的
    public Boolean deleteFor(int id);
    //用来增加的
    public Boolean addFor(T t);


}

