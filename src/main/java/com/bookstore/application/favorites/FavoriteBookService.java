package com.bookstore.application.favorites;

import com.bookstore.application.books.Book;
import com.bookstore.application.books.BookRepository;
import com.bookstore.application.users.User;
import com.bookstore.application.users.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
public class FavoriteBookService {

    private final FavoriteBookRepo favoriteBookRepo;
    private final UserRepository userRepository;
    private final BookRepository bookRepository;

    public FavoriteBookService(FavoriteBookRepo favoriteBookRepo, UserRepository userRepository, BookRepository bookRepository) {
        this.favoriteBookRepo = favoriteBookRepo;
        this.userRepository = userRepository;
        this.bookRepository = bookRepository;
    }

    public List<FavoriteBook> getUserFavBooks() {
        return favoriteBookRepo.findAll();
    }

    @Transactional
    public FavoriteBook addFavBook(FavoriteBookDTO favoriteBookDTO) {

        User user = userRepository.findById(favoriteBookDTO.getUserId())
                .orElseThrow(() -> new EntityNotFoundException("User with id " + favoriteBookDTO.getUserId() + " is not found"));

        Book book = bookRepository.findById(favoriteBookDTO.getBookId())
                .orElseThrow(() -> new EntityNotFoundException("Book with id " + favoriteBookDTO.getUserId() + " is not found"));
        FavoriteBook favoriteBook = new FavoriteBook(user, book);
        favoriteBookRepo.save(favoriteBook);

        return favoriteBook;
    }
}
