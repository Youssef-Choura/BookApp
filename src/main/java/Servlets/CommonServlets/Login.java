package Servlets.CommonServlets;

import Beans.BeanException;
import Beans.User;
import DAO.DaoFactory;
import DAO.DaoUser;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;

@WebServlet(name = "Login", value = "/Login")
public class Login extends HttpServlet {
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
        this.getServletContext().getRequestDispatcher("/index.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //Creation of a new user to compare to database
        User user = new User();

        //Setting login
        try {
            //try setting login
            user.setLogin(request.getParameter("Username"));
        } catch (BeanException LoginError) {
            //if length is superior to 10 send error message
            request.setAttribute("LoginLengthError", LoginError.getMessage());
        }

        //Setting password
        try {
            //try setting password
            user.setPassword(request.getParameter("Password"));
        } catch (BeanException PasswordError) {
            //if length is inferior to 6 send error message
            request.setAttribute("PasswordLengthError", PasswordError.getMessage());
        }

        //Searching database for matching username and password
        //If the user is found redirect to Books home page
        try {
            //if it's an administrator
            if (daoUser.signIn(user) && user.getLogin().equals("admin")){
                //Start a new session
                HttpSession session = request.getSession();
                session.setAttribute("login",user.getLogin());
                //Adding cookie
                Cookie cookie =new Cookie("login", user.getLogin());
                cookie.setMaxAge(60*60*24);
                response.addCookie(cookie);
                //Dispatcher
                request.setAttribute("books",daoUser.getBooks());
                this.getServletContext().getRequestDispatcher("/Administrator.jsp").forward(request, response);
                //if it's a normal user
            }else if (daoUser.signIn(user)){
                //Start new session
                HttpSession session = request.getSession();
                session.setAttribute("login",user.getLogin());
                //Adding cookie
                Cookie cookie =new Cookie("login", user.getLogin());
                cookie.setMaxAge(60*60*24);
                response.addCookie(cookie);
                //Dispatcher
                request.setAttribute("books",daoUser.getBooks());
                this.getServletContext().getRequestDispatcher("/UserHomePage.jsp").forward(request, response);
            }
        } catch (BeanException LoginError) {
            //if not found  reload and display error message
            request.setAttribute("LoginInfoError", LoginError.getMessage());
            this.getServletContext().getRequestDispatcher("/index.jsp").forward(request, response);
        }
}
}

