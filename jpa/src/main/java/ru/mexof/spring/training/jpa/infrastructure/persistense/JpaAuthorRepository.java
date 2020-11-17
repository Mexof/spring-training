package ru.mexof.spring.training.jpa.infrastructure.persistense;

import org.springframework.stereotype.Repository;
import ru.mexof.spring.training.jpa.services.IdNotExistException;
import ru.mexof.spring.training.jpa.services.author.Author;
import ru.mexof.spring.training.jpa.services.author.AuthorRepository;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

@Repository
public class JpaAuthorRepository implements AuthorRepository {
    @PersistenceContext
    private final EntityManager entityManager;

    public JpaAuthorRepository(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public Long findIdByName(String authorName) throws IdNotExistException {
        try {
            TypedQuery<Long> query = entityManager.createQuery(
                    "select a.id from Author a where a.name=:authorName", Long.class);
            query.setParameter("authorName", authorName);

            return query.getSingleResult();
        } catch (NoResultException e) {
            throw new IdNotExistException(e);
        }
    }

    @Override
    public Long addAuthor(Author author) {
        entityManager.persist(author);
        return author.getId();
    }
}
