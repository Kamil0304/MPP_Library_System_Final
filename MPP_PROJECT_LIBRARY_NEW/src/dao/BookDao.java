/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import java.io.IOException;
import java.util.List;
import model.Book;
import model.BookCopy;

/**
 *
 * @author 984959
 */
public class BookDao {
    
    static final String OUTPUT_DIR = IStoragePath.OUTPUT_DIR + "book"; 
    BookCopyDao bookCopyDAO = new BookCopyDao();
    
    //any exception happned it returned to the caller
    public void addBook(Book book) throws IOException{       
       //if the listCopies in book object is not null -> we should store each book copy in the folder bookCopy
       List<BookCopy> copies = book.getCopies(); 
       book.setCopies(null);
       DataAccessUtil.saveObject(OUTPUT_DIR, book.getIsbn(), book);       
       
    }
    
    //get all books 
    public List<Book> getAllBooks(){
        return DataAccessUtil.readAllObject(OUTPUT_DIR);
    }
    
    //if addBook implemented by save based on Isbn -> then method is ok 
    public Book getBookByIsbn(String isbn){
        return DataAccessUtil.readGenericObject(OUTPUT_DIR, isbn);
    }
    
}
