package DAO.Book;

import Beans.BeanException;
import Beans.Book;
import Beans.User;
import DAO.DaoFactory;
import DAO.Exceptions.AbstractException;
import DAO.Exceptions.DaoException;
import DAO.Exceptions.IsbnException;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class DaoBookImpl implements DaoBook{
    private final DaoFactory daoFactory;

    public DaoBookImpl(DaoFactory daoFactory) {
        this.daoFactory = daoFactory;
    }

    /*----------------------------Books methods------------------------------------------*/

    /**
     * This method takes a book as a parameter and adds it to the database
     * @param book  parameter of type Book containing the book to add
     * @throws IsbnException        if the isbn already exists in database
     * @throws AbstractException    if the abstract already exists in database
     * @throws DaoException      if there is a problem communicating with database
     */

    @Override
    public void addBook(Book book) throws IsbnException, AbstractException, DaoException {
        //Setting connection and preparing statement
        try (Connection connection = daoFactory.getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery("SELECT isbn,abstract FROM t_books;")) {
            //Checking if the isbn abstract already exist in database
            while (resultSet.next()) {
                //If they exist throw exceptions
                String isbn = resultSet.getString("isbn");
                String abstract_ = resultSet.getString("abstract");

                if (Objects.equals(book.getIsbn(), isbn)) {
                    throw new IsbnException("Isbn already exists");
                }
                if (book.getAbstract_().trim().equals(abstract_)) {
                    throw new AbstractException("Abstract already exists");
                }
            }
        } catch (SQLException Error) {
            Error.printStackTrace();
        }
        Connection connection = null;
        //Setting connection and preparing a prepared statement
        try {
            connection = daoFactory.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO t_books(isbn,title,authors,\n" +
                    " lang,abstract,publishYear)VALUES(?,?,?,?,?,?);");
            preparedStatement.setString(1, book.getIsbn());
            preparedStatement.setString(2, book.getTitle());
            preparedStatement.setString(3, book.getAuthors());
            preparedStatement.setString(4, book.getLanguage());
            preparedStatement.setString(5, book.getAbstract_());
            //If an exception is thrown
            if (book.getYear() == 0){
                throw new SQLException("Invalid year");
            }else {
                preparedStatement.setInt(6, book.getYear());
            }
            //Execute preparedStatement and commit changes
            preparedStatement.executeUpdate();
            connection.commit();

        } catch (SQLException Error) {
            try {
                if (connection != null) {
                    connection.rollback();
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
            throw new DaoException("DatabaseError (Check Fields) !");
        }

    }

    /**
     * This method takes two parameters a book and a user and marks
     * the book to the user and adds to database both book and user's id
     * @param book  parameter of type Book containing the book to mark
     * @param user contains a parameter of type User containing the user's info
     *             The user argument must not be null
     * @throws DaoException if there is a problem communicating with database
     */

    @Override
    public void MarkBook(Book book, User user) throws DaoException {
        //Setting connection and preparing statement
        boolean found = false;
        try (Connection connection = daoFactory.getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery("select id_book,id_user from t_book_user;")) {
            //Checking if the Book is already marked to the user
            while (resultSet.next()) {
                String isbn = resultSet.getString("id_book");
                int id = resultSet.getInt("id_user");
                if ((book.getIsbn().equals(isbn)) && user.getUserId() == id) {
                    found = true;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        //If it's not found in the database add book id and user id
        if (!found) {
            Connection connection = null;
            //Setting connection and preparing statement
            try {
                connection = daoFactory.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO t_book_user(id_user,id_book)VALUES(?,?);");
                preparedStatement.setInt(1, user.getUserId());
                preparedStatement.setString(2, book.getIsbn());
                //Executing prepared statement and committing
                preparedStatement.executeUpdate();
                connection.commit();

            } catch (SQLException Error) {
                //If caught check connection status
                try {
                    //If it's not null undo changes
                    if (connection != null) {
                        connection.rollback();
                    }
                    //If it can't undo catch Exception
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
                //If fields can't be set, throw new DaoException
                throw new DaoException("DatabaseError (Check Fields) !");
            }
        }
    }

    /**
     * This method takes two parameters a book and a user and
     * unmark the book from the user removing both book and user id from the database
     * @param book  parameter of type Book containing the book to unmark
     * @param user contains a parameter of type User containing the user's info
     *             The user argument must not be null
     * @throws DaoException if there is a problem communicating with database
     */

    @Override
    public void UnMarkBook(Book book, User user) throws DaoException {
        //Setting connection and preparing statement
        Connection connection = null;
        //Removing ids from database
        try {
            connection = daoFactory.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement("DELETE from t_book_user where id_book=?;");
            preparedStatement.setString(1, book.getIsbn());
            //Executing prepared statement and committing
            preparedStatement.executeUpdate();
            connection.commit();

        } catch (SQLException Error) {
            //If caught check connection status
            try {
                //If it's not null undo changes
                if (connection != null) {
                    connection.rollback();
                }
                //If it can't undo catch Exception
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
            //If fields can't be set, throw new DaoException
            throw new DaoException("DatabaseError (Check Fields) !");
        }
    }

    /**
     * This method takes a user and returns all the marked book's isbns
     * @param user contains a parameter of type User containing the user's data
     *             The user argument must not be null
     * @return  Returns a list of element type String containing all books isbns
     */

    @Override
    public ArrayList<String> getMarkedISBNS(User user) {
        //Setting connection and preparing statement
        ArrayList<String> booksISBN = new ArrayList<>();
        try (Connection connection = daoFactory.getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery("select id_book,id_user from t_book_user;")) {

            while (resultSet.next()) {
                String isbn = resultSet.getString("id_book");
                int id = resultSet.getInt("id_user");
                //If it's the given user id
                if (user.getUserId() == id) {
                    //Add isbn to the isbn list
                    booksISBN.add(isbn);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return booksISBN;
    }

    /**
     * This method takes two parameters book and oldISBN. The old book is found by searching
     * for the old isbn in the database and will be replaced with the new book's data given
     * @param book  parameter of type Book containing all the new book's data
     * @param OldISBN   parameter of type String containing the book's old isbn to edit
     * @throws DaoException         if there is a problem communicating with database
     * @throws IsbnException        if the isbn already exists in database
     * @throws AbstractException    if the abstract already exists in database
     */

    @Override
    public void editBook(Book book, String OldISBN) throws DaoException, IsbnException, AbstractException {
        //Setting connection and preparing statement
        try (Connection connection = daoFactory.getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery("SELECT isbn,abstract FROM t_books;")) {

            while (resultSet.next()) {
                String isbn = resultSet.getString("isbn");
                String abstract_ = resultSet.getString("abstract");
                //If the new book's isbn and abstract already exist throw exceptions
                if (!isbn.equals(OldISBN)){
                    if (book.getIsbn().equals(isbn)) {
                        throw new IsbnException("Isbn already exists");
                    }
                    if (book.getAbstract_().trim().equals(abstract_.trim())) {
                        throw new AbstractException("Abstract already exists");
                    }
                }
            }
        } catch (SQLException Error) {
            Error.printStackTrace();
        }
        //Setting connection and preparing statement
        Connection connection = null;
        try {
            connection = daoFactory.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement("UPDATE t_books SET isbn = ?,title = ?," +
                    "authors = ?,lang = ?,abstract = ?,publishYear = ? WHERE isbn = ?;");
            preparedStatement.setString(1, book.getIsbn());
            preparedStatement.setString(2, book.getTitle());
            preparedStatement.setString(3, book.getAuthors());
            preparedStatement.setString(4, book.getLanguage());
            preparedStatement.setString(5, book.getAbstract_());
            //If an exception is thrown
            if (book.getYear() == 0){
                throw new SQLException("Invalid year");
            }else {
                preparedStatement.setInt(6, book.getYear());
            }
            preparedStatement.setString(7, OldISBN);
            //Executing prepared statement and committing changes
            preparedStatement.executeUpdate();
            connection.commit();

        } catch (SQLException Error) {
            //If caught check connection status
            try {
                //If it's not null undo changes
                if (connection != null) {
                    connection.rollback();
                }
                //If it can't undo catch Exception
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
            //If fields can't be set, throw new DaoException
            throw new DaoException("DatabaseError (Check Fields) !");
        }
    }

    /**
     * @param book  parameter of type Book containing the book to delete
     * @throws DaoException if there is a problem communicating with database
     */

    @Override
    public void deleteBook(Book book) throws DaoException {
        //Setting connection and preparing statement
        try (Connection connection = daoFactory.getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery("SELECT isbn FROM t_books;")) {
            //Finding book
            while (resultSet.next()) {
                String isbn = resultSet.getString("isbn");
                if (Objects.equals(book.getIsbn(), isbn)) {
                    //If found delete book
                    try {
                        PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM t_books WHERE isbn=?;");
                        preparedStatement.setString(1, book.getIsbn());
                        //Execute statement and commit changes
                        preparedStatement.executeUpdate();
                        connection.commit();

                    } catch (SQLException Error) {
                        //Undo changes
                        try {
                            connection.rollback();
                            //Catch exception if not
                        } catch (SQLException ex) {
                            ex.printStackTrace();
                        }
                        //If communication isn't possible with database throw exception
                        throw new DaoException("DatabaseError!");
                    }
                }
            }
        } catch (SQLException Error) {
            Error.printStackTrace();
        }
    }

    /**
     * This method gets the book's isbn and compares it with database books
     * isbns, if they match, it returns the book containing that specific isbn
     *
     * @param BookISBN argument of type String containing the book isbn
     *                 this isbn argument should not be null
     * @return Returns a book of type Book
     */

    @Override
    public Book getBook(String BookISBN) {
        Book book = new Book();
        //Setting connection and preparing statement
        try (Connection connection = daoFactory.getConnection();
             Statement statement = connection.createStatement();
             //Executing statement and getting results
             ResultSet resultSet = statement.executeQuery("select title , authors , isbn , lang , abstract , publishYear from t_books ;")) {

            while (resultSet.next()) {
                //Get isbn from isbn column
                String isbn = resultSet.getString("isbn");
                //If it matches the given isbn
                if (isbn.equals(BookISBN)) {
                    //Set a new book with the row column values
                    String title = resultSet.getString("title");
                    String authors = resultSet.getString("authors");
                    String lang = resultSet.getString("lang");
                    String abstract_ = resultSet.getString("abstract");
                    int publishYear = resultSet.getInt("publishYear");

                    book.setIsbn(isbn);
                    book.setTitle(title);
                    book.setAuthors(authors);
                    book.setLanguage(lang);
                    book.setAbstract_(abstract_);
                    book.setYear(publishYear);
                }
            }
            //Catch Exception while setting fields or communicating with database
        } catch (SQLException | BeanException ex) {
            ex.printStackTrace();
        }
        return book;
    }

    /**
     * This method returns a book list of all the database books
     *
     * @return Returns a List of element type Book
     */

    @Override
    public List<Book> getBooks() {
        List<Book> books = new ArrayList<>();
        //Setting connection and preparing statement
        try (Connection connection = daoFactory.getConnection();
             Statement statement = connection.createStatement();
             //Executing statement and getting results
             ResultSet resultSet = statement.executeQuery("select title,authors,isbn,lang,abstract,publishYear from t_books;")) {

            while (resultSet.next()) {
                //Setting a new book from the resultSet values
                String isbn = resultSet.getString("isbn");
                String title = resultSet.getString("title");
                String authors = resultSet.getString("authors");
                String lang = resultSet.getString("lang");
                String abstract_ = resultSet.getString("abstract");
                int publishYear = resultSet.getInt("publishYear");

                Book book = new Book();
                book.setIsbn(isbn);
                book.setTitle(title);
                book.setAuthors(authors);
                book.setLanguage(lang);
                book.setAbstract_(abstract_);
                book.setYear(publishYear);
                //Adding book to the book list
                books.add(book);
            }
            //Catch Exception while setting fields or communicating with database
        } catch (SQLException | BeanException e) {
            e.printStackTrace();
        }
        return books;
    }




}
