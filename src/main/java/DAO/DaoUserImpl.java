package DAO;

import Beans.BeanException;
import Beans.Book;
import Beans.User;
import DAO.Exceptions.*;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class DaoUserImpl implements DaoUser {
    private final DaoFactory daoFactory;

    public DaoUserImpl(DaoFactory daoFactory) {
        this.daoFactory = daoFactory;
    }


    @Override
    public boolean signIn(User user) throws BeanException {
        try (Connection connection = daoFactory.getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery("select login,password from t_users;")) {

            while (resultSet.next()) {
                String login = resultSet.getString("Login");
                String password = resultSet.getString("Password");
                if (Objects.equals(user.getLogin(), login) && Objects.equals(user.getPassword(), password)) {
                    return true;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        throw new BeanException("Invalid Login or password");
    }

    @Override
    public void signUp(User user) throws DaoException, LoginException, EmailException, TelephoneException {
        try (Connection connection = daoFactory.getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery("select login,email,telephone from t_users;")) {

            while (resultSet.next()) {
                String login = resultSet.getString("login");
                String email = resultSet.getString("email");
                String telephone = resultSet.getString("telephone");

                if (Objects.equals(user.getEmail(), email)) {
                    throw new EmailException("Email already exists");
                }
                if (Objects.equals(user.getLogin(), login)) {
                    throw new LoginException("Login already exists");
                }
                if (Objects.equals(user.getTelephone(), telephone)) {
                    throw new TelephoneException("Phone number already exists");
                }
            }
        } catch (SQLException Error) {
            Error.printStackTrace();
        }
        Connection connection = null;
        try {
            connection = daoFactory.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement("INSERT into t_users(id_user,login,password,first_name,family_name,gender,grade,address,email,telephone) " +
                    "VALUES(?,?,?,?,?,?,?,?,?,?);");
            preparedStatement.setInt(1, user.getUserId());
            preparedStatement.setString(2, user.getLogin());
            preparedStatement.setString(3, user.getPassword());
            preparedStatement.setString(4, user.getFirstName());
            preparedStatement.setString(5, user.getFamilyName());
            preparedStatement.setString(6, user.getGender());
            preparedStatement.setString(7, user.getGrade());
            preparedStatement.setString(8, user.getAddress());
            preparedStatement.setString(9, user.getEmail());
            preparedStatement.setString(10, user.getTelephone());

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

    @Override
    public void deleteUser(User user) throws DaoException {
        try (Connection connection = daoFactory.getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery("select login from t_users;")) {

            while (resultSet.next()) {
                String login = resultSet.getString("login");
                if (login.equals(user.getLogin())) {
                    try {
                        PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM t_users WHERE login=?;");
                        preparedStatement.setString(1, user.getLogin());
                        preparedStatement.executeUpdate();
                        connection.commit();

                    } catch (SQLException Error) {
                        try {
                            connection.rollback();
                        } catch (SQLException ex) {
                            ex.printStackTrace();
                        }
                        throw new DaoException("DatabaseError!");
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void editUser(User user, String loginToEdit) throws DaoException,EmailException,LoginException,TelephoneException {
        try (Connection connection = daoFactory.getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery("select login,email,telephone from t_users where not login = loginToEdit;")) {

            while (resultSet.next()) {
                String login = resultSet.getString("login");
                String email = resultSet.getString("email");
                String telephone = resultSet.getString("telephone");

                if (Objects.equals(user.getEmail(), email)) {
                    throw new EmailException("Email already exists");
                }
                if (Objects.equals(user.getLogin(), login)) {
                    throw new LoginException("Login already exists");
                }
                if (Objects.equals(user.getTelephone(), telephone)) {
                    throw new TelephoneException("Phone number already exists");
                }
            }
        } catch (SQLException Error) {
            Error.printStackTrace();
        }
        Connection connection = null;
        try {
            connection = daoFactory.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement("UPDATE t_users SET login = ?,password = ?,first_name = ?,family_name = ?," +
                    "gender = ?,grade = ?,address = ?,email = ?,telephone = ? WHERE login = ?;");

            preparedStatement.setString(1, user.getLogin());
            preparedStatement.setString(2, user.getPassword());
            preparedStatement.setString(3, user.getFirstName());
            preparedStatement.setString(4, user.getFamilyName());
            preparedStatement.setString(5, user.getGender());
            preparedStatement.setString(6, user.getGrade());
            preparedStatement.setString(7, user.getAddress());
            preparedStatement.setString(8, user.getEmail());
            preparedStatement.setString(9, user.getTelephone());
            preparedStatement.setString(10, loginToEdit);
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

    @Override
    public List<User> getUsers() {
        List<User> users = new ArrayList<>();
        try (Connection connection = daoFactory.getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery("select Login,Password,first_name,family_name,gender,grade,address,email,telephone from t_users;")) {

            while (resultSet.next()) {
                String login = resultSet.getString("Login");
                String password = resultSet.getString("Password");
                String firstName = resultSet.getString("first_name");
                String familyName = resultSet.getString("family_name");
                String gender = resultSet.getString("gender");
                String grade = resultSet.getString("grade");
                String address = resultSet.getString("address");
                String email = resultSet.getString("email");
                String telephone = resultSet.getString("telephone");

                User user = new User();
                user.setLogin(login);
                user.setPassword(password);
                user.setFirstName(firstName);
                user.setFamilyName(familyName);
                user.setGender(gender);
                user.setGrade(grade);
                user.setAddress(address);
                user.setEmail(email);
                user.setTelephone(telephone);

                users.add(user);
            }
        } catch (SQLException | BeanException e) {
            e.printStackTrace();
        }
        return users;
    }

    @Override
    public void MarkBook(Book book, User user) throws DaoException {
        boolean found = false;
        try (Connection connection = daoFactory.getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery("select id_book,id_user from t_book_user;")) {

            while (resultSet.next()) {
                String isbn = resultSet.getString("id_book");
                int id = resultSet.getInt("id_user");
                if ((book.getIsbn().equals(isbn)) && user.getUserId() == id){
                    found = true;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        if (!found){
            Connection connection = null;
            try {
                connection = daoFactory.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO t_book_user(id_user,id_book)VALUES(?,?);");
                preparedStatement.setInt(1, user.getUserId());
                preparedStatement.setString(2, book.getIsbn());

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
                throw new DaoException("DatabaseError!");
            }
        }
    }

    @Override
    public void UnMarkBook(Book book, User user) throws DaoException {
        Connection connection = null;
        try {
            connection = daoFactory.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement("DELETE from t_book_user where id_book=?;");
            preparedStatement.setString(1, book.getIsbn());

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
            throw new DaoException("DatabaseError!");
        }
    }

    @Override
    public ArrayList<String> getMarkedISBNS(User user) {
        ArrayList<String> booksISBN = new ArrayList<>();
        try (Connection connection = daoFactory.getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery("select id_book,id_user from t_book_user;")) {

            while (resultSet.next()) {
                String isbn = resultSet.getString("id_book");
                int id = resultSet.getInt("id_user");
                if (user.getUserId() == id){
                    booksISBN.add(isbn);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return booksISBN;
    }

    @Override
    public void addBook(Book book) throws IsbnException, AbstractException, DaoException {
        try (Connection connection = daoFactory.getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery("SELECT isbn,abstract FROM t_books;")) {

            while (resultSet.next()) {
                String isbn = resultSet.getString("isbn");
                String abstract_ = resultSet.getString("abstract");

                if (Objects.equals(book.getIsbn(), isbn)) {
                    throw new IsbnException("Isbn already exists");
                }
                if (Objects.equals(book.getAbstract_(), abstract_)) {
                    throw new AbstractException("Abstract already exists");
                }
            }
        } catch (SQLException Error) {
            Error.printStackTrace();
        }
        Connection connection = null;
        try {
            connection = daoFactory.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO t_books(isbn,title,authors,\n" +
                    " lang,abstract,publishYear)VALUES(?,?,?,?,?,?);");
            preparedStatement.setString(1, book.getIsbn());
            preparedStatement.setString(2, book.getTitle());
            preparedStatement.setString(3, book.getAuthors());
            preparedStatement.setString(4, book.getLanguage());
            preparedStatement.setString(5, book.getAbstract_());
            //Pourquoi =0 ?
            if (book.getYear() == 0) {
                throw new SQLException("invalid year");
            } else {
                preparedStatement.setInt(6, book.getYear());
            }

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

    @Override
    public void deleteBook(Book book) throws DaoException {

        try (Connection connection = daoFactory.getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery("SELECT isbn FROM t_books;")) {

            while (resultSet.next()) {
                String isbn = resultSet.getString("isbn");
                if (Objects.equals(book.getIsbn(), isbn)) {
                    try {
                        PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM t_books WHERE isbn=?;");
                        preparedStatement.setString(1, book.getIsbn());
                        preparedStatement.executeUpdate();
                        connection.commit();

                    } catch (SQLException Error) {
                        try {
                            connection.rollback();
                        } catch (SQLException ex) {
                            ex.printStackTrace();
                        }
                        throw new DaoException("DatabaseError!");
                    }
                }
            }
        } catch (SQLException Error) {
            Error.printStackTrace();
        }
    }

    @Override
    public void editBook(Book book, String OldISBN) throws DaoException,IsbnException,AbstractException {
        try (Connection connection = daoFactory.getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery("SELECT isbn,abstract FROM t_books where not isbn = OldISBN;")) {

            while (resultSet.next()) {
                String isbn = resultSet.getString("isbn");
                String abstract_ = resultSet.getString("abstract");

                if (Objects.equals(book.getIsbn(), isbn)) {
                    throw new IsbnException("Isbn already exists");
                }
                if (Objects.equals(book.getAbstract_(), abstract_)) {
                    throw new AbstractException("Abstract already exists");
                }
            }
        } catch (SQLException Error) {
            Error.printStackTrace();
        }
        Connection connection=null;
        try {
            connection = daoFactory.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement("UPDATE t_books SET isbn = ?,title = ?," +
                    "authors = ?,lang = ?,abstract = ?,publishYear = ? WHERE isbn = ?;");
            preparedStatement.setString(1, book.getIsbn());
            preparedStatement.setString(2, book.getTitle());
            preparedStatement.setString(3, book.getAuthors());
            preparedStatement.setString(4, book.getLanguage());
            preparedStatement.setString(5, book.getAbstract_());
            if (book.getYear() == 0) {
                throw new SQLException("invalid year");
            } else {
                preparedStatement.setInt(6, book.getYear());
            }
            preparedStatement.setString(7, OldISBN);
            preparedStatement.executeUpdate();
            connection.commit();

        } catch (SQLException Error) {
            try {
                if (connection!=null){
                    connection.rollback();
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
            throw new DaoException("DatabaseError (Check Fields) !");
        }
    }


    @Override
    public List<Book> getBooks() {
        List<Book> books = new ArrayList<>();
        try (Connection connection = daoFactory.getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery("select title,authors,isbn,lang,abstract,publishYear from t_books;")) {

            while (resultSet.next()) {
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

                books.add(book);
            }
        } catch (SQLException | BeanException e) {
            e.printStackTrace();
        }
        return books;
    }

    @Override
    public User getUser(String login1) {
        User user = new User();
        try (Connection connection = daoFactory.getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery("select id_user,login,password,first_name,family_name,gender,grade,address,email,telephone from t_users ;")) {

            while (resultSet.next()) {
                String login = resultSet.getString("login");
                if (login.equals(login1)) {
                    int userId = resultSet.getInt("id_user");
                    String password = resultSet.getString("password");
                    String firstName = resultSet.getString("first_name");
                    String familyName = resultSet.getString("family_name");
                    String gender = resultSet.getString("gender");
                    String grade = resultSet.getString("grade");
                    String address = resultSet.getString("address");
                    String email = resultSet.getString("email");
                    String telephone = resultSet.getString("telephone");

                    user.setUserId(userId);
                    user.setLogin(login);
                    user.setPassword(password);
                    user.setFirstName(firstName);
                    user.setFamilyName(familyName);
                    user.setGender(gender);
                    user.setGrade(grade);
                    user.setAddress(address);
                    user.setEmail(email);
                    user.setTelephone(telephone);
                }
            }
        } catch (SQLException | BeanException e) {
            e.printStackTrace();
        }
        return user;
    }

    @Override
    public Book getBook(String BookISBN) {
        Book book = new Book();
        try (Connection connection = daoFactory.getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery("select title , authors , isbn , lang , abstract , publishYear from t_books ;")) {

            while (resultSet.next()) {
                String isbn = resultSet.getString("isbn");
                if (isbn.equals(BookISBN)) {
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
        } catch (SQLException | BeanException ex) {
            ex.printStackTrace();
        }
        return book;
    }
}
