package org.example.deltawebfacade.exceptions;

public class ExistByIdException extends RuntimeException {
    public <T> ExistByIdException(Class<T> clazz, Long id){
        super("Element from class " + clazz.getName() + " with id = " + id + " already exist");
    }
}
