package ru.mexof.spring.training.jpa.services.genre;

import ru.mexof.spring.training.jpa.services.IdNotExistException;

public interface GenreRepository {
    Long findIdByType(String genre) throws IdNotExistException;
    Long addGenre(Genre genre);
}
