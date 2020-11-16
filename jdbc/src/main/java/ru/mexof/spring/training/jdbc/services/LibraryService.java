package ru.mexof.spring.training.jdbc.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.mexof.spring.training.jdbc.services.author.Author;
import ru.mexof.spring.training.jdbc.services.author.AuthorRepository;
import ru.mexof.spring.training.jdbc.services.book.Book;
import ru.mexof.spring.training.jdbc.services.book.BookRepository;
import ru.mexof.spring.training.jdbc.services.genre.Genre;
import ru.mexof.spring.training.jdbc.services.genre.GenreRepository;

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
        createNewOrReuseExistedAuthor(book.getAuthor());
        createNewOrReuseExistedGenre(book.getGenre());

        return bookRepository.add(book);
    }

    private void createNewOrReuseExistedAuthor(Author author) {
        Long authorId;
        try {
            authorId = authorRepository.findIdByName(author.getName());
        } catch (IdNotExistException e) {
            authorId = authorRepository.addAuthor(author);
        }
        author.setId(authorId);
    }

    private void createNewOrReuseExistedGenre(Genre genre) {
        Long genreId;
        try {
            genreId = genreRepository.findIdByType(genre.getType());
        } catch (IdNotExistException e) {
            genreId = genreRepository.addGenre(genre);
        }
        genre.setId(genreId);
    }

    @Transactional
    public boolean deleteBookById(long bookId) {
        return bookRepository.delete(bookId);
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
