package ru.mexof.spring.training.jpa.infrastructure.persistense;

import org.springframework.stereotype.Repository;
import ru.mexof.spring.training.jpa.services.book.Book;
import ru.mexof.spring.training.jpa.services.book.BookRepository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import java.util.List;

@Repository
public class JpaBookRepository implements BookRepository {
    @PersistenceContext
    private final EntityManager entityManager;

    public JpaBookRepository(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public List<Book> findAll() {
        Query query = entityManager.createQuery("select b from Book b", Book.class);

        return query.getResultList();
    }

    @Override
    public List<Book> findByAuthorId(long authorId) {
        Query query = entityManager.createQuery("select b from Book b where b.author.id=:authorId", Book.class);
        query.setParameter("authorId", authorId);

        return query.getResultList();
    }

    @Override
    public List<Book> findByGenreId(long genreId) {
        Query query = entityManager.createQuery("select b from Book b where b.genre.id=:genreId", Book.class);
        query.setParameter("genreId", genreId);

        return query.getResultList();
    }

    @Override
    public Book findById(long bookId) {
        TypedQuery<Book> query = entityManager.createQuery("select b from Book b where b.id=:bookId", Book.class);
        query.setParameter("bookId", bookId);

        return query.getSingleResult();
    }

    @Override
    public Long add(Book book) {
        entityManager.persist(book);

        return book.getId();
    }

    @Override
    public void delete(long bookId) {
        entityManager.remove(findById(bookId));
    }
}
