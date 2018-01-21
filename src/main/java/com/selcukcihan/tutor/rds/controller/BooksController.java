package com.selcukcihan.tutor.rds.controller;

import com.selcukcihan.tutor.rds.model.Book;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.web.bind.annotation.RequestMethod.GET;

@RestController
public class BooksController {
    @RequestMapping(path="/books/{id}", method=GET)
    public Book greeting(@PathVariable("id") String id) {
        return new Book(id, "LOTR", "JKRowling");
    }
}
