package ru.mexof.spring.training.jpa.services.author;

import ru.mexof.spring.training.jpa.services.IdNotExistException;

public interface AuthorRepository {
    Long findIdByName(String name) throws IdNotExistException;
    Long addAuthor(Author author);
}
