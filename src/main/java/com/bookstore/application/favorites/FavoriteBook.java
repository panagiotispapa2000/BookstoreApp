package com.bookstore.application.favorites;

import com.bookstore.application.books.Book;
import com.bookstore.application.users.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class FavoriteBook {

    @EmbeddedId
    private EmbeddableKey embeddableKey;

    @MapsId("userId")
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id")
    private User user;

    @MapsId("bookId")
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "book_id")
    private Book book;

    public FavoriteBook(User user, Book book) {
        this.embeddableKey = new EmbeddableKey(user.getId(), book.getId());
        this.user = user;
        this.book = book;
    }
}