package com.bookstore.application.genres;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "genres")
@Getter
@Setter
public class Genre {
    @Id
    @Column(name = "genre_id")
    private Integer genre_id;

    @Column(name = "genre_name")
    private String genre_name;
}
