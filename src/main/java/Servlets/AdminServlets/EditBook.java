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
import java.text.NumberFormat;

@WebServlet(name = "EditBook", value = "/EditBook")
public class EditBook extends HttpServlet {
    private DaoUser daoUser;
    private String currentISBN;
    private Book currentBook;

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
            if (userLogin.equals("admin")) {
                if (request.getParameter("EditButton") != null) {
                    currentISBN = request.getParameter("EditButton");
                    currentBook = daoUser.getBook(currentISBN);
                    request.setAttribute("CurrentBook", currentBook);
                    this.getServletContext().getRequestDispatcher("/EditBook.jsp").forward(request, response);
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
                if (currentISBN != null) {
                    currentBook = daoUser.getBook(currentISBN);
                    String OldISBN = currentBook.getIsbn();
                    Book ModifiedBook = new Book();

                    try {
                        //try setting Year
                        ModifiedBook.setYear(Integer.parseInt(request.getParameter("PublishYear")));
                    } catch (BeanException | NumberFormatException PublishYearError) {
                        //if year isn't between 1000 and 2022 send error message
                        request.setAttribute("PublishYearError", PublishYearError.getMessage());
                    }
                    try {
                        //try setting ISBN
                        ModifiedBook.setIsbn((request.getParameter("ISBN")));
                    } catch (BeanException ISBNFormatError) {
                        //if ISBN contains other characters than numbers send error
                        request.setAttribute("ISBNFormatError", ISBNFormatError.getMessage());
                    }
                    ModifiedBook.setTitle(request.getParameter("Title"));
                    ModifiedBook.setAuthors(request.getParameter("Authors"));
                    ModifiedBook.setAbstract_(request.getParameter("Abstract"));
                    ModifiedBook.setLanguage(request.getParameter("Language"));
                    try {
                        daoUser.editBook(ModifiedBook, OldISBN);
                        request.setAttribute("CurrentBook", ModifiedBook);
                        this.getServletContext().getRequestDispatcher("/BookContent.jsp").forward(request, response);
                        currentISBN = null;
                        currentBook = null;

                    } catch (DaoException DaoError) {
                        request.setAttribute("DaoError", DaoError.getMessage());
                        request.setAttribute("CurrentBook", currentBook);
                        this.getServletContext().getRequestDispatcher("/EditBook.jsp").forward(request, response);
                    } catch (IsbnException ISBNError) {
                        request.setAttribute("ISBNError", ISBNError.getMessage());
                        this.getServletContext().getRequestDispatcher("/NewBook.jsp").forward(request, response);
                        request.setAttribute("CurrentBook", currentBook);
                        this.getServletContext().getRequestDispatcher("/EditBook.jsp").forward(request, response);
                    } catch (AbstractException AbstractError) {
                        request.setAttribute("AbstractError", AbstractError.getMessage());
                        this.getServletContext().getRequestDispatcher("/NewBook.jsp").forward(request, response);
                        request.setAttribute("CurrentBook", currentBook);
                        this.getServletContext().getRequestDispatcher("/EditBook.jsp").forward(request, response);
                    }
                }
            }
                //if not throw Error
                else {
                    throw new ServletException("No session found u have to login first");
                }
            } catch(ServletException NoSessionError){
                //Catch error message and display on the login page
                request.setAttribute("NoSessionError", NoSessionError.getMessage());
                this.getServletContext().getRequestDispatcher("/index.jsp").forward(request, response);
            }
        }
    }



