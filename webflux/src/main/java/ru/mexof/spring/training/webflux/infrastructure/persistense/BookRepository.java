package ru.mexof.spring.training.webflux.infrastructure.persistense;

import org.bson.types.ObjectId;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface BookRepository extends ReactiveCrudRepository<Book, Long> {
    Flux<Book> findByAuthor(Author author);
    Flux<Book> findByGenre(Genre genre);
    Mono<Book> findById(ObjectId bookId);
}
