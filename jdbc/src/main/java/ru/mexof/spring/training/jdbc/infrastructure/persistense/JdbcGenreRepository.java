package ru.mexof.spring.training.jdbc.infrastructure.persistense;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.stereotype.Repository;
import ru.mexof.spring.training.jdbc.services.genre.Genre;
import ru.mexof.spring.training.jdbc.services.genre.GenreRepository;
import ru.mexof.spring.training.jdbc.services.IdNotExistException;

@Repository
public class JdbcGenreRepository implements GenreRepository {
    private final NamedParameterJdbcOperations namedParameterJdbcOperations;

    @Autowired
    public JdbcGenreRepository(NamedParameterJdbcOperations namedParameterJdbcOperations) {
        this.namedParameterJdbcOperations = namedParameterJdbcOperations;
    }

    @Override
    public Long findIdByType(String genreType) throws IdNotExistException {
        MapSqlParameterSource namedParameters =
                new MapSqlParameterSource().addValue("genreType", genreType);

        try {
            return namedParameterJdbcOperations.queryForObject(
                    "select g.id from genres g where g.type=:genreType",
                    namedParameters,
                    Long.class
            );
        } catch (EmptyResultDataAccessException e) {
            throw new IdNotExistException(e);
        }
    }

    @Override
    public Long addGenre(Genre genre) {
        MapSqlParameterSource namedParameters =
                new MapSqlParameterSource().addValue("genreType", genre.getType());

        namedParameterJdbcOperations.update("insert into genres (type) values(:genreType)", namedParameters);

        return namedParameterJdbcOperations.queryForObject(
                "select g.id from genres g where g.type=:genreType",
                namedParameters,
                Long.class
        );
    }
}
