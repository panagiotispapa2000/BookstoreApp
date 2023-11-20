package com.bookstore.application.carts;

import com.bookstore.application.books.Book;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
@Entity
@Table(name = "cart_items")
@Getter
@Setter
public class CartItem {
    @Id
    @Column(name = "cart_item_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "cart_id")
    private Cart cart;

    @ManyToOne
    @JoinColumn(name = "book_id")
    private Book book;

    @Column(name = "cart_item_quantity")
    private int quantity;

    @Column(name = "cart_item_price")
    private float price;
}
