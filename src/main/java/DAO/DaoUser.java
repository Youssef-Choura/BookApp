package DAO;

import Beans.BeanException;
import Beans.Book;
import Beans.User;
import DAO.Exceptions.*;

import java.util.ArrayList;
import java.util.List;

public interface DaoUser {
    void signUp(User user) throws DaoException, LoginException, EmailException, TelephoneException;
    boolean signIn(User user) throws BeanException;
    void deleteUser(User user) throws DaoException;
    void editUser(User user,String loginToEdit) throws DaoException, EmailException, LoginException, TelephoneException;
    User getUser(String login1);
    List<User> getUsers();


    void MarkBook(Book book,User user) throws DaoException;
    void UnMarkBook(Book book,User user) throws DaoException;
    void addBook(Book book) throws IsbnException, AbstractException, DaoException;
    void deleteBook(Book book) throws DaoException;
    void editBook(Book book,String OldISBN) throws DaoException, IsbnException, AbstractException;
    ArrayList<String> getMarkedISBNS(User user);
    List<Book> getBooks();
    Book getBook(String ISBN);
}
