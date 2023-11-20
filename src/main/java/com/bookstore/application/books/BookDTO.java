package com.bookstore.application.books;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BookDTO {
    private Integer id;
    private String title;
    private String author;
    private String description;
    private float price;
    private boolean availability;
    private Integer genreId;
    private String isbn;
    private int publication;
    private String publisher;
}
