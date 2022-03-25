package Beans;

// Exception thrown when setting a Bean's field

public class BeanException extends Exception {
    public BeanException(String message) {
        super(message);
    }
}