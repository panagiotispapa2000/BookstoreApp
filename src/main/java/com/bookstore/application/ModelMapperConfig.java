package com.bookstore.application;


import com.bookstore.application.books.Book;
import com.bookstore.application.books.BookDTO;
import com.bookstore.application.carts.Cart;
import com.bookstore.application.carts.CartDTO;
import com.bookstore.application.favorites.FavoriteBook;
import com.bookstore.application.favorites.FavoriteBookDTO;
import com.bookstore.application.users.User;
import com.bookstore.application.users.UserDTO;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ModelMapperConfig{
    @Bean
    public ModelMapper modelMapper(){
        ModelMapper modelMapper = new ModelMapper();
        
        /* For fetching a single user without the password field */
        modelMapper.typeMap(User.class, UserDTO.class);

        modelMapper.typeMap(Cart.class, CartDTO.class);

        modelMapper.typeMap(Book.class, BookDTO.class)
                .addMapping(src -> src.getGenre().getGenre_id(), BookDTO::setGenreId);

        modelMapper.typeMap(FavoriteBook.class, FavoriteBookDTO.class);

        return modelMapper;
    }
}
