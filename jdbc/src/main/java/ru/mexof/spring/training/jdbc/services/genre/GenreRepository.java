package ru.mexof.spring.training.jdbc.services.genre;

import ru.mexof.spring.training.jdbc.services.IdNotExistException;

public interface GenreRepository {
    Long findIdByType(String genre) throws IdNotExistException;
    Long addGenre(Genre genre);
}
