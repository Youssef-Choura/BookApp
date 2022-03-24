package Servlets.AdminServlets;

import Beans.BeanException;
import Beans.User;
import DAO.DaoFactory;
import DAO.DaoUser;
import DAO.Exceptions.DaoException;
import DAO.Exceptions.EmailException;
import DAO.Exceptions.LoginException;
import DAO.Exceptions.TelephoneException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "EditUser", value = "/EditUser")
public class EditUser extends HttpServlet {
    private DaoUser daoUser;
    private String currentLogin;
    private User currentUser;

    @Override
    public void init() throws ServletException {
        //Getting a DaoFactory instance
        DaoFactory daoFactory = DaoFactory.getInstance();
        //Getting an implementation instance
        this.daoUser = daoFactory.getUtilisateurDao();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            //Checking session
            String userLogin = (String) request.getSession().getAttribute("login");
            if (userLogin.equals("admin")) {
                if (request.getParameter("EditButton") != null) {
                    currentLogin = request.getParameter("EditButton");
                    currentUser = daoUser.getUser(currentLogin);
                    request.setAttribute("CurrentUser", currentUser);
                    this.getServletContext().getRequestDispatcher("/EditUser.jsp").forward(request, response);
                }
            }
            //if not throw Error
            else {
                throw new ServletException("No session found u have to login first");
            }
        } catch (ServletException NoSessionError) {
            //Catch error message and display on the login page
            request.setAttribute("NoSessionError", NoSessionError.getMessage());
            this.getServletContext().getRequestDispatcher("/index.jsp").forward(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            //Checking session
            String userLogin = (String) request.getSession().getAttribute("login");
            if (userLogin.equals("admin")) {
                if (currentLogin != null) {
                    currentUser = daoUser.getUser(currentLogin);
                    String OldLogin = currentUser.getLogin();
                    User ModifiedUser = new User();

                    ModifiedUser.setFirstName(request.getParameter("FirstName"));
                    ModifiedUser.setFamilyName(request.getParameter("FamilyName"));
                    ModifiedUser.setGrade(request.getParameter("Grade"));
                    ModifiedUser.setAddress(request.getParameter("Address"));
                    ModifiedUser.setGender(request.getParameter("Gender"));

                    try {
                        //try setting phone number
                        ModifiedUser.setTelephone(request.getParameter("Telephone"));
                    } catch (BeanException TelephoneFormatError) {
                        //if it's not tunisian
                        request.setAttribute("TelephoneFormatError", TelephoneFormatError.getMessage());
                    }
                    try {
                        //try setting login
                        ModifiedUser.setLogin(request.getParameter("Login"));
                    } catch (BeanException LoginError) {
                        //if length is superior to 10 send error message
                        request.setAttribute("LoginLengthError", LoginError.getMessage());
                    }
                    try {
                        //try setting email
                        ModifiedUser.setEmail(request.getParameter("Email"));
                    } catch (BeanException EmailError) {
                        //if regex doesn't match send error message
                        request.setAttribute("EmailFormatError", EmailError.getMessage());
                    }
                    try {
                        //try setting password
                        ModifiedUser.setPassword(request.getParameter("Password"));
                    } catch (BeanException PasswordError) {
                        //if length is inferior to 6 send error message
                        request.setAttribute("PasswordLengthError", PasswordError.getMessage());
                    }
                    try {
                        daoUser.editUser(ModifiedUser, OldLogin);
                        request.setAttribute("Users", daoUser.getUsers());
                        this.getServletContext().getRequestDispatcher("/UsersList.jsp").forward(request, response);
                    } catch (DaoException DaoException) {
                        request.setAttribute("CurrentUser", currentUser);
                        request.setAttribute("DaoException", DaoException.getMessage());
                        this.getServletContext().getRequestDispatcher("/EditUser.jsp").forward(request, response);
                    } catch (LoginException LoginError) {
                        request.setAttribute("CurrentUser", currentUser);
                        request.setAttribute("LoginError", LoginError.getMessage());
                        this.getServletContext().getRequestDispatcher("/EditUser.jsp").forward(request, response);
                    } catch (EmailException EmailError) {
                        request.setAttribute("CurrentUser", currentUser);
                        request.setAttribute("EmailError", EmailError.getMessage());
                        this.getServletContext().getRequestDispatcher("/EditUser.jsp").forward(request, response);
                    } catch (TelephoneException TelephoneError) {
                        request.setAttribute("CurrentUser", currentUser);
                        request.setAttribute("TelephoneError", TelephoneError.getMessage());
                        this.getServletContext().getRequestDispatcher("/EditUser.jsp").forward(request, response);
                    }
                }
            }
            //if not throw Error
            else {
                throw new ServletException("No session found u have to login first");
            }
        } catch (ServletException NoSessionError) {
            //Catch error message and display on the login page
            request.setAttribute("NoSessionError", NoSessionError.getMessage());
            this.getServletContext().getRequestDispatcher("/index.jsp").forward(request, response);
        }
    }
}
