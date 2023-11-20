package com.bookstore.application.books;

import com.bookstore.application.genres.Genre;
import com.bookstore.application.favorites.FavoriteBook;
import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Entity
@Table(name = "books")
@Getter
@Setter
public class Book {
    @Id
    @Column(name = "book_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "title")
    private String title;

    @Column(name = "author")
    private String author;

    @Column(name = "description")
    private String description;

    @Column(name = "price")
    private float price;

    @Column(name = "availability")
    private boolean availability;

    @ManyToOne
    @JoinColumn(name = "genre_id")
    private Genre genre;

    @Column(name = "isbn")
    private String isbn;

    @Column(name = "publication")
    private int publication;

    @Column(name = "publisher")
    private String publisher;

    @JsonBackReference
    @OneToMany(mappedBy = "book", cascade = CascadeType.ALL, orphanRemoval = true)
    Set<FavoriteBook> favoriteBooks;
}
