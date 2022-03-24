package Servlets.UserServlets;

import Beans.Book;
import DAO.DaoFactory;
import DAO.DaoUser;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;

@WebServlet(name = "ViewBook", value = "/ViewBook")
public class ViewBook extends HttpServlet {
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
            if (request.getSession().getAttribute("login") != null) {
                //Checking ISBN value
                if (request.getParameter("ViewButton") != null) {
                    //if not null import book with the retrieved ISBN
                    Book book = daoUser.getBook(request.getParameter("ViewButton"));
                    request.setAttribute("CurrentBook", book);
                    this.getServletContext().getRequestDispatcher("/ViewBook.jsp").forward(request, response);
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

    }
}
