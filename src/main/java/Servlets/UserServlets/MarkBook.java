package Servlets.UserServlets;

import Beans.Book;
import Beans.User;
import DAO.DaoFactory;
import DAO.DaoUser;
import DAO.Exceptions.DaoException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;

@WebServlet(name = "MarkBook", value = "/MarkBook")
public class MarkBook extends HttpServlet {
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
            //Checking session
            if (request.getSession().getAttribute("login") != null) {
                String userLogin = (String) request.getSession().getAttribute("login");
                if (request.getParameter("MarkButton") != null) {
                    Book book = daoUser.getBook(request.getParameter("MarkButton"));
                    User user = daoUser.getUser(userLogin);
                    ArrayList<Book> books = new ArrayList<>();
                    try {
                        daoUser.MarkBook(book,user);
                        ArrayList<String> ISBNS = daoUser.getMarkedISBNS(user);
                        for (String isbn : ISBNS) {
                            Book NewBook = daoUser.getBook(isbn);
                            books.add(NewBook);
                        }
                        request.setAttribute("books", books);
                        this.getServletContext().getRequestDispatcher("/UserMarkedBooks.jsp").forward(request, response);
                    } catch (DaoException e) {
                        e.printStackTrace();
                    }
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
}

