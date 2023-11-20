package com.bookstore.application.users;

public class UserGlobalException extends RuntimeException {
    public UserGlobalException(String errorMessage) {
        super(errorMessage);
    }
}
