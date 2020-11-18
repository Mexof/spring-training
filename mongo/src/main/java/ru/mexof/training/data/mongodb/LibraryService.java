package ru.mexof.training.data.mongodb;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LibraryService {
    private final BookRepository bookRepository;

    @Autowired
    public LibraryService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    public List<Book> findAll() {
        return bookRepository.findAll();
    }

    public Book findById(ObjectId bookId) {
        return bookRepository.findById(bookId);
    }

    public void addBook(Book book) {
        bookRepository.save(book);
    }

    public void deleteBookById(ObjectId bookId) {
        bookRepository.delete(bookRepository.findById(bookId));
    }

    public List<Book> findByAuthor(String authorName) {
        return bookRepository.findByAuthor(new Author(authorName));
    }

    public List<Book> findByGenre(String genreType) {
        return bookRepository.findByGenre(new Genre(genreType));
    }
}
