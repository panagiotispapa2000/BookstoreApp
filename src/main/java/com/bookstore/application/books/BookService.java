package com.bookstore.application.books;

import com.bookstore.application.genres.Genre;
import com.bookstore.application.genres.GenreRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.criteria.Predicate;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class BookService {
    private final BookRepository bookRepository;
    private final GenreRepository genreRepository;
    private final ModelMapper modelMapper;

    public BookService(BookRepository bookRepository, GenreRepository genreRepository, ModelMapper modelMapper) {
        this.bookRepository = bookRepository;
        this.genreRepository = genreRepository;
        this.modelMapper = modelMapper;
    }

    @Transactional
    //With JPA specifications included
    public List<Book> getBooksByAuthorOrGenre(String authorName, Integer genreID, int page, int size, String byPrice) {
        Specification<Book> bookSpec = (root, query, criteriaBuilder) -> {
            Predicate authorPredicate = authorName != null
                    ? criteriaBuilder.equal(root.get("author"), authorName)
                    : criteriaBuilder.conjunction();

            Predicate genrePredicate = genreID != null
                    ? criteriaBuilder.equal(root.get("genre").get("genre_id"), genreID)
                    : criteriaBuilder.conjunction();

            return criteriaBuilder.and(authorPredicate, genrePredicate);
        };
        Pageable pageable = PageRequest.of(page, size, Sort.by(byPrice));
        return bookRepository.findAll(bookSpec, pageable).getContent();
    }

    @Transactional
    public ResponseEntity<Book> getBook(Long bookID) {
        Optional<Book> book = bookRepository.findById(bookID);
        if (book.isPresent()) {
            return ResponseEntity.ok(book.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @Transactional
    public BookDTO create(BookDTO bookDTO) {
        Genre genre = genreRepository.findById(bookDTO.getGenreId())
                .orElseThrow(() -> new EntityNotFoundException("Genre ID does not exist"));

        Book book = modelMapper.map(bookDTO, Book.class);
        book.setGenre(genre);
        bookRepository.save(book);

        bookDTO = modelMapper.map(book, BookDTO.class);
        return bookDTO;
    }

    @Transactional
    public BookDTO update(Long id, BookDTO updatedBookDTO) {
        Book existingBook = bookRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Book with id " + id + " not found"));
        Genre genre = genreRepository.findById(updatedBookDTO.getGenreId())
                .orElseThrow(() -> new EntityNotFoundException("Genre with id" + updatedBookDTO.getGenreId() + " not found"));
        modelMapper.map(updatedBookDTO, existingBook);
        existingBook.setGenre(genre);
        return modelMapper.map(existingBook, BookDTO.class);
    }

    public void delete() {
        bookRepository.deleteAll();
    }

    public void deleteBookById(Long bookID) {
        bookRepository.deleteById(bookID);
    }
}