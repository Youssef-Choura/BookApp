package Servlets.AdminServlets;

import Beans.BeanException;
import Beans.Book;
import DAO.DaoFactory;
import DAO.DaoUser;
import DAO.Exceptions.DaoException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "DeleteBook", value = "/DeleteBook")
public class DeleteBook extends HttpServlet {
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
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            //Checking Admin session
            String userLogin = (String) request.getSession().getAttribute("login");
            if (userLogin.equals("admin")) {
                //Checking ISBN value
                if (request.getParameter("DeleteButton") != null) {
                    try {
                        //Getting book info with the retrieved ISBN
                        Book book = daoUser.getBook (request.getParameter("DeleteButton"));
                        //Deleting book
                        daoUser.deleteBook(book);
                        //Forwarding the new book list and refreshing the jsp page
                        request.setAttribute("books", daoUser.getBooks());
                        this.getServletContext().getRequestDispatcher("/Administrator.jsp").forward(request, response);
                    } catch (DaoException e) {
                        e.printStackTrace();
                    }
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
}
