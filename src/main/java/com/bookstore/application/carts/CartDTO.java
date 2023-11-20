package com.bookstore.application.carts;

import lombok.Getter;
import lombok.Setter;
import java.util.ArrayList;
import java.util.List;

@Setter
@Getter
public class CartDTO {
    private Long id;
    private Long userId;
    private List<CartItemDTO> cartItemList = new ArrayList<>();
}