package ru.mexof.spring.training.jdbc.services.author;

import ru.mexof.spring.training.jdbc.services.IdNotExistException;

public interface AuthorRepository {
    Long findIdByName(String name) throws IdNotExistException;
    Long addAuthor(Author author);
}
