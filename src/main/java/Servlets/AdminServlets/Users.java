package Servlets.AdminServlets;

import DAO.DaoFactory;
import DAO.DaoUser;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "Users", value = "/Users")
public class Users extends HttpServlet {
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
        try {
            //Checking session
            //If there's a session show jsp page
            String userLogin = (String) request.getSession().getAttribute("login");
            if (userLogin.equals("admin")) {
                request.setAttribute("Users", daoUser.getUsers());
                this.getServletContext().getRequestDispatcher("/UsersList.jsp").forward(request, response);
            }
            //if not throw Error
            else {
                throw new ServletException("No session found u have to login first");

            }
        } catch (
                ServletException NoSessionError) {
            //Catch error message and display on the login page
            request.setAttribute("NoSessionError", NoSessionError.getMessage());
            this.getServletContext().getRequestDispatcher("/index.jsp").forward(request, response);
        }

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
