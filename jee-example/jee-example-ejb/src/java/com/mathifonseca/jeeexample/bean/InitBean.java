package com.mathifonseca.jeeexample.bean;

import com.mathifonseca.jeeexample.dto.AuthorDto;
import com.mathifonseca.jeeexample.dto.BookDto;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.Singleton;
import javax.ejb.LocalBean;
import javax.ejb.Startup;
import org.apache.log4j.Logger;

@Startup
@Singleton
@LocalBean
public class InitBean {
    
    static Logger log = Logger.getLogger(InitBean.class.getName());
    
    @EJB
    private AuthorBean authorBean;
    
    @PostConstruct
    public void init() {
        
        log.info("Initializing data...");
         
        if (authorBean.getAuthors(false).isEmpty()) {
            AuthorDto authorDto = new AuthorDto();
            authorDto.setName("José Mauro de Vasconcelos");
            AuthorDto zeze = authorBean.createAuthor(authorDto);
            BookDto bookDto = new BookDto();
            bookDto.setName("Meu Pé de Laranja Lima");
            bookDto.setPublicationDate("1968-01-01");
            authorBean.addBook(zeze, bookDto);
            
            AuthorDto authorDto2 = new AuthorDto();
            authorDto2.setName("Horacio Quiroga");
            AuthorDto quiroga = authorBean.createAuthor(authorDto2);
            BookDto bookDto2 = new BookDto();
            bookDto2.setName("Cuentos de amor, de locura y de muerte");
            bookDto2.setPublicationDate("1917-01-01");
            authorBean.addBook(quiroga, bookDto2); 
        }
        
    }

}
