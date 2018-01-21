package com.selcukcihan.tutor.rds.business;

import com.selcukcihan.tutor.rds.dao.BookRepository;
import com.selcukcihan.tutor.rds.exception.RdsException;
import com.selcukcihan.tutor.rds.model.Book;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.Optional;

@Service
public class BookOperator {
    private final BookRepository bookRepository;

    @Autowired
    public BookOperator(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    public Optional<Book> getBook(String id) throws RdsException {
        return bookRepository.getBook(id);
    }

    public List<Book> getBooks() throws RdsException {
        return bookRepository.getBooks();
    }

    @PostConstruct
    public void fillData() throws RdsException {
        bookRepository.createTable();
        if (bookRepository.getBooks().size() == 0) {
            bookRepository.populateTable();
        }
    }
}
