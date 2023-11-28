package com.bookstore.application.carts;

import lombok.Getter;
import lombok.Setter;


@Setter
@Getter
public class CartItemDTO {
    private Long id;
    private Long bookId;
    private int quantity;
}