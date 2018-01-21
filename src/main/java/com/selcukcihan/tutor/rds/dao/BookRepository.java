package com.selcukcihan.tutor.rds.dao;

import com.selcukcihan.tutor.rds.configuration.database.ConnectionProperties;
import com.selcukcihan.tutor.rds.exception.RdsException;
import com.selcukcihan.tutor.rds.model.Book;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;

@Repository
public class BookRepository {
    private static final Logger LOGGER = Logger.getLogger(BookRepository.class.getName());
    private static final String CREATE_TABLE_QUERY = "create table if not exists books (" +
            "id varchar(20) NOT NULL," +
            "title varchar(40) NOT NULL," +
            "author varchar(40) NOT NULL)";
    private static final String POPULATE_TABLE_QUERY = "insert into books (id, title, author) values" +
            "('1', 'Effective Java', 'Joshua Bloch')," +
            "('2', 'Dive into Python', 'Mark Pilgrim')," +
            "('3', 'The C Programming Language', 'Brian Kernighan and Dennis Ritchie')," +
            "('4', 'GUI Bloopers', 'Jeff Johnson')";
    private static final String SELECT_ALL_QUERY = "select id, title, author from books";
    private static final String SELECT_ONE_QUERY = "select id, title, author from books where id=?";

    private final BookTranslator bookTranslator;
    private final ConnectionProperties connectionProperties;

    @Autowired
    public BookRepository(BookTranslator bookTranslator, ConnectionProperties connectionProperties) {
        this.bookTranslator = bookTranslator;
        this.connectionProperties = connectionProperties;
    }

    private Connection getConnection() throws RdsException {
        try {
            return DriverManager.getConnection(connectionProperties.getUrl(),
                    connectionProperties.getUser(), connectionProperties.getPassword());
        } catch (SQLException e) {
            LOGGER.severe(String.format("Unable to connect parameters: %s", connectionProperties));
            throw new RdsException(e.toString());
        }
    }

    public int createTable() throws RdsException {
        return executeUpdate(CREATE_TABLE_QUERY);
    }

    public void populateTable() throws RdsException {
        executeUpdate(POPULATE_TABLE_QUERY);
    }

    private int executeUpdate(String query) throws RdsException {
        Connection connection = getConnection();
        Statement statement = null;
        try {
            statement = connection.createStatement();
            return statement.executeUpdate(query);
        } catch (SQLException e) {
            LOGGER.severe(String.format("Unable to execute statement: %s", query));
            throw new RdsException(e.toString());
        } finally {
            try { statement.close(); } catch (Exception e) { /* Intentionally ignored */ }
            try { connection.close(); } catch (Exception e) { /* Intentionally ignored */ }
        }
    }

    public List<Book> getBooks() throws RdsException {
        Connection connection = getConnection();
        Statement statement = null;
        try {
            statement = connection.createStatement();
            return bookTranslator.translateAll(statement.executeQuery(SELECT_ALL_QUERY));
         } catch (SQLException e) {
            throw new RdsException(e.toString());
        } finally {
            try { statement.close(); } catch (Exception e) { /* Intentionally ignored */ }
            try { connection.close(); } catch (Exception e) { /* Intentionally ignored */ }
        }
    }

    public Optional<Book> getBook(String id) throws RdsException {
        Connection connection = getConnection();
        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = connection.prepareStatement(SELECT_ONE_QUERY);
            preparedStatement.setString(1, id);
            return bookTranslator.translateOne(preparedStatement.executeQuery());
         } catch (SQLException e) {
            LOGGER.severe(String.format("Unable fetch book with id=", id));
            throw new RdsException(e.toString());
        } finally {
            try { preparedStatement.close(); } catch (Exception e) { /* Intentionally ignored */ }
            try { connection.close(); } catch (Exception e) { /* Intentionally ignored */ }
        }
    }
}
