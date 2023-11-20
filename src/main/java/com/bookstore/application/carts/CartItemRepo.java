package com.bookstore.application.carts;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
@Repository
public interface CartItemRepo extends JpaRepository<CartItem, Long> {

}
