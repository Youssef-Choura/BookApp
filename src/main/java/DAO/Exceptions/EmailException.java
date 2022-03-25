package DAO.Exceptions;

// Exception thrown when the Email already exists in database

public class EmailException extends Exception{
    public EmailException(String message){
        super(message);
    }
}
