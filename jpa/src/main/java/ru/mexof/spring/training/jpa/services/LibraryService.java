package ru.mexof.spring.training.jpa.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.mexof.spring.training.jpa.services.author.AuthorRepository;
import ru.mexof.spring.training.jpa.services.book.Book;
import ru.mexof.spring.training.jpa.services.book.BookRepository;
import ru.mexof.spring.training.jpa.services.genre.GenreRepository;

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
    public long addBook(Book book) {
        authorRepository.addAuthor(book.getAuthor());
        genreRepository.addGenre(book.getGenre());
        return bookRepository.add(book);
    }

    @Transactional
    public void deleteBookById(long bookId) {
        bookRepository.delete(bookId);
    }

    public List<Book> findByAuthor(String authorName) throws LibraryServiceException {
        try {
            long authorId = authorRepository.findIdByName(authorName);
            return bookRepository.findByAuthorId(authorId);
        } catch (IdNotExistException e) {
            throw new LibraryServiceException("This author name doesn't exist in database", e);
        }
    }

    public List<Book> findByGenre(String genreType) throws LibraryServiceException {
        try {
            long genreId = genreRepository.findIdByType(genreType);
            return bookRepository.findByGenreId(genreId);
        } catch (IdNotExistException e) {
            throw new LibraryServiceException("This genre type doesn't exist in database", e);
        }
    }
}
