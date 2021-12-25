/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

//import com.sun.javafx.scene.control.skin.VirtualFlow;
import static dao.BookDao.OUTPUT_DIR;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import model.Book;
import model.BookCopy;

/**
 *
 * @author win7
 */
public class BookCopyDao {
    
    static final String OUTPUT_DIR = IStoragePath.OUTPUT_DIR + "bookCopy"; 
    

 public void addBookCopy(BookCopy copy) throws IOException{ 
     DataAccessUtil.saveObject(OUTPUT_DIR, copy.getCopynumber(), copy);
 }   

//get all books 
    public List<BookCopy> getAllBookCopies(){
        return DataAccessUtil.readAllObject(OUTPUT_DIR);
    }
    
     
    public BookCopy getBookCopyByCopyNumber(String copyNumber){
        return DataAccessUtil.readGenericObject(OUTPUT_DIR, copyNumber);
    }
    
    public BookCopy getBookCopyByBookIsbn(String isbn){
        return DataAccessUtil.readGenericObject(OUTPUT_DIR, isbn);
    }
    
    public List<BookCopy> getBookCopiesByISBN(String isbn){
        List<BookCopy> copies = DataAccessUtil.readAllObject(OUTPUT_DIR);
        List<BookCopy> bookCopies = new ArrayList<>();
        for(int i=0 ; i<copies.size() ; i++){
            if(copies.get(i).getIsbn().equals(isbn) && copies.get(i).isAvailable()){
                bookCopies.add(copies.get(i));
            }
        }
        return bookCopies;
    }
    
    
}
