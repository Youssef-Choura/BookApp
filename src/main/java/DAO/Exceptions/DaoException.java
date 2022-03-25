package DAO.Exceptions;

// Exception thrown when the DAO can't communicate with database

public class DaoException extends Exception{
    public DaoException(String message){
        super(message);
    }
}