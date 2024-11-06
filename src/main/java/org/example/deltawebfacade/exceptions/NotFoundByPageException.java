package org.example.deltawebfacade.exceptions;

public class NotFoundByPageException extends RuntimeException {
    public <T> NotFoundByPageException(){
        super("no files found on this page");
    }
}
