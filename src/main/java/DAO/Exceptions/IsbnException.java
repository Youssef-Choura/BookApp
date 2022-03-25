package DAO.Exceptions;

// Exception thrown when the ISBN already exists in database

public class IsbnException extends Exception{
    public IsbnException(String message){
        super(message);
    }
}
