package com.selcukcihan.tutor.rds.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.hateoas.ResourceSupport;

public class Book extends ResourceSupport {
    private final String id;
    private final String title;
    private final String author;

    @JsonCreator
    public Book(@JsonProperty("id") String id, @JsonProperty("title") String title, @JsonProperty("author") String author) {
        super();
        this.id = id;
        this.title = title;
        this.author = author;
    }

    @JsonGetter("id")
    public String getBookId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }
}
