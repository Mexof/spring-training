package ru.mexof.spring.training.webflux.services;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.mexof.spring.training.webflux.infrastructure.persistense.Author;
import ru.mexof.spring.training.webflux.infrastructure.persistense.Book;
import ru.mexof.spring.training.webflux.infrastructure.persistense.BookRepository;
import ru.mexof.spring.training.webflux.infrastructure.persistense.Genre;

@Service
public class LibraryService {
    private final BookRepository bookRepository;

    @Autowired
    public LibraryService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    public Flux<Book> findAll() {
        return bookRepository.findAll();
    }

    public Mono<Book> findById(ObjectId bookId) {
        return bookRepository.findById(bookId);
    }

    public void addBook(Book book) {
        bookRepository.save(book);
    }

    public void deleteBookById(ObjectId bookId) {
        bookRepository.findById(bookId).subscribe(bookRepository::delete);
    }

    public Flux<Book> findByAuthor(String authorName) {
        return bookRepository.findByAuthor(new Author(authorName));
    }

    public Flux<Book> findByGenre(String genreType) {
        return bookRepository.findByGenre(new Genre(genreType));
    }
}
