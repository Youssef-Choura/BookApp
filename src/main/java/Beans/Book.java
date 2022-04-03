package Beans;

import java.io.Serializable;
import java.util.regex.Pattern;

public class Book implements Serializable {
    private String isbn;
    private String title;
    private String authors;
    private String language;
    private int year;
    private String abstract_;

    public Book() {
    }

    public String getIsbn() {
        return isbn;
    }
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthors() {
        return authors;
    }

    public void setAuthors(String authors) {
        this.authors = authors;
    }

    public void setIsbn(String isbn) throws BeanException {
        //Checking ISBN validity (10 or 13 numbers only)
        Pattern pattern = Pattern.compile("^([0-9]{10}|[0-9]{13})$");
        if (pattern.matcher(isbn).matches() && (isbn.length() == 10 || isbn.length() == 13)){
            this.isbn=isbn;
        }
        else {
            throw new BeanException("ISBN can contain only 10 or 13 numbers");
        }
    }


    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) throws BeanException{
        if (year > 2022 || year < 1000){
            throw new BeanException("not a valid year");
        }
        else{
            this.year = year;
        }
    }

    public String getAbstract_() {
        return abstract_;
    }

    public void setAbstract_(String abstract_) {
        this.abstract_ = abstract_;
    }
}
