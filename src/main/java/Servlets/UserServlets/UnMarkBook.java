package Servlets.UserServlets;

import Beans.Book;
import Beans.User;
import DAO.DaoFactory;
import DAO.DaoUser;
import DAO.Exceptions.DaoException;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.util.ArrayList;

@WebServlet(name = "UnMarkBook", value = "/UnMarkBook")
public class UnMarkBook extends HttpServlet {
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
                    //Getting book and user infos
                    Book book = daoUser.getBook(request.getParameter("MarkButton"));
                    User user = daoUser.getUser(userLogin);
                    try {
                        //Unmark Book
                        daoUser.UnMarkBook(book, user);
                    } catch (DaoException e) {
                        e.printStackTrace();
                    }
                    //Instantiating an arraylist to add marked books to
                    ArrayList<Book> books = new ArrayList<>();
                    //Getting books isbn
                    ArrayList<String> ISBNS = daoUser.getMarkedISBNS(user);
                    //Creating books from isbns and adding them to the marked books list
                    for (String isbn : ISBNS) {
                        Book NewBook = daoUser.getBook(isbn);
                        books.add(NewBook);
                    }
                    //Forwarding book list to UserMarkedBooks.jsp page
                    request.setAttribute("books", books);
                    this.getServletContext().getRequestDispatcher("/UserMarkedBooks.jsp").forward(request, response);
                }
            }
            //If there's no session throw SessionError
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
