package com.bookstore.application.favorites;

import org.springframework.data.jpa.repository.JpaRepository;

public interface FavoriteBookRepo extends JpaRepository<FavoriteBook, EmbeddableKey> {
}
