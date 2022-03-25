package Servlets.AdminServlets;

import Beans.User;
import DAO.DaoFactory;
import DAO.User.DaoUser;
import DAO.Exceptions.DaoException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "DeleteUser", value = "/DeleteUser")
public class DeleteUser extends HttpServlet {
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

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            //Checking session
            if (request.getSession().getAttribute("login") != null) {
                //Checking Admin session
                String userLogin = (String) request.getSession().getAttribute("login");
                if (userLogin.equals("admin")) {
                    //Checking login value
                    if (request.getParameter("DeleteButton") != null) {
                        try {
                            //Getting the user to delete info
                            User user = daoUser.getUser((request.getParameter("DeleteButton")));
                            //Deleting user
                            daoUser.deleteUser(user);
                            //Forwarding the user list and refreshing the jsp page
                            request.setAttribute("Users", daoUser.getUsers());
                            this.getServletContext().getRequestDispatcher("/UsersList.jsp").forward(request, response);
                        } catch (DaoException e) {
                            e.printStackTrace();
                        }
                    }
                }
                //If it's not the admin throw SessionError
                else {
                    throw new ServletException("No session found u have to login first");
                }
            }
        } catch (ServletException NoSessionError) {
            //Catch error message and display it on the login page
            request.setAttribute("NoSessionError", NoSessionError.getMessage());
            this.getServletContext().getRequestDispatcher("/index.jsp").forward(request, response);
        }

    }
}

