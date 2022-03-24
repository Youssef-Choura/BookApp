package Beans;

import java.util.regex.Pattern;

public class Book {
    private String isbn;
    private String title;
    private String authors;
    private String language;
    private int year;
    private String abstract_;

    public Book() {
    }

    public Book(String isbn, String title, String authors, String language, int year, String abstract_) {
        this.isbn = isbn;
        this.title = title;
        this.authors = authors;
        this.language = language;
        this.year = year;
        this.abstract_ = abstract_;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) throws BeanException {
        //Checking ISBN validity
        Pattern pattern = Pattern.compile("\\d+");
        if (pattern.matcher(isbn).matches() && (isbn.length() == 10 || isbn.length() == 13)){
            this.isbn=isbn;
        }
        else {
            throw new BeanException("ISBN can contain only 10 or 13 numbers");
        }
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
