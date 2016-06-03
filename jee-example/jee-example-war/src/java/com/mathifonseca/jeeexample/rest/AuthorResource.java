package com.mathifonseca.jeeexample.rest;

import com.google.gson.Gson;
import com.mathifonseca.jeeexample.bean.AuthorBean;
import com.mathifonseca.jeeexample.bean.BookBean;
import com.mathifonseca.jeeexample.dto.AuthorDto;
import com.mathifonseca.jeeexample.dto.BookDto;
import java.util.List;
import javax.ejb.EJB;
import javax.ws.rs.Consumes;
import javax.ws.rs.Produces;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.POST;
import javax.ws.rs.PathParam;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("authors")
public class AuthorResource {

    @EJB
    private AuthorBean authorBean;
    
    @EJB
    private BookBean bookBean;
    
    private final Gson gson = new Gson();

    public AuthorResource() {
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getJson(@QueryParam("withBooks") boolean withBooks) {
        List<AuthorDto> list = authorBean.getAuthors(withBooks);
        return Response.ok().entity(gson.toJson(list)).build();
    }
    
    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAuthor(@PathParam("id") Long id) {
        AuthorDto dto = authorBean.findAuthorById(id);
        return Response.ok().entity(gson.toJson(dto)).build();
    }
    
    @GET
    @Path("/{id}/books")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getBooks(@PathParam("id") Long id) {
        List<BookDto> list = bookBean.getBooksByAuthorId(id);
        return Response.ok().entity(gson.toJson(list)).build();
    }
    
    @GET
    @Path("/{authorId}/books/{bookId}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getBooks(@PathParam("authorId") Long authorId, @PathParam("bookId") Long bookId) {
        List<BookDto> list = bookBean.getBooksByAuthorId(bookId);
        return Response.ok().entity(gson.toJson(list)).build();
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response postJson(String json) {
        AuthorDto author = authorBean.createAuthor(gson.fromJson(json, AuthorDto.class));
        return Response.status(Response.Status.CREATED).entity(author).build();
    }
    
    @POST
    @Path("/{id}/books")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response postBook(@PathParam("id") Long id, String json) {
        AuthorDto authorDto = authorBean.findAuthorById(id);
        BookDto bookDto = authorBean.addBook(authorDto, gson.fromJson(json, BookDto.class));
        return Response.status(Response.Status.CREATED).entity(bookDto).build();
    }
    
}
