package Servlets.AdminServlets;

import Beans.BeanException;
import Beans.Book;
import DAO.DaoFactory;
import DAO.DaoUser;
import DAO.Exceptions.AbstractException;
import DAO.Exceptions.DaoException;
import DAO.Exceptions.IsbnException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "NewBook", value = "/NewBook")
public class NewBook extends HttpServlet {
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
                if (request.getSession().getAttribute("login").equals("admin")) {
                    this.getServletContext().getRequestDispatcher("/NewBook.jsp").forward(request, response);
                } else {
                    this.getServletContext().getRequestDispatcher("/NewBook.jsp").forward(request, response);
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

        try {
            //Checking session
            String userLogin = (String) request.getSession().getAttribute("login");
            if (userLogin.equals("admin")) {
                //Creation of a new book with admin inputs
                Book book = new Book();

                try {
                    //try setting Year
                    book.setYear(Integer.parseInt(request.getParameter("PublishYear")));
                } catch (BeanException | NumberFormatException PublishYearError) {
                    //if year isn't between 1000 and 2022 send error message
                    request.setAttribute("PublishYearError", PublishYearError.getMessage());
                }

                try {
                    //try setting ISBN
                    book.setIsbn((request.getParameter("ISBN")));
                } catch (BeanException ISBNFormatError) {
                    //if ISBN contains other characters than numbers send error
                    request.setAttribute("ISBNFormatError", ISBNFormatError.getMessage());
                }
                //Setting other parameters
                book.setLanguage(request.getParameter("Language"));
                book.setAbstract_(request.getParameter("Abstract"));
                book.setAuthors(request.getParameter("Authors"));
                book.setTitle(request.getParameter("Title"));

                //Adding book to database

                try {
                    daoUser.addBook(book);
                    request.setAttribute("books", daoUser.getBooks());
                    this.getServletContext().getRequestDispatcher("/Administrator.jsp").forward(request, response);
                    //if not added reload and display errors
                } catch (IsbnException ISBNError) {
                    request.setAttribute("ISBNError", ISBNError.getMessage());
                    this.getServletContext().getRequestDispatcher("/NewBook.jsp").forward(request, response);
                } catch (AbstractException AbstractError) {
                    request.setAttribute("AbstractError", AbstractError.getMessage());
                    this.getServletContext().getRequestDispatcher("/NewBook.jsp").forward(request, response);
                } catch (DaoException DaoError) {
                    request.setAttribute("DaoError", DaoError.getMessage());
                    this.getServletContext().getRequestDispatcher("/NewBook.jsp").forward(request, response);
                }

            } else {
                throw new ServletException("No session found u have to login first");
            }
        } catch (ServletException NoSessionError) {
            //Catch error message and display on the login page
            request.setAttribute("NoSessionError", NoSessionError.getMessage());
            this.getServletContext().getRequestDispatcher("/index.jsp").forward(request, response);
        }
    }
}

