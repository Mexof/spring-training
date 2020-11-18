package ru.mexof.spring.training.data.infrastructure;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import ru.mexof.spring.training.data.services.LibraryService;
import ru.mexof.spring.training.data.services.LibraryServiceException;
import ru.mexof.spring.training.data.services.author.Author;
import ru.mexof.spring.training.data.services.book.Book;
import ru.mexof.spring.training.data.services.genre.Genre;

import java.util.List;

@ShellComponent
public class LibraryConsole {
    private final LibraryService libraryService;

    @Autowired
    public LibraryConsole(LibraryService libraryService) {
        this.libraryService = libraryService;
    }

    @ShellMethod("Show all books in library")
    public List<Book> findAllBooks() {
        return libraryService.findAll();
    }

    @ShellMethod("Find book by id")
    public Book findById(long bookId) {
        return libraryService.findById(bookId);
    }

    @ShellMethod("Add book to library")
    public void addBook(String name, String authorName, String genre) {
        Book book = new Book(name, new Author(authorName), new Genre(genre));
        libraryService.addBook(book);
    }

    @ShellMethod("Delete book from library")
    public void deleteBook(long bookId) {
        libraryService.deleteBookById(bookId);
    }

    @ShellMethod("Find books by author's name")
    public List<Book> findBooksByAuthor(String author) throws LibraryServiceException {
        return libraryService.findByAuthor(author);
    }

    @ShellMethod("Find books by genre")
    public List<Book> findBooksByGenre(String genre) throws LibraryServiceException {
        return libraryService.findByGenre(genre);
    }
}
