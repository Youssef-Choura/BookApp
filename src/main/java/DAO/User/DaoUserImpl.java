package DAO.User;

import Beans.BeanException;
import Beans.User;
import DAO.DaoFactory;
import DAO.Exceptions.*;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class DaoUserImpl implements DaoUser {
    private final DaoFactory daoFactory;

    /**
     * Class constructor specifying the daoFactory to create.
     */

    public DaoUserImpl(DaoFactory daoFactory) {
        this.daoFactory = daoFactory;
    }

    /*----------------------------------Users methods----------------------------------------*/

    /**
     * This method attempts to sign the use up with the given User argument.
     * It connects to the database, creates and executes a SQL generic dialect statement
     * to get all the users logins, emails and telephones and compared them with the provided
     * user ones.If they match it throws and exception, if not it creates a prepared statement
     * with the user info and executes it updating the database (adding the user to the database).
     *
     * @param user contains a parameter of type User containing the user's info
     *             The user argument must not be null
     * @throws DaoException       if there is a problem communicating with database
     * @throws LoginException     if the login already exists in database
     * @throws EmailException     if the Email already exists in database
     * @throws TelephoneException if the Telephone already exists in database
     */

    @Override
    public void signUp(User user) throws DaoException, LoginException, EmailException, TelephoneException {
        //Setting connection and preparing statement
        try (Connection connection = daoFactory.getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery("select login,email,telephone from t_users;")) {
            //Checking if the login email and telephone already exist in database
            while (resultSet.next()) {
                String login = resultSet.getString("login");
                String email = resultSet.getString("email");
                String telephone = resultSet.getString("telephone");
                //If they exist throw Exceptions
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
        //Setting connection and preparing a prepared statement
        try {
            connection = daoFactory.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement("INSERT into t_users(id_user,login,password,first_name,family_name,gender,grade,address,email,telephone) " +
                    "VALUES(?,?,?,?,?,?,?,?,?,?);");
            //Setting fields
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
            //Executing preparedStatement and committing
            preparedStatement.executeUpdate();
            connection.commit();
            //Catch SqlException
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
     * This method attempts to sign the user in with the login and password provided
     * in the user parameter returning true if the user exists in database and throwing an
     * exception if it does not exist
     *
     * @param user contains a parameter of type User containing the user's login and password
     *             The user argument must not be null
     * @return boolean : true if the user exists in database
     * @throws BeanException if the user is not found in the database
     */

    @Override
    public boolean signIn(User user) throws BeanException {
        //Setting connection and preparing statement
        try (Connection connection = daoFactory.getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery("select login,password from t_users;")) {

            while (resultSet.next()) {
                String login = resultSet.getString("Login");
                String password = resultSet.getString("Password");
                //If user login and password are found in database
                if (Objects.equals(user.getLogin(), login) && Objects.equals(user.getPassword(), password)) {
                    //Return true
                    return true;
                }
            }
            //If it can't undo catch Exception
        } catch (SQLException e) {
            e.printStackTrace();
        }
        //If fields can't be set, throw new DaoException
        throw new BeanException("Invalid Login or password");
    }

    /**
     * This method takes two parameters the user's new data and the old login user
     * and updates the user in the database with the new given data
     *
     * @param user        contains a parameter of type User containing the user's new info to update
     *                    The user argument must not be null
     * @param loginToEdit parameter of type String containing the user's login to edit
     * @throws DaoException       if there is a problem communicating with database
     * @throws EmailException     if the Email already exists in database
     * @throws LoginException     if the login already exists in database
     * @throws TelephoneException if the Telephone already exists in database
     */

    @Override
    public void editUser(User user, String loginToEdit) throws DaoException, EmailException, LoginException, TelephoneException {
        //Setting connection and preparing statement
        try (Connection connection = daoFactory.getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery("select login,email,telephone from t_users where not login = loginToEdit;")) {
            //Checking if the login email and telephone already exists in database
            while (resultSet.next()) {
                String login = resultSet.getString("login");
                String email = resultSet.getString("email");
                String telephone = resultSet.getString("telephone");
                //If they exist throw Exceptions
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
        //Setting connection and preparing a prepared statement
        try {
            connection = daoFactory.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement("UPDATE t_users SET login = ?,password = ?,first_name = ?,family_name = ?," +
                    "gender = ?,grade = ?,address = ?,email = ?,telephone = ? WHERE login = ?;");
            //Setting fields
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
            //Executing preparedStatement and committing
            preparedStatement.executeUpdate();
            connection.commit();
            //Catch SqlException
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
     * This method takes a user as a parameter and deletes it from database
     *
     * @param user contains a parameter of type User containing the user's info
     *             The user argument must not be null
     * @throws DaoException if there is a problem communicating with database
     */

    @Override
    public void deleteUser(User user) throws DaoException {
        //Setting connection and preparing statement
        try (Connection connection = daoFactory.getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery("select login from t_users;")) {
            //Checking if the login already exists in database
            while (resultSet.next()) {
                //If login matches
                String login = resultSet.getString("login");
                if (login.equals(user.getLogin())) {
                    try {
                        //Delete User
                        PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM t_users WHERE login=?;");
                        preparedStatement.setString(1, user.getLogin());
                        //Commit update
                        preparedStatement.executeUpdate();
                        connection.commit();

                    } catch (SQLException Error) {
                        try {
                            //Undo changes
                            connection.rollback();
                        }
                        //If it can't undo catch Exception
                        catch (SQLException ex) {
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

    /**
     * This method gets the user's login and compares it with database users
     * logins, if they match, it returns the user containing that specific login
     *
     * @param login1 contains the user's login
     *               the login argument should not be null
     * @return Returns a user of type User
     */

    @Override
    public User getUser(String login1) {
        User user = new User();
        //Setting connection and preparing statement
        try (Connection connection = daoFactory.getConnection();
             Statement statement = connection.createStatement();
             //Executing statement and getting results
             ResultSet resultSet = statement.executeQuery("select id_user,login,password,first_name,family_name,gender,grade,address,email,telephone from t_users ;")) {

            while (resultSet.next()) {
                //Get login from login column
                String login = resultSet.getString("login");
                //If it matches the given login
                if (login.equals(login1)) {
                    //Set a new user with the row column values
                    int userId = resultSet.getInt("id_user");
                    String password = resultSet.getString("password");
                    String firstName = resultSet.getString("first_name");
                    String familyName = resultSet.getString("family_name");
                    String gender = resultSet.getString("gender");
                    String grade = resultSet.getString("grade");
                    String address = resultSet.getString("address");
                    String email = resultSet.getString("email");
                    String telephone = resultSet.getString("telephone");
                    //If it's not the admin
                    if (!login.equals("admin")) {
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
            }
            //Catch Exception while setting fields or communicating with database
        } catch (SQLException | BeanException e) {
            e.printStackTrace();
        }
        return user;
    }

    /**
     * This method returns a List of element type user of all the database users
     *
     * @return a List of element type User
     */

    @Override
    public List<User> getUsers() {
        List<User> users = new ArrayList<>();
        //Setting connection and preparing statement
        try (Connection connection = daoFactory.getConnection();
             Statement statement = connection.createStatement();
             //Executing statement and getting results
             ResultSet resultSet = statement.executeQuery("select Login,Password,first_name,family_name,gender,grade,address,email,telephone from t_users;")) {

            while (resultSet.next()) {
                //Setting a new user from the resultSet values
                String login = resultSet.getString("Login");
                String password = resultSet.getString("Password");
                String firstName = resultSet.getString("first_name");
                String familyName = resultSet.getString("family_name");
                String gender = resultSet.getString("gender");
                String grade = resultSet.getString("grade");
                String address = resultSet.getString("address");
                String email = resultSet.getString("email");
                String telephone = resultSet.getString("telephone");
                //If it's not the admin
                if (!login.equals("admin")) {
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
                    //Adding user to the user list
                    users.add(user);
                }
            }
            //Catch Exception while setting fields or communicating with database
        } catch (SQLException | BeanException e) {
            e.printStackTrace();
        }
        return users;
    }
}
