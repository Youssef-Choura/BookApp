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
                    //Getting book and user infos
                    Book book = daoUser.getBook(request.getParameter("MarkButton"));
                    User user = daoUser.getUser(userLogin);
                    //Instantiating an arraylist to add marked books to
                    ArrayList<Book> books = new ArrayList<>();
                    try {
                        //MarkBook
                        daoUser.MarkBook(book,user);
                        //Getting books isbn
                        ArrayList<String> ISBNS = daoUser.getMarkedISBNS(user);
                        for (String isbn : ISBNS) {
                            //Creating books from isbns and adding them to the marked books list
                            Book NewBook = daoUser.getBook(isbn);
                            books.add(NewBook);
                        }
                        //Forwarding book list to UserMarkedBooks.jsp page
                        request.setAttribute("books", books);
                        this.getServletContext().getRequestDispatcher("/UserMarkedBooks.jsp").forward(request, response);
                    } catch (DaoException e) {
                        e.printStackTrace();
                    }
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

