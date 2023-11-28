package com.bookstore.application.carts;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
public interface CartItemRepo extends JpaRepository<CartItem, Long> {

}
