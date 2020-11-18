package ru.mexof.spring.training.data.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.mexof.spring.training.data.services.author.Author;
import ru.mexof.spring.training.data.services.author.AuthorRepository;
import ru.mexof.spring.training.data.services.book.Book;
import ru.mexof.spring.training.data.services.book.BookRepository;
import ru.mexof.spring.training.data.services.genre.Genre;
import ru.mexof.spring.training.data.services.genre.GenreRepository;

import java.util.List;

@Service
public class LibraryService {
    private final BookRepository bookRepository;
    private final AuthorRepository authorRepository;
    private final GenreRepository genreRepository;

    @Autowired
    public LibraryService(BookRepository bookRepository,
                          AuthorRepository authorRepository,
                          GenreRepository genreRepository) {
        this.bookRepository = bookRepository;
        this.authorRepository = authorRepository;
        this.genreRepository = genreRepository;
    }

    public List<Book> findAll() {
        return bookRepository.findAll();
    }

    public Book findById(long bookId) {
        return bookRepository.findById(bookId);
    }

    @Transactional
    public void addBook(Book book) {
        authorRepository.save(book.getAuthor());
        genreRepository.save(book.getGenre());
        bookRepository.save(book);
    }

    @Transactional
    public void deleteBookById(long bookId) {
        bookRepository.delete(bookRepository.findById(bookId));
    }

    public List<Book> findByAuthor(String authorName) {
        Author author = authorRepository.findByName(authorName);
        return bookRepository.findByAuthor(author);
    }

    public List<Book> findByGenre(String genreType) {
        Genre genre = genreRepository.findByType(genreType);
        return bookRepository.findByGenre(genre);
    }
}
