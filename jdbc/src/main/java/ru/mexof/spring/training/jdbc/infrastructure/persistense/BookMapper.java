package ru.mexof.spring.training.jdbc.infrastructure.persistense;

import org.springframework.jdbc.core.RowMapper;
import ru.mexof.spring.training.jdbc.services.author.Author;
import ru.mexof.spring.training.jdbc.services.book.Book;
import ru.mexof.spring.training.jdbc.services.genre.Genre;

import java.sql.ResultSet;
import java.sql.SQLException;

public class BookMapper implements RowMapper<Book> {
    @Override
    public Book mapRow(ResultSet rs, int rowNum) throws SQLException {
        long id = rs.getLong("id");
        String name = rs.getNString("name");
        long authorId = rs.getLong("author_id");
        String authorName = rs.getNString("author");
        long genreId = rs.getLong("genre_id");
        String genreType = rs.getNString("genre");

        return new Book(
                id,
                name,
                new Author(authorId, authorName),
                new Genre(genreId, genreType)
        );
    }
}
