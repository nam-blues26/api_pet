package com.pet.exception;

public class ExistedCategoryException extends RuntimeException{
    public ExistedCategoryException(String message) {
        super(message);
    }
}
