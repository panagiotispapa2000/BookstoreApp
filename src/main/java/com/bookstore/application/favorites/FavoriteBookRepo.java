package com.bookstore.application.favorites;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FavoriteBookRepo extends JpaRepository<FavoriteBook, EmbeddableKey> {
}
