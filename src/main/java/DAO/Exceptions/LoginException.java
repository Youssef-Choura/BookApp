package DAO.Exceptions;

// Exception thrown when the Login already exists in database

public class LoginException extends Exception{
    public LoginException(String message){
        super(message);
    }
}
