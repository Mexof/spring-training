package ru.mexof.training.data.mongodb;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;

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
    public Book findById(ObjectId bookId) {
        return libraryService.findById(bookId);
    }

    @ShellMethod("Add book to library")
    public void addBook(String name, String authorName, String genre) {
        Book book = new Book(name, new Author(authorName), new Genre(genre));
        libraryService.addBook(book);
    }

    @ShellMethod("Delete book from library")
    public void deleteBook(ObjectId bookId) {
        libraryService.deleteBookById(bookId);
    }

    @ShellMethod("Find books by author's name")
    public List<Book> findBooksByAuthor(String author) {
        return libraryService.findByAuthor(author);
    }

    @ShellMethod("Find books by genre")
    public List<Book> findBooksByGenre(String genre) {
        return libraryService.findByGenre(genre);
    }
}
