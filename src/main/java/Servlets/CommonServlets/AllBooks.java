package Servlets.CommonServlets;

import DAO.DaoFactory;
import DAO.DaoUser;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "AllBooks", value = "/AllBooks")
public class AllBooks extends HttpServlet {
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
            String userLogin = (String) request.getSession().getAttribute("login");
            if (userLogin != null) {
                //If it's an administrator
                if (userLogin.equals("admin")) {
                    //Forwarding to Admin books page
                    request.setAttribute("books",daoUser.getBooks());
                    this.getServletContext().getRequestDispatcher("/Administrator.jsp").forward(request, response);
                    //If it's a normal user
                } else {
                    //Forwarding to user books page
                    request.setAttribute("books",daoUser.getBooks());
                    this.getServletContext().getRequestDispatcher("/UserHomePage.jsp").forward(request, response);
                }
            }
            //If it's not the admin throw SessionError
            else {
                throw new ServletException("No session found u have to login first");

            }
        } catch (ServletException NoSessionError) {
            //Catch error message and display it on the login page
            request.setAttribute("NoSessionError", NoSessionError.getMessage());
            this.getServletContext().getRequestDispatcher("/index.jsp").forward(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws
            ServletException, IOException {

    }
}
