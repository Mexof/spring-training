package ru.mexof.spring.training.webflux.infrastructure.resources;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.mexof.spring.training.webflux.infrastructure.persistense.Book;
import ru.mexof.spring.training.webflux.services.LibraryService;

@RestController
public class BookLibraryResource {
    private final LibraryService libraryService;

    @Autowired
    public BookLibraryResource(LibraryService libraryService) {
        this.libraryService = libraryService;
    }

    @GetMapping("/persons")
    public Flux<Book> findAll() {
        return libraryService.findAll();
    }

    @GetMapping("/persons/filter/author")
    public Flux<Book> findByAuthor(@RequestParam String authorName) {
        return libraryService.findByAuthor(authorName);
    }

    @GetMapping("/persons/filter/genre")
    public Flux<Book> findByGenre(@RequestParam String genreType) {
        return libraryService.findByGenre(genreType);
    }

    @PostMapping("/person")
    public void addBook(@RequestBody Book book) {
        libraryService.addBook(book);
    }

    @GetMapping("/person/{bookId}")
    public Mono<Book> findById(@PathVariable ObjectId bookId) {
        return libraryService.findById(bookId);
    }

    @DeleteMapping("/person/{bookId}")
    public void deleteBookById(@PathVariable ObjectId bookId) {
        libraryService.deleteBookById(bookId);
    }
}
