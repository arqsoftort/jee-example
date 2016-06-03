package com.mathifonseca.jeeexample.bean;

import com.mathifonseca.jeeexample.domain.Author;
import com.mathifonseca.jeeexample.domain.Book;
import com.mathifonseca.jeeexample.dto.BookDto;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.LocalBean;
import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.JMSException;
import javax.jms.MessageProducer;
import javax.jms.ObjectMessage;
import javax.jms.Queue;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Stateless
@LocalBean
public class BookBean {

    @PersistenceContext
    private EntityManager em;
    
    @EJB
    private DateBean dateBean;
    
    @Resource(lookup = "jms/ConnectionFactory")
    private ConnectionFactory connectionFactory;
    
    @Resource(lookup = "jms/Queue")
    private Queue queue;
    
    public List<BookDto> getBooksByAuthorId(Long authorId) {
        return toDto(em.createQuery("select b from Book b where b.author.id = :authorId")
                .setParameter("authorId", authorId)
                .getResultList());
    }
    
    public BookDto findBookById(Long id) {
        return toDto(em.find(Book.class, id));
    }
    
    public BookDto createBook(Author author, BookDto dto) {
        Book book = toEntity(dto);
        book.setAuthor(author);
        em.persist(book);
        BookDto result = toDto(book);
        sendNotification(result);
        return result;
    }
    
    private void sendNotification(BookDto book) {
        try {
            Connection connection = connectionFactory.createConnection();
            Session session = connection.createSession();
            ObjectMessage msg = session.createObjectMessage(book);
            MessageProducer producer = session.createProducer(queue);
            producer.send(msg);
            session.close();
            connection.close();
        } catch (JMSException ex) {
            Logger.getLogger(BookBean.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private Book toEntity(BookDto dto) {
        Book book = new Book();
        book.setName(dto.getName());
        book.setPublicationDate(dateBean.toDate(dto.getPublicationDate()));
        return book;
    }
    
    private BookDto toDto(Book entity) {
        BookDto dto = new BookDto();
        dto.setId(entity.getId());
        dto.setName(entity.getName());
        dto.setPublicationDate(dateBean.toString(entity.getPublicationDate()));
        return dto;
    }
    
    private List<BookDto> toDto(List<Book> entities) {
        List<BookDto> dtos = new ArrayList<>();
        for (Book book : entities) {
            dtos.add(toDto(book));
        }
        return dtos;
    }

}
