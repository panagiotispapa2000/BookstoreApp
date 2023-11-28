package com.bookstore.application.carts;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/carts")
public class CartController {
    private final CartService cartService;
    public CartController(CartService cartService) {
        this.cartService = cartService;
    }

    @GetMapping
    public List<Cart> getCarts() {
        return cartService.getAllCarts();
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<Cart> getCartById(@PathVariable("id") Long cartID) {
        return cartService.getCart(cartID);
    }

    @PostMapping
    public CartDTO createCart(@RequestBody CartDTO cartDTO) {
        return cartService.addItemCart(cartDTO);
    }

    @PutMapping(path = "/{id}")
    public CartDTO updateCart(@PathVariable("id") Long cartID,
                              @RequestBody CartDTO updatedCartDTO) {
        return cartService.updateItemCart(cartID, updatedCartDTO);
    }

    @DeleteMapping(path = "/{id}")
    public void deleteCart(@PathVariable("id") Long cartID) {
        cartService.deleteCart(cartID);
    }

    @DeleteMapping(path = "/items/{id}")
    public void deleteCartItem(@PathVariable("id") Long cartItemID) {
        cartService.deleteItem(cartItemID);
    }
}