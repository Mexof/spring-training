package ru.mexof.spring.training.data.services.genre;

import org.springframework.data.jpa.repository.JpaRepository;

public interface GenreRepository extends JpaRepository<Genre, Long> {
    Genre findByType(String genreType);
}
