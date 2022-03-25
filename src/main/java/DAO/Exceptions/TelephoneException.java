package DAO.Exceptions;

// Exception thrown when the Phone Number already exists in database

public class TelephoneException extends Exception{
    public TelephoneException(String message){
        super(message);
    }
}
