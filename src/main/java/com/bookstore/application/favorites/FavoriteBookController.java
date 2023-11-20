package com.bookstore.application.favorites;

import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/favorites")
public class FavoriteBookController {

    private final FavoriteBookService favoriteBookService;

    public FavoriteBookController(FavoriteBookService favoriteBookService) {
        this.favoriteBookService = favoriteBookService;
    }

    @GetMapping
    public List<FavoriteBook> getFavoriteBooks() {
        return favoriteBookService.getUserFavBooks();
    }

    @PostMapping
    public FavoriteBook addFavoriteBook(@RequestBody FavoriteBookDTO favoriteBookDTO) {
        return favoriteBookService.addFavBook(favoriteBookDTO);
    }
}
