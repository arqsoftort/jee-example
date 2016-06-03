package com.mathifonseca.jeeexample.dto;

import java.io.Serializable;
import java.util.List;

public class AuthorDto implements Serializable {
    
    private Long id;
    private String name;
    private List<BookDto> books;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<BookDto> getBooks() {
        return books;
    }

    public void setBooks(List<BookDto> books) {
        this.books = books;
    }
    
}
