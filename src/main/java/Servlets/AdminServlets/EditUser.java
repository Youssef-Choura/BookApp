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
            //Checking Admin session
            String userLogin = (String) request.getSession().getAttribute("login");
            if (userLogin.equals("admin")) {
                //Checking login to edit value
                if (request.getParameter("EditButton") != null) {
                    //Setting currentLogin and currentUser and forwarding info to EditUser.jsp page
                    currentLogin = request.getParameter("EditButton");
                    currentUser = daoUser.getUser(currentLogin);
                    request.setAttribute("CurrentUser", currentUser);
                    this.getServletContext().getRequestDispatcher("/EditUser.jsp").forward(request, response);
                }
            }
            //If it's not the admin throw SessionError
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
            //Checking Admin session
            String userLogin = (String) request.getSession().getAttribute("login");
            if (userLogin.equals("admin")) {
                //Checking currentLogin and currentUser values
                if (currentLogin != null && currentUser != null) {
                    //Instantiating a new User
                    User ModifiedUser = new User();
                    //Setting the new user's fields
                    ModifiedUser.setFirstName(request.getParameter("FirstName"));
                    ModifiedUser.setFamilyName(request.getParameter("FamilyName"));
                    ModifiedUser.setGrade(request.getParameter("Grade"));
                    ModifiedUser.setAddress(request.getParameter("Address"));
                    ModifiedUser.setGender(request.getParameter("Gender"));

                    try {
                        //Setting phone number
                        ModifiedUser.setTelephone(request.getParameter("Telephone"));
                    } catch (BeanException TelephoneFormatError) {
                        //If it's not tunisian send error message
                        request.setAttribute("TelephoneFormatError", TelephoneFormatError.getMessage());
                    }
                    try {
                        //Setting login
                        ModifiedUser.setLogin(request.getParameter("Login"));
                    } catch (BeanException LoginError) {
                        //If length is superior to 10 send error message
                        request.setAttribute("LoginLengthError", LoginError.getMessage());
                    }
                    try {
                        //Setting email
                        ModifiedUser.setEmail(request.getParameter("Email"));
                    } catch (BeanException EmailError) {
                        //If regex doesn't match send error message
                        request.setAttribute("EmailFormatError", EmailError.getMessage());
                    }
                    try {
                        //Setting password
                        ModifiedUser.setPassword(request.getParameter("Password"));
                    } catch (BeanException PasswordError) {
                        //If length is inferior to 6 send error message
                        request.setAttribute("PasswordLengthError", PasswordError.getMessage());
                    }
                    try {
                        //Editing User
                        daoUser.editUser(ModifiedUser, currentUser.getLogin());
                        //Forwarding user list to UsersList.jsp
                        request.setAttribute("Users", daoUser.getUsers());
                        this.getServletContext().getRequestDispatcher("/UsersList.jsp").forward(request, response);
                        //Resetting values
                        currentLogin=null;
                        currentUser=null;
                    //Catching errors
                    } catch (DaoException DaoException) {
                        //Forward currentUser info and error messages
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
            //If it's not the admin throw SessionError
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
