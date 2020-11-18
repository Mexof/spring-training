package ru.mexof.training.data.mongodb;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface BookRepository extends MongoRepository<Book, Long> {
    List<Book> findByAuthor(Author author);
    List<Book> findByGenre(Genre genre);
    Book findById(ObjectId bookId);
}
