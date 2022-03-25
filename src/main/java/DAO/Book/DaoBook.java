package DAO.Book;

import Beans.Book;
import Beans.User;
import DAO.Exceptions.AbstractException;
import DAO.Exceptions.DaoException;
import DAO.Exceptions.IsbnException;

import java.util.ArrayList;
import java.util.List;

public interface DaoBook {

    void MarkBook(Book book, User user) throws DaoException;
    void UnMarkBook(Book book,User user) throws DaoException;
    void addBook(Book book) throws IsbnException, AbstractException, DaoException;
    void deleteBook(Book book) throws DaoException;
    void editBook(Book book,String OldISBN) throws DaoException, IsbnException, AbstractException;
    ArrayList<String> getMarkedISBNS(User user);
    List<Book> getBooks();
    Book getBook(String ISBN);
}
