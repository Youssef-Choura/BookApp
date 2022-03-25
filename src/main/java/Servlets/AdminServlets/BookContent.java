package Servlets.AdminServlets;

import Beans.Book;
import DAO.Book.DaoBook;
import DAO.DaoFactory;
import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;

@WebServlet(name = "BookContent", value = "/BookContent")
public class BookContent extends HttpServlet {
    private DaoBook daoBook;

    @Override
    public void init() throws ServletException {
        //Getting a DaoFactory instance
        DaoFactory daoFactory = DaoFactory.getInstance();
        //Getting an implementation instance
        this.daoBook = daoFactory.getDaoBook();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            //Checking session
            if (request.getSession().getAttribute("login") != null) {
                String userLogin = (String) request.getSession().getAttribute("login");
                //Checking Admin session
                if (userLogin.equals("admin")) {
                    //Checking ISBN value
                    if (request.getParameter("ViewButton") != null) {
                        //Getting book info with the retrieved ISBN
                        Book book = daoBook.getBook(request.getParameter("ViewButton"));
                        //Forwarding book info to BookContent.jsp and showing it
                        request.setAttribute("CurrentBook", book);
                        this.getServletContext().getRequestDispatcher("/BookContent.jsp").forward(request, response);
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

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
