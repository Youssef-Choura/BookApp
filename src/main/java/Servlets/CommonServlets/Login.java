package Servlets.CommonServlets;

import Beans.BeanException;
import Beans.User;
import DAO.Book.DaoBook;
import DAO.DaoFactory;
import DAO.User.DaoUser;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;

@WebServlet(name = "Login", value = "/Login")
public class Login extends HttpServlet {
    private DaoUser daoUser;
    private DaoBook daoBook;

    @Override
    public void init() throws ServletException {
        //Getting a DaoFactory instance
        DaoFactory daoFactory = DaoFactory.getInstance();
        //Getting an implementation instance
        this.daoUser = daoFactory.getDaoUser();
        this.daoBook = daoFactory.getDaoBook();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.getServletContext().getRequestDispatcher("/index.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //Creation of a new user to compare to database
        User user = new User();

        //Setting login
        try {
            user.setLogin(request.getParameter("Username"));
        } catch (BeanException LoginError) {
            //If length is superior to 10 send error message
            request.setAttribute("LoginLengthError", LoginError.getMessage());
        }

        //Setting password
        try {
            user.setPassword(request.getParameter("Password"));
        } catch (BeanException PasswordError) {
            //If length is inferior to 6 send error message
            request.setAttribute("PasswordLengthError", PasswordError.getMessage());
        }

        try {
            //Sign in user
            if (daoUser.signIn(user) && user.getLogin().equals("admin")){
                //If it's an administrator
                //Start a new session
                HttpSession session = request.getSession();
                session.setAttribute("login",user.getLogin());
                //Adding 1 month cookie
                Cookie cookie =new Cookie("login", user.getLogin());
                cookie.setMaxAge(60*60*24*30);
                response.addCookie(cookie);
                //Forwarding to Admin home page
                request.setAttribute("books",daoBook.getBooks());
                this.getServletContext().getRequestDispatcher("/Administrator.jsp").forward(request, response);
                //if it's a normal user
            }else if (daoUser.signIn(user)){
                //Start new session
                HttpSession session = request.getSession();
                session.setAttribute("login",user.getLogin());
                //Adding 1 day cookie
                Cookie cookie =new Cookie("login", user.getLogin());
                cookie.setMaxAge(60*60*24);
                response.addCookie(cookie);
                //Forwarding to User home page
                request.setAttribute("books",daoBook.getBooks());
                this.getServletContext().getRequestDispatcher("/UserHomePage.jsp").forward(request, response);
            }
        } catch (BeanException LoginError) {
            //If infos are not found in the database reload and display error message
            request.setAttribute("LoginInfoError", LoginError.getMessage());
            this.getServletContext().getRequestDispatcher("/index.jsp").forward(request, response);
        }
}
}

