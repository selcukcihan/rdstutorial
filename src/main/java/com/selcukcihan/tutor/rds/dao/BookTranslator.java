package com.selcukcihan.tutor.rds.dao;

import com.selcukcihan.tutor.rds.exception.RdsException;
import com.selcukcihan.tutor.rds.model.Book;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

@Component
public class BookTranslator {

    public List<Book> translateAll(ResultSet resultSet) throws RdsException {
        List<Book> books = new LinkedList<>();
        try {
            while (resultSet.next()) {
                books.add(new Book(resultSet.getString("id"),
                        resultSet.getString("title"), resultSet.getString("author")));
            }
        } catch (SQLException ex) {
            throw new RdsException(ex.toString());
        }
        return books;
    }

    public Optional<Book> translateOne(ResultSet resultSet) throws RdsException {
        try {
            if (resultSet.next()) {
                return Optional.of(new Book(resultSet.getString("id"),
                        resultSet.getString("title"), resultSet.getString("author")));
            } else {
                return Optional.empty();
            }
        } catch (SQLException ex) {
            throw new RdsException(ex.toString());
        }
    }
}
