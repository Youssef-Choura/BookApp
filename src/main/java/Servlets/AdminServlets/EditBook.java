package Servlets.AdminServlets;

import Beans.BeanException;
import Beans.Book;
import DAO.Book.DaoBook;
import DAO.DaoFactory;
import DAO.Exceptions.AbstractException;
import DAO.Exceptions.DaoException;
import DAO.Exceptions.IsbnException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "EditBook", value = "/EditBook")
public class EditBook extends HttpServlet {
    private DaoBook daoBook;
    private String currentISBN;
    private Book currentBook;

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
                    //Checking ISBN to edit value
                    if (request.getParameter("EditButton") != null) {
                        //Getting currentISBN and currentBook info to display in the edit page
                        currentISBN = request.getParameter("EditButton");
                        currentBook = daoBook.getBook(currentISBN);
                        //Forwarding book info to EditBook.jsp page
                        request.setAttribute("CurrentBook", currentBook);
                        this.getServletContext().getRequestDispatcher("/EditBook.jsp").forward(request, response);
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

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            //Checking Admin session
            String userLogin = (String) request.getSession().getAttribute("login");
            if (userLogin.equals("admin")) {
                //Checking currentISBN and currentBook values
                if (currentISBN != null && currentBook != null) {
                    //Instantiating an empty book
                    Book ModifiedBook = new Book();
                    //Try Setting fields
                    try {
                        //try setting Year
                        int year = Integer.parseInt(request.getParameter("PublishYear"));
                        ModifiedBook.setYear(year);
                    } catch (BeanException | NumberFormatException PublishYearError) {
                        //If year isn't between 1000 and 2022 send error message
                        request.setAttribute("PublishYearError", PublishYearError.getMessage());
                    }


                    try {
                        //try setting ISBN
                        ModifiedBook.setIsbn((request.getParameter("ISBN")));
                    } catch (BeanException ISBNFormatError) {
                        //if ISBN isn't composed of 10 or 13 numbers send error
                        request.setAttribute("ISBNFormatError", ISBNFormatError.getMessage());
                    }
                    ModifiedBook.setTitle(request.getParameter("Title"));
                    ModifiedBook.setAuthors(request.getParameter("Authors"));
                    ModifiedBook.setAbstract_(request.getParameter("Abstract"));
                    ModifiedBook.setLanguage(request.getParameter("Language"));
                    try {
                        //Edit book
                        daoBook.editBook(ModifiedBook, currentBook.getIsbn());
                        //Forwarding the modified book info to the BookContent.jsp page
                        request.setAttribute("CurrentBook", ModifiedBook);
                        this.getServletContext().getRequestDispatcher("/BookContent.jsp").forward(request, response);
                        //resetting current isbn and book
                        currentISBN = null;
                        currentBook = null;
                    //Catching exceptions
                    } catch (DaoException DaoError) {
                        //Forwarding errors and currentBook info
                        request.setAttribute("DaoError", DaoError.getMessage());
                        request.setAttribute("CurrentBook", currentBook);
                        this.getServletContext().getRequestDispatcher("/EditBook.jsp").forward(request, response);

                    } catch (IsbnException ISBNError) {
                        request.setAttribute("ISBNError", ISBNError.getMessage());
                        request.setAttribute("CurrentBook", currentBook);
                        this.getServletContext().getRequestDispatcher("/EditBook.jsp").forward(request, response);

                    } catch (AbstractException AbstractError) {
                        request.setAttribute("AbstractError", AbstractError.getMessage());
                        request.setAttribute("CurrentBook", currentBook);
                        this.getServletContext().getRequestDispatcher("/EditBook.jsp").forward(request, response);
                    }
                }
            }
            //If it's not the admin throw SessionError
                else {
                    throw new ServletException("No session found u have to login first");
                }
            } catch(ServletException NoSessionError){
                //Catch error message and display it on the login page
                request.setAttribute("NoSessionError", NoSessionError.getMessage());
                this.getServletContext().getRequestDispatcher("/index.jsp").forward(request, response);
            }
        }
    }



