/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package util;

import java.io.File;
import java.io.FileWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import Main.Main;
import dao.BookCopyDao;
import dao.BookDao;
import dao.IStoragePath;
import dao.SystemUserDAO;
import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.stage.FileChooser;
import model.Book;
import model.BookCopy;
import model.SystemUser;

/**
 *
 * @author mauro
 */
public class util {
    
    
    private static boolean writeToConsole = true;
    
    private static boolean writeToFile = false;

    public static boolean isWriteToFile() {
        return writeToFile;
    }

    public static void setWriteToFile(boolean _writeToFile) {
        writeToFile = _writeToFile;
    }

    public static boolean isWriteToConsole() {
        return writeToConsole;
    }

    public static void setWriteToConsole(boolean _writeToConsole) {
        writeToConsole = _writeToConsole;
    }
    
    public static void log(String msg) {
        
       if (isWriteToConsole()) {
           //System.out.println(callerClassName + ": "+ msg);
           System.out.println("System Library: " + msg);
       } 
       
       if (isWriteToFile()) {
           //Must write in a log file...
           //System.out.println(callerClassName + ": "+ msg);
           System.out.println(msg);
       }
        
    }

    public static void log(Object o, String msg) {
         String callerClassName = o.getClass().getName();
        
        
       if (isWriteToConsole()) {
           System.out.println("System Library("+ callerClassName + "): " + msg);
           System.out.println("System Library: " + msg);
       } 
       
       if (isWriteToFile()) {
           //Must write in a log file...
           //System.out.println(callerClassName + ": "+ msg);
           System.out.println(msg);
           //saveFileRoutine(file, msg);
       }
        
    }
    
    public static void setLogFile() {
        
        File logFile;
        
    }
    
    private static void saveFileRoutine(File file, String txt)
			throws Exception{
		
        try {

            file.createNewFile();
            FileWriter writer = new FileWriter(file);
            writer.write(txt);
            writer.close();
        } catch(Exception e) {
                            e.printStackTrace();                            
            }
	}
    
    public static void createInitialStorageDirs() {
        List<File> folderList = new ArrayList<File>();
        boolean success;
        
        
        folderList.add(new File(IStoragePath.OUTPUT_DIR + "bookCopy"));
        folderList.add(new File(IStoragePath.OUTPUT_DIR + "book"));
        folderList.add(new File(IStoragePath.OUTPUT_DIR + "checkoutEntry"));
        folderList.add(new File(IStoragePath.OUTPUT_DIR + "checkoutRecord"));
        folderList.add(new File(IStoragePath.OUTPUT_DIR + "member"));
        folderList.add(new File(IStoragePath.OUTPUT_DIR + "user"));
        
        
        for (File folder : folderList ) {
            if (!folder.exists()) {
                success = folder.mkdirs();
            }
        }
        
    }

    public static void createInitialDatabase() {
        SystemUser admin = new SystemUser();
        admin.setUsername("11");
        admin.setPassword("11");
        admin.setRole(SystemUser.AuthorizationLevel.ADMINISTRATOR);
        
        SystemUserDAO adminDAO = new SystemUserDAO();
        
        SystemUser librarian = new SystemUser();
        librarian.setUsername("lib");
        librarian.setPassword("lib");
        librarian.setRole(SystemUser.AuthorizationLevel.LIBRARIAN);
        
        SystemUserDAO librarianDAO = new SystemUserDAO();
        
        SystemUser rootUser = new SystemUser();
        rootUser.setUsername("both");
        rootUser.setPassword("both");
        rootUser.setRole(SystemUser.AuthorizationLevel.BOTH);
        
        SystemUserDAO rootDAO = new SystemUserDAO();
        
        
        /*Book book = new Book();
        book.setIsbn("1111111111111");
        book.setTitle("Computer Networks");
        BookDao bookDAO = new BookDao();

        BookCopy bookCopy = new BookCopy();
        bookCopy.setBook(book);
        bookCopy.setCopynumber("11111");
        BookCopyDao bookCopyDAO = new BookCopyDao();
        
        Book book2 = new Book();
        book2.setIsbn("2222222222222");
        book2.setTitle("Java for Dummies");
        BookDao bookDAO2 = new BookDao();
        BookCopy bookCopy2 = new BookCopy ();
        bookCopy2.setBook(book2);
        bookCopy2.setCopynumber("22222");
        BookCopyDao bookCopyDAO2 = new BookCopyDao();
        */
        
        try {
            adminDAO.addUser(admin);
            librarianDAO.addUser(librarian);
            rootDAO.addUser(rootUser);
            //bookDAO.addBook(book);
            //bookDAO2.addBook(book2);
            //bookCopyDAO.addBookCopy(bookCopy);
            //bookCopyDAO2.addBookCopy(bookCopy2);
            
        } catch (IOException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
    }


}
