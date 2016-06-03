package com.mathifonseca.jeeexample.dto;

import java.io.Serializable;

public class BookDto implements Serializable {
    
    private Long id;
    private String name;
    private String publicationDate;

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

    public String getPublicationDate() {
        return publicationDate;
    }

    public void setPublicationDate(String publicationDate) {
        this.publicationDate = publicationDate;
    }
    
}
