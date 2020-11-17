package ru.mexof.spring.training.jpa.infrastructure.persistense;

import org.springframework.stereotype.Repository;
import ru.mexof.spring.training.jpa.services.IdNotExistException;
import ru.mexof.spring.training.jpa.services.genre.Genre;
import ru.mexof.spring.training.jpa.services.genre.GenreRepository;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

@Repository
public class JpaGenreRepository implements GenreRepository {
    @PersistenceContext
    private final EntityManager entityManager;

    public JpaGenreRepository(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public Long findIdByType(String genreType) throws IdNotExistException {
        try {
            TypedQuery<Long> query = entityManager.createQuery(
                    "select g.id from Genre g where g.type=:genreType", Long.class);
            query.setParameter("genreType", genreType);

            return query.getSingleResult();
        } catch (NoResultException e) {
            throw new IdNotExistException(e);
        }
    }

    @Override
    public Long addGenre(Genre genre) {
        entityManager.persist(genre);
        return genre.getId();
    }
}
