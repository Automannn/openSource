package com.automannn.openSource.jdbc;

import com.automannn.javawebWork.entity.Book;

import java.sql.SQLException;
import java.util.List;

/**
 * @author automannn@163.com
 * @time 2018/6/20 2:11
 */
public class BookDatabaseOperation extends AbstractDBOperationBase<Book> {
    public BookDatabaseOperation(Book entity) {
        super(entity);
    }

    @Override
    void generated() {
    }


    @Override
    public int add(Book book) {
       return super.generatedADD(book);
    }

    @Override
    public int update(Book book) {
       return super.generatedUPDATE(book);
    }

    @Override
    public List query(Book book) {
        return super.generatedQUERY(book);
    }

    @Override
    public int delete(int id) {
        int size =-1;
        try {
            preparedStatement = connection.prepareStatement("delete from "+sqlFormatter.getTableName()+" where id=?");
            preparedStatement.setInt(1,id);
            size= preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return size;
    }
}
