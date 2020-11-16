package ru.mexof.spring.training.jdbc.services.book;

import java.util.List;

public interface BookRepository {
    List<Book> findAll();
    List<Book> findByAuthorId(long authorId);
    List<Book> findByGenreId(long genreId);
    Book findById(long bookId);
    Long add(Book book);
    boolean delete(long bookId);
}
