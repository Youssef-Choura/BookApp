package Servlets.CommonServlets;

import Beans.BeanException;
import Beans.User;
import DAO.*;
import DAO.Exceptions.DaoException;
import DAO.Exceptions.EmailException;
import DAO.Exceptions.LoginException;
import DAO.Exceptions.TelephoneException;
import DAO.DaoUser;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.util.Objects;

@WebServlet(name = "Register", value = "/Register")
public class Register extends HttpServlet {
    private DaoUser daoUser;

    @Override
    public void init() throws ServletException {
        //Getting a DaoFactory instance
        DaoFactory daoFactory = DaoFactory.getInstance();
        //Getting an implementation instance
        this.daoUser = daoFactory.getUtilisateurDao();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.getServletContext().getRequestDispatcher("Registration.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //Creation of a new user to compare to database
        User user = new User();
        //Setting fields
        user.setFirstName(request.getParameter("FirstName"));
        user.setFamilyName(request.getParameter("FamilyName"));
        user.setGrade(request.getParameter("Grade"));
        user.setAddress(request.getParameter("Address"));
        user.setGender(request.getParameter("Gender"));
        //Setting login
        try {
            user.setLogin(request.getParameter("Username"));
        } catch (BeanException LoginError) {
            //If length is superior to 10 send error message
            request.setAttribute("LoginLengthError", LoginError.getMessage());
        }
        try {
            //Setting phone number
            user.setTelephone(request.getParameter("Telephone"));
        } catch (BeanException TelephoneFormatError) {
            //If it's not numerical send error message
            request.setAttribute("TelephoneFormatError", TelephoneFormatError.getMessage());
        }
        //Setting Email
        try {
            user.setEmail(request.getParameter("Email"));
        } catch (BeanException EmailError) {
            //If regex doesn't match send error message
            request.setAttribute("EmailFormatError", EmailError.getMessage());
        }
        //Password setting and Confirmation
        try {
            //Compare Passwords
            if (Objects.equals(request.getParameter("PasswordConfirmation"), request.getParameter("Password"))) {
                //If they match check password length
                try {
                    //Setting password
                    user.setPassword(request.getParameter("Password"));
                } catch (BeanException PasswordError) {
                    //If length is inferior to 6 send error message
                    request.setAttribute("PasswordLengthError", PasswordError.getMessage());
                }
            } else {
                //If they don't match throw an exception
                throw new BeanException("Password doesn't match");
            }
        } catch (BeanException PassConfirmationError) {
            //Reload displaying errors
            request.setAttribute("PassConfirmationError", PassConfirmationError.getMessage());
        }

        //Adding user to database

        try {
            //Add user
            daoUser.signUp(user);
            //Start a new session
            HttpSession session = request.getSession();
            session.setAttribute("login",user.getLogin());
            //Adding 1 day cookie
            Cookie cookie =new Cookie("login", user.getLogin());
            cookie.setMaxAge(60*60*24);
            response.addCookie(cookie);
            //Forwarding to User home page
            request.setAttribute("books",daoUser.getBooks());
            this.getServletContext().getRequestDispatcher("/UserHomePage.jsp").forward(request, response);
            //If user is not added reload and display errors
        } catch (DaoException UniqueError) {
            request.setAttribute("UniqueError", UniqueError.getMessage());
            request.setAttribute("CurrentUser",user);
            this.getServletContext().getRequestDispatcher("/Registration.jsp").forward(request, response);
        }
        catch (LoginException LoginError){
            request.setAttribute("LoginError", LoginError.getMessage());
            request.setAttribute("CurrentUser",user);
            this.getServletContext().getRequestDispatcher("/Registration.jsp").forward(request, response);
        }
        catch (EmailException EmailError){
            request.setAttribute("EmailError", EmailError.getMessage());
            request.setAttribute("CurrentUser",user);
            this.getServletContext().getRequestDispatcher("/Registration.jsp").forward(request, response);
        }
        catch (TelephoneException TelephoneError){
            request.setAttribute("TelephoneError", TelephoneError.getMessage());
            request.setAttribute("CurrentUser",user);
            this.getServletContext().getRequestDispatcher("/Registration.jsp").forward(request, response);
        }
    }
}

