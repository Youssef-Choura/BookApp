package Servlets.AdminServlets;

import DAO.DaoFactory;
import DAO.User.DaoUser;

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
        this.daoUser = daoFactory.getDaoUser();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            //Checking session
            if (request.getSession().getAttribute("login") != null) {
                //Checking Admin session
                String userLogin = (String) request.getSession().getAttribute("login");
                if (userLogin.equals("admin")) {
                    //Forwarding user's info list to UserList.jsp
                    request.setAttribute("Users", daoUser.getUsers());
                    this.getServletContext().getRequestDispatcher("/UsersList.jsp").forward(request, response);
                }
                //If it's not the admin throw SessionError
                else {
                    throw new ServletException("No session found u have to login first");
                }
            }
        } catch (
                ServletException NoSessionError) {
            //Catch error message and display it on the login page
            request.setAttribute("NoSessionError", NoSessionError.getMessage());
            this.getServletContext().getRequestDispatcher("/index.jsp").forward(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
