package com.automannn.openSource.jdbc;

import com.automannn.javawebWork.reflection.*;

import java.lang.reflect.Field;
import java.lang.reflect.Type;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

/**
 * @author automannn@163.com
 * @time 2018/6/20 0:43
 */
public abstract class AbstractDBOperationBase<T> extends DataBaseHelper<T> implements DB<T>, DataBaseOperaion<T> {
    protected String SQL_ADD = null;

    protected String SQL_UPDATE = null;

    protected String SQL_QUERY = null;

    protected DefaultProperty defaultProperty;

    protected SqlFormatter sqlFormatter = null;


    @Override
    public void setEntity(T entity) {
        defaultProperty = new DefaultProperty(entity);
        String name = entity.getClass().getName();
        name = name.substring(name.lastIndexOf(".") + 1);
        sqlFormatter = new SimpleSqlFormatter(name);
    }

    public AbstractDBOperationBase(T entity) {
        defaultProperty = new DefaultProperty(entity);
        String name = entity.getClass().getName();
        name = name.substring(name.lastIndexOf(".") + 1);
        sqlFormatter = new SimpleSqlFormatter(name);
    }

    protected int generatedADD(T entity) {
        int effects = -1;
        setEntity(entity);
        this.SQL_ADD = sqlFormatter.addSqlFormat(this.defaultProperty);
        effects = generatedWork(this.SQL_ADD);
        if (effects == 1) {
            int id = 0;

            try {
                resultSet = preparedStatement.getGeneratedKeys();
                while (resultSet.next()) {
                    id = resultSet.getInt(1);
                }
                Field f = entity.getClass().getDeclaredField("id");
                f.setAccessible(true);
                f.set(entity, id);
            } catch (NoSuchFieldException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return effects;
    }

    protected int generatedUPDATE(T entity) {
        setEntity(entity);
        this.SQL_UPDATE = sqlFormatter.updateSqlFormat(this.defaultProperty);


        return generatedWork(this.SQL_UPDATE);
    }

    protected List generatedQUERY(T entity) {
        List<T> tList = null;
        setEntity(entity);
        this.SQL_QUERY = sqlFormatter.querySqlFormat(this.defaultProperty);
        //有返回值，但是不是我们需要的，因此另作处理
        generatedWork(this.SQL_QUERY);
        try {
            resultSet = preparedStatement.getResultSet();
            tList = generatedEntitys(resultSet, entity);

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return tList;
    }

    private List<T> generatedEntitys(ResultSet resultSet, T entity) {
        List tlist = new ArrayList();
        if (resultSet == null) throw new IllegalArgumentException("未取到结果");
        try {
            while (resultSet.next()) {
                T t = genetedEntity(resultSet, entity);
                tlist.add(t);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return tlist;
    }

    private T genetedEntity(ResultSet resultSet, T entity) {
        ClassProperty classProperty = new DefaultProperty(entity);
        Field[] fs = classProperty.getClassProperty();
        try {

            //todo:留待扩展
            for (Field f : fs) {
                String name = f.getName();
                name = name.substring(name.lastIndexOf(".") + 1);
                name = Object2TableNameTransUtil.object2tablename(name);
                Type clazztype = f.getType();
                String clazzname = clazztype.getTypeName();
                Object resultvalue = null;
                if (clazzname.equals(String.class.getName())) {
                    resultvalue = resultSet.getString(name);
                } else if (clazzname.equals(Integer.class.getName())) {
                    resultvalue = resultSet.getInt(name);
                } else if (clazzname.equals(Date.class.getName())) {
                    resultvalue = resultSet.getDate(name);
                } else {
                    resultvalue = resultSet.getString(name);
                }
                f.setAccessible(true);
                f.set(entity, resultvalue);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return entity;
    }

    ;

    abstract void generated();

    private int generatedWork(String sql) {

        int effectedNums = -1;
        try {
            preparedStatement = connection.prepareStatement(sql, 1);
            Map map = this.defaultProperty.getObjectProperty();
            Iterator iterator = map.entrySet().iterator();
            int index = 1;
            while (iterator.hasNext()) {
                //todo: 留待扩展
                Map.Entry entry = (Map.Entry) iterator.next();
                String name = (String) entry.getKey();
                Object value = entry.getValue();
                int len = this.defaultProperty.getClassProperty().length;
                Class clazz = null;
                Field[] fis = this.defaultProperty.getClassProperty();
                for (int i = 0; i < len; i++) {
                    if (fis[i].getName().equals(name)) {
                        clazz = fis[i].getType();
                    }
                }
                if (clazz == null) throw new IllegalArgumentException("类对象与对象类不匹配，请检查后重试");
                if (clazz.equals(String.class)) {
                    preparedStatement.setString(index++, (String) value);
                } else if (clazz.equals(Integer.class)) {
                    preparedStatement.setInt(index++, (Integer) value);
                } else if (clazz.equals(Date.class)) {
                    preparedStatement.setDate(index++, (java.sql.Date) value);
                } else {
                    preparedStatement.setString(index++, (String) value);
                }
            }
            preparedStatement.execute();
            effectedNums = preparedStatement.getUpdateCount();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return effectedNums;
    }

}
