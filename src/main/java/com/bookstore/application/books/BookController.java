package com.bookstore.application.books;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/books")
public class BookController {

    private final BookService bookService;

    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    //With JPA specifications included
    @GetMapping
    public List<Book> getBooks(@RequestParam(value = "author", required = false) String authorName,
                               @RequestParam(value = "genre", required = false) Integer genreID,
                               @RequestParam(value = "page", defaultValue = "0", required = false) int page,
                               @RequestParam(value = "size", defaultValue = "5", required = false) int size,
                               @RequestParam(defaultValue = "price") String sortByPrice) {
        return bookService.getBooksByAuthorOrGenre(authorName, genreID, page, size, sortByPrice);
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<Book> getBookById(@PathVariable("id") Long bookID) {
        return bookService.getBook(bookID);
    }

    @PostMapping
    public BookDTO createBook(@RequestBody BookDTO bookDTO) {
        return bookService.create(bookDTO);
    }

    @PutMapping("/{id}")
    public BookDTO updateBook(@PathVariable("id") Long bookID,
                              @RequestBody BookDTO updatedBookDTO) {
        return bookService.update(bookID, updatedBookDTO);
    }

    @DeleteMapping
    public void deleteAllBooks() {
        bookService.delete();
    }

    @DeleteMapping(path = "/{id}")
    public void deleteBook(@PathVariable("id") Long bookID) {
        bookService.deleteBookById(bookID);
    }
}