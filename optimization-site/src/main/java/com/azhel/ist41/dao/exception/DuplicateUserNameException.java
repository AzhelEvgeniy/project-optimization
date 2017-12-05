package com.azhel.ist41.dao.exception;

public class DuplicateUserNameException extends Exception{
    public DuplicateUserNameException(){
        super();
    }

    public DuplicateUserNameException(String message) {
        super(message);
    }

    public DuplicateUserNameException(String message, Throwable cause) {
        super(message, cause);
    }

}
