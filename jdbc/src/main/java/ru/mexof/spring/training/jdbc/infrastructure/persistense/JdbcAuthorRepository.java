package ru.mexof.spring.training.jdbc.infrastructure.persistense;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.stereotype.Repository;
import ru.mexof.spring.training.jdbc.services.IdNotExistException;
import ru.mexof.spring.training.jdbc.services.author.Author;
import ru.mexof.spring.training.jdbc.services.author.AuthorRepository;

@Repository
public class JdbcAuthorRepository implements AuthorRepository {
    private final NamedParameterJdbcOperations namedParameterJdbcOperations;

    @Autowired
    public JdbcAuthorRepository(NamedParameterJdbcOperations namedParameterJdbcOperations) {
        this.namedParameterJdbcOperations = namedParameterJdbcOperations;
    }

    @Override
    public Long findIdByName(String authorName) throws IdNotExistException {
        MapSqlParameterSource namedParameters =
                new MapSqlParameterSource().addValue("authorName", authorName);

        try {
            return namedParameterJdbcOperations.queryForObject(
                    "select a.id from authors a where a.name=:authorName",
                    namedParameters,
                    Long.class
            );
        } catch (EmptyResultDataAccessException e) {
            throw new IdNotExistException(e);
        }
    }

    @Override
    public Long addAuthor(Author author) {
        MapSqlParameterSource namedParameters =
                new MapSqlParameterSource().addValue("authorName", author.getName());

        namedParameterJdbcOperations.update("insert into authors (name) values(:authorName)", namedParameters);

        return namedParameterJdbcOperations.queryForObject(
                "select a.id from authors a where a.name=:authorName",
                namedParameters,
                Long.class
        );
    }
}
