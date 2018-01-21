package com.selcukcihan.tutor.rds.controller;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.*;

import com.selcukcihan.tutor.rds.business.BookOperator;
import com.selcukcihan.tutor.rds.exception.BookNotFoundException;
import com.selcukcihan.tutor.rds.exception.RdsException;
import com.selcukcihan.tutor.rds.model.Book;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Resource;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.springframework.web.bind.annotation.RequestMethod.GET;

@RestController
public class BooksController {
    private final BookOperator bookOperator;

    @Autowired
    public BooksController(BookOperator bookOperator) {
        this.bookOperator = bookOperator;
    }

    @RequestMapping(path="/books/{id}", method=GET, produces=MediaType.APPLICATION_JSON_VALUE)
    public Resource<Book> getBook(@PathVariable("id") String id) throws RdsException {
        Optional<Book> book = bookOperator.getBook(id);
        if (book.isPresent()) {
            Resource<Book> resource = new Resource(book.get());
            resource.add(linkTo(methodOn(BooksController.class).getBook(book.get().getBookId())).withSelfRel());
            resource.add(linkTo(methodOn(BooksController.class).getBooks()).withRel("books"));
            return resource;
        } else {
            throw new BookNotFoundException(String.format("Unable to find book with id=%s", id));
        }
    }

    @RequestMapping(path="/books", method=GET, produces=MediaType.APPLICATION_JSON_VALUE)
    public Collection<Resource<Book>> getBooks() throws RdsException {
        return bookOperator.getBooks().stream()
                .map(this::getBookResource)
                .collect(Collectors.toList());
    }

    private Resource<Book> getBookResource(Book book) {
        Resource<Book> resource = new Resource(book);
        try {
            resource.add(linkTo(methodOn(BooksController.class).getBook(book.getBookId())).withSelfRel());
        } catch (RdsException e) {
        }
        return resource;
    }
}
