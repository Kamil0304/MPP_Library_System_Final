package model;

import java.io.Serializable;

public class BookCopy implements Serializable{

    private String copynumber;
    private boolean available;
    private Book book;
    private String isbn; //foreign key   

    public String getCopynumber() {
        return this.copynumber;
    }

    public void setCopynumber(String copynumber) {
        this.copynumber = copynumber;
    }

    public boolean isAvailable() {
        return this.available;
    }

    public void setAvailable(boolean available) {
        this.available = available;
    }
    
    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

}
