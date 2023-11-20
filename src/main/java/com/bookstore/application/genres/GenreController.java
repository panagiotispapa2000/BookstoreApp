package com.bookstore.application.genres;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/genres")
public class GenreController {

    @Autowired
    GenreService genreService;

    @GetMapping
    public List<Genre> getGenres() {
        return genreService.getGenres();
    }

    @PostMapping
    public Genre createGenre(@RequestBody Genre genre) {
        return genreService.create(genre);
    }
}
