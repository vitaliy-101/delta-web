package org.example.deltawebfacade.exceptions;

public class FileNullException extends RuntimeException {
    public <T> FileNullException(){
        super("The sent file is null");
    }
}
