package com.application.book.catalog.domain;

import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.query.TextCriteria;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface BookItemRepository extends MongoRepository<BookItem, String> {
    Optional<BookItem> findByCode(String code);

    Page<BookItem> findAllBy(TextCriteria searchCriteria, Pageable page);

    boolean existsProductByCode(String code);
}
