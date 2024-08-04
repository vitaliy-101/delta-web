package org.example.deltawebfacade.exceptions;


public class NotFoundByIdException extends RuntimeException {
    public <T> NotFoundByIdException(Class<T> clazz, Long id){
        super("Element in class " + clazz.getName() + " with id = " + id + " not found");
    }
}