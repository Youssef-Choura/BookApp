package DAO.User;

import Beans.BeanException;
import Beans.User;
import DAO.Exceptions.*;

import java.util.List;

public interface DaoUser {
    void signUp(User user) throws DaoException, LoginException, EmailException, TelephoneException;
    boolean signIn(User user) throws BeanException;
    void deleteUser(User user) throws DaoException;
    void editUser(User user,String loginToEdit) throws DaoException, EmailException, LoginException, TelephoneException;
    User getUser(String login1);
    List<User> getUsers();
}
