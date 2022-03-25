package DAO.Exceptions;

// Exception thrown when the Abstract already exists in database

public class AbstractException extends Exception{
    public AbstractException(String message){
        super(message);
    }
}
