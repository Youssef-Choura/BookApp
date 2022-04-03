package Servlets.AdminServlets;

import Beans.BeanException;
import Beans.Book;
import DAO.Book.DaoBook;
import DAO.DaoFactory;
import DAO.User.DaoUser;
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
                //Checking Admin session
                String userLogin = (String) request.getSession().getAttribute("login");
                if (userLogin.equals("admin")) {
                    //Redirect to UsersList.jsp page
                    response.sendRedirect("/BookApp/NewBook.jsp");
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
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        try {
            //Checking session
            String userLogin = (String) request.getSession().getAttribute("login");
            if (userLogin.equals("admin")) {
                //Instantiating a new book
                Book book = new Book();
                boolean Status = true;
                //Setting fields
                try {
                    //Setting Year
                    int year = Integer.parseInt(request.getParameter("PublishYear"));
                    book.setYear(year);
                } catch (BeanException | NumberFormatException PublishYearError) {
                    //If year isn't between 1000 and 2022 send error message
                    Status = false;
                    request.setAttribute("PublishYearError", PublishYearError.getMessage());
                }
                try {
                    //Setting ISBN
                    book.setIsbn((request.getParameter("ISBN")));
                } catch (BeanException ISBNFormatError) {
                    //if ISBN isn't composed of 10 or 13 numbers send error
                    Status = false;
                    request.setAttribute("ISBNFormatError", ISBNFormatError.getMessage());
                }
                //Setting other parameters
                book.setLanguage(request.getParameter("Language"));
                book.setAbstract_(request.getParameter("Abstract"));
                book.setAuthors(request.getParameter("Authors"));
                book.setTitle(request.getParameter("Title"));

                if (Status) {
                    try {
                        //Adding book to database
                        daoBook.addBook(book);
                        //Forwarding the new book list to the home page
                        request.setAttribute("books", daoBook.getBooks());
                        this.getServletContext().getRequestDispatcher("/Administrator.jsp").forward(request, response);
                        //Catching exceptions
                    } catch (IsbnException ISBNError) {
                        //If not added forward inputs and display errors
                        request.setAttribute("ISBNError", ISBNError.getMessage());
                        request.setAttribute("CurrentBook", book);
                        this.getServletContext().getRequestDispatcher("/NewBook.jsp").forward(request, response);
                    } catch (AbstractException AbstractError) {
                        request.setAttribute("AbstractError", AbstractError.getMessage());
                        request.setAttribute("CurrentBook", book);
                        this.getServletContext().getRequestDispatcher("/NewBook.jsp").forward(request, response);
                    } catch (DaoException DaoError) {
                        request.setAttribute("DaoError", DaoError.getMessage());
                        request.setAttribute("CurrentBook", book);
                        this.getServletContext().getRequestDispatcher("/NewBook.jsp").forward(request, response);
                    }
                }
                else {
                    request.setAttribute("CurrentBook", book);
                    this.getServletContext().getRequestDispatcher("/NewBook.jsp").forward(request, response);
                }
                    //If it's not the admin throw SessionError
                } else {
                    throw new ServletException("No session found u have to login first");
                }
            } catch(ServletException NoSessionError){
                //Catch error message and display it on the login page
                request.setAttribute("NoSessionError", NoSessionError.getMessage());
                this.getServletContext().getRequestDispatcher("/index.jsp").forward(request, response);
            }

    }
}

