package com.mathifonseca.jeeexample.bean;

import com.mathifonseca.jeeexample.domain.Author;
import com.mathifonseca.jeeexample.dto.AuthorDto;
import com.mathifonseca.jeeexample.dto.BookDto;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.LocalBean;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Stateless
@LocalBean
public class AuthorBean {
    
    @PersistenceContext
    private EntityManager em;
    
    @EJB
    private BookBean bookBean;

    public List<AuthorDto> getAuthors(boolean withBooks) {
        return toDto(em.createQuery("select a from Author a").getResultList(), withBooks);
    }
    
    public AuthorDto findAuthorById(Long id) {
        return toDto(em.find(Author.class, id), false);
    }

    public AuthorDto createAuthor(AuthorDto dto) {
        Author author = toEntity(dto);
        em.persist(author);
        return toDto(author, false);
    }
    
    public BookDto addBook(AuthorDto authorDto, BookDto bookDto) {
        Author author = em.find(Author.class, authorDto.getId());
        return bookBean.createBook(author, bookDto);
    }
    
    private List<AuthorDto> toDto(List<Author> entities, boolean includeBooks) {
        List<AuthorDto> dtos = new ArrayList<>();
        for (Author author : entities) {
            dtos.add(toDto(author, includeBooks));
        }
        return dtos;
    }
    
    private AuthorDto toDto(Author entity, boolean includeBooks) {
        AuthorDto dto = new AuthorDto();
        dto.setId(entity.getId());
        dto.setName(entity.getName());
        if (includeBooks) {
            dto.setBooks(bookBean.getBooksByAuthorId(entity.getId()));
        }
        return dto;
    }
    
    private Author toEntity(AuthorDto dto) {
        Author entity = new Author();
        entity.setName(dto.getName());
        return entity;
    }

}
