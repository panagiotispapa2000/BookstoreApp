package com.bookstore.application.carts;

import com.bookstore.application.books.Book;
import com.bookstore.application.books.BookRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class CartService {

    private final CartRepository cartRepository;
    private final CartItemRepo cartItemRepo;
    private final BookRepository bookRepository;
    private final ModelMapper modelMapper;

    public CartService(CartRepository cartRepository, BookRepository bookRepository, ModelMapper modelMapper, CartItemRepo cartItemRepo) {
        this.cartRepository = cartRepository;
        this.bookRepository = bookRepository;
        this.modelMapper = modelMapper;
        this.cartItemRepo = cartItemRepo;
    }

    public List<Cart> getAllCarts() {
        return cartRepository.findAll();
    }

    public ResponseEntity<Cart> getCart(Long cartID) {
        Optional<Cart> cart = cartRepository.findById(cartID);
        if (cart.isPresent()) {
            return ResponseEntity.ok(cart.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @Transactional
    public CartDTO addItemCart(CartDTO cartDTO) {
        Cart cart = modelMapper.map(cartDTO, Cart.class);
        cart.setCreationDate(LocalDateTime.now());

        for (CartItem cartItem : cart.getCartItemList()) {
            Book book = bookRepository.findById(cartItem.getBook().getId())
                    .orElseThrow(() -> new EntityNotFoundException("Book with ID "
                            + cartItem.getBook().getId() + " was not found."));
            if (book.isAvailability()) { //Check if the book is available for adding
                if (cart.getCartItemList().stream()
                        .filter(item -> item.getBook().getId().equals(book.getId()))
                        .toList()
                        .size() == 2) { //Check if the book is added twice
                    throw new DuplicateKeyException("You cannot add the same Book ID two times");
                } else {
                    cartItem.setBook(book);
                    cartItem.setCart(cart);
                    cartItem.setPrice(cartItem.getQuantity() * cartItem.getBook().getPrice());
                }
            } else
                throw new EntityNotFoundException("Book with ID " + cartItem.getBook().getId() + " is not available for adding to the cart");
        }

        cart = cartRepository.save(cart);
        cartDTO = modelMapper.map(cart, CartDTO.class);

        return cartDTO;
    }

    @Transactional
    public CartDTO updateItemCart(Long cartID, CartDTO updatedCartDTO) {
        Cart existingCart = cartRepository.findById(cartID)
                .orElseThrow(() -> new EntityNotFoundException("Cart with ID " + cartID + " was not found."));

        modelMapper.map(updatedCartDTO, existingCart);

        for (CartItemDTO updatedCartItemDTO : updatedCartDTO.getCartItemList()) {
            Book book = bookRepository.findById(updatedCartItemDTO.getBookId())
                    .orElseThrow(() -> new EntityNotFoundException("Book with ID "
                            + updatedCartItemDTO.getBookId() + " not found."));
            if (book.isAvailability()) {

                CartItem cartItem = existingCart.getCartItemList()
                        .stream()
                        .filter(item -> item.getBook().getId().equals(updatedCartItemDTO.getBookId()))
                        .findAny()
                        .orElse(new CartItem());

                cartItem.setQuantity(updatedCartItemDTO.getQuantity());
                cartItem.setCart(existingCart);
                cartItem.setBook(book);
                cartItem.setPrice(cartItem.getQuantity() * cartItem.getBook().getPrice());
            } else {
                throw new EntityNotFoundException("Book with ID " + updatedCartItemDTO.getBookId() + " is not available");
            }
        }
        return modelMapper.map(existingCart, CartDTO.class);
    }

    public void deleteCart(Long id) {
        cartRepository.deleteById(id);
    }

    @Transactional
    public void deleteItem(Long id) {
        Optional<CartItem> cartItem = cartItemRepo.findById(id);

        Cart cart = cartItem.get().getCart();
        cart.getCartItemList().remove(cartItem.get());
        cartItemRepo.deleteById(id);
        if (cart.getCartItemList().isEmpty()) { //If the list of cart items is empty after the last item cart was deleted, then delete the whole cart
            deleteCart(cart.getId());
        }
    }
}