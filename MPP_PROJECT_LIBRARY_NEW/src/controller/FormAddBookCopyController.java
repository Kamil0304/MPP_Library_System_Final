/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import dao.BookCopyDao;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import model.Book;
import model.BookCopy;
import util.FieldValidator;
import util.Message;

/**
 * FXML Controller class
 *
 * @author mauro
 */
public class FormAddBookCopyController extends SaveFormBaseController {

    @FXML
    private TextField isbnField;
    
    @FXML
    private TextField titleField;

    @FXML
    private TextField copyNumField;
    
    @FXML
    private Button addButton;
    
    BookCopyDao bookCopyDao = new BookCopyDao();
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        isbnField.setDisable(true);        
        titleField.setDisable(true);
    }
    
    
    @FXML
    private void btnAddBookCopyAction(ActionEvent event) {
        
        //Validation  
        if ( !validate() ) // if not valid don't continu, just return to the form
            return;   
        
        BookCopy copy = new BookCopy();
        copy.setCopynumber(copyNumField.getText());
        copy.setIsbn(isbnField.getText());
        Book book = new Book();
        book.setIsbn(isbnField.getText());
        copy.setBook(book);
        copy.setAvailable(true);
        try {
            bookCopyDao.addBookCopy(copy);
             Message.showSuccessMessage("Add Book Copy", "Book Copy Added Sucessfully to book", ""); 
        } catch (IOException ex) {
            Message.showErrorMessage("Add Book", "Saving Book Copy Failed. Exception message: ",  ex.getMessage());                      
        }

        //Stage stage = (Stage) addButton.getScene().getWindow();
            //stage.close();
        
    }

    public TextField getIsbnField() {
        return isbnField;
    }

    public TextField getTitleField() {
        return titleField;
    }

    @Override
    void validateAllFields() {
        //validate isbn
        if (!isValidCopyNumber()) {
            if (!invalidFields.contains("Copy Number")) {
                invalidFields.add("Copy Number");
                copyNumField.setStyle(INVALID_STYLE_BORDER);
            }
        } else {
            invalidFields.remove("Copy Number");
            copyNumField.setStyle(VALID_STYLE_BORDER);
        }
    }
    
    //Fields validation methods:
    
    //Copy Number: valid if non empty and isAlphaNumerice
    private boolean isValidCopyNumber(){
        String textValue = copyNumField.getText();
        if (!FieldValidator.isEmpty(textValue) && FieldValidator.isAlphaNumeric(textValue)) 
            return true;        
        return false;
    }


}
