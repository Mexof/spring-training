package ru.mexof.spring.training.data.services.book;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.mexof.spring.training.data.services.author.Author;
import ru.mexof.spring.training.data.services.genre.Genre;

import java.util.List;

public interface BookRepository extends JpaRepository<Book, Long> {
    List<Book> findByAuthor(Author author);
    List<Book> findByGenre(Genre genre);
    Book findById(long bookId);
}
