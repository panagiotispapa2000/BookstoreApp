package com.bookstore.application.genres;

import org.springframework.stereotype.Service;
import java.util.List;
@Service
public class GenreService {

    private final GenreRepository genreRepository;

    public GenreService(GenreRepository genreRepository) {
        this.genreRepository = genreRepository;
    }

    public List<Genre> getGenres() {
        return genreRepository.findAll();
    }

    public Genre create(Genre genre) {
        return genreRepository.save(genre);
    }
}
