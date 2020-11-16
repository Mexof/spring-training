package ru.mexof.spring.training.jdbc.infrastructure.persistense;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;
import ru.mexof.spring.training.jdbc.services.book.Book;
import ru.mexof.spring.training.jdbc.services.book.BookRepository;

import java.util.List;

@Repository
public class JdbcBookRepository implements BookRepository {
    private final NamedParameterJdbcOperations namedParameterJdbcOperations;

    @Autowired
    public JdbcBookRepository(NamedParameterJdbcOperations namedParameterJdbcOperations) {
        this.namedParameterJdbcOperations = namedParameterJdbcOperations;
    }

    @Override
    public List<Book> findAll() {
        return namedParameterJdbcOperations.query(
                "select b.id, b.name, b.author_id, b.genre_id, a.name as author, g.type as genre " +
                        "from books b " +
                        "left join authors a on b.author_id=a.id " +
                        "left join genres g on b.genre_id=g.id ",
                new BookMapper()
        );
    }

    @Override
    public List<Book> findByAuthorId(long authorId) {
        SqlParameterSource namedParameters = new MapSqlParameterSource().addValue("authorId", authorId);

        return namedParameterJdbcOperations.query(
                "select b.id, b.name, b.author_id, b.genre_id, a.name as author, g.type as genre " +
                        "from books b " +
                        "left join authors a on b.author_id=a.id " +
                        "left join genres g on b.genre_id=g.id " +
                        "where b.author_id=:authorId",
                namedParameters,
                new BookMapper());
    }

    @Override
    public List<Book> findByGenreId(long genreId) {
        SqlParameterSource namedParameters = new MapSqlParameterSource().addValue("genreId", genreId);

        return namedParameterJdbcOperations.query(
                "select b.id, b.name, b.author_id, b.genre_id, a.name as author, g.type as genre " +
                        "from books b " +
                        "left join authors a on b.author_id=a.id " +
                        "left join genres g on b.genre_id=g.id " +
                        "where b.genre_id=:genreId",
                namedParameters,
                new BookMapper());
    }

    @Override
    public Book findById(long bookId) {
        SqlParameterSource namedParameters = new MapSqlParameterSource().addValue("id", bookId);

        return namedParameterJdbcOperations.queryForObject(
                "select b.id, b.name, b.author_id, b.genre_id, a.name as author, g.type as genre " +
                        "from books b " +
                        "left join authors a on b.author_id=a.id " +
                        "left join genres g on b.genre_id=g.id " +
                        "where b.id=:id",
                namedParameters,
                new BookMapper());
    }

    @Override
    public Long add(Book book) {
        MapSqlParameterSource namedParameters =
                new MapSqlParameterSource()
                        .addValue("name", book.getName())
                        .addValue("authorId", book.getAuthor().getId())
                        .addValue("genreId", book.getGenre().getId());

        namedParameterJdbcOperations.update(
                "insert into books (name, author_id, genre_id) values(:name, :authorId, :genreId)",
                namedParameters);

        return namedParameterJdbcOperations.queryForObject(
                "select b.id from books b where b.name=:name",
                namedParameters,
                Long.class);
    }

    @Override
    public boolean delete(long bookId) {
        SqlParameterSource namedParameters = new MapSqlParameterSource().addValue("id", bookId);

        int countOfDeletedRows =
                namedParameterJdbcOperations.update("delete from books b where b.id=:id", namedParameters);

        return countOfDeletedRows > 0;
    }
}
