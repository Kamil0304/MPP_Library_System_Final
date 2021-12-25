/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import dao.BookDao;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import model.Book;
import model.BookCopy;
import util.FieldValidator;
import util.Message;

/**
 * FXML Controller class
 *
 * @author Huthayfa
 */
public class FormAddBookController extends SaveFormBaseController {

    @FXML
    private TextField txtISBN;//13 digits number e.g. 978-3-16-148410-5
    
    @FXML
    private TextField txtTitle;
    
    //TODO: Hanle Authors
    
    @FXML
    private ComboBox<Integer> cmbCheckoutLength;   
    
    @FXML
    private Button btnSave;
    
     
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {        
        cmbCheckoutLength.getItems().addAll(7, 21); //possible values of the comboBox
        cmbCheckoutLength.setValue(7);//set default
    }  
    
    @FXML
    private void btnSaveAction(ActionEvent event) {        

        //Validation  
        if ( !validate() ) // if not valid don't continu, just return to the form
            return;        
        
        //if The data is valid
        Book book = new Book();
        book.setIsbn(txtISBN.getText());
        book.setTitle(txtTitle.getText());
        //System.out.println("Continue Implement Save");
        
        List<BookCopy> copiesList = new ArrayList<>();   
        
        BookDao bookDao = new BookDao();       
        try {
            bookDao.addBook(book);
            Message.showSuccessMessage("Add Book", "Saving Book Sucess", "");            
        } catch (IOException ex) {
            Message.showErrorMessage("Add Book", "Saving Book Failed. Exception message: ",  ex.getMessage());          
            
        }    
    }
    
    //this method validate each field, and if it is not valid -> it is added to the list
    //called from Template method validate
    @Override
    void validateAllFields() {

        //validate isbn
        if (!isValidIsbn()) {
            if (!invalidFields.contains("ISBN")) {
                invalidFields.add("ISBN");
                txtISBN.setStyle(INVALID_STYLE_BORDER);
            }
        } else {
            invalidFields.remove("ISBN");
            txtISBN.setStyle(VALID_STYLE_BORDER);
        }
        
        //validate title
        if (!isValidTitle()) {
            if (!invalidFields.contains("Title")) {
                invalidFields.add("Title");
                txtTitle.setStyle(INVALID_STYLE_BORDER);
            }
        } else {
            invalidFields.remove("Title");
            txtTitle.setStyle(VALID_STYLE_BORDER);
        }

    }
       
    //Fields validation methods:
    
    //ISBN:
    private boolean isValidIsbn(){
        String textValue = txtISBN.getText();
        if (!FieldValidator.isEmpty(textValue) && FieldValidator.isNumericOnly(textValue) && textValue.length() == 13) 
            return true;        
        return false;
    }
    
    //Title, may contain numbers or other symbols (e.g. physics 141 & ..)
    private boolean isValidTitle(){        
        if (FieldValidator.isEmpty(txtTitle.getText())) 
            return false;        
        return true;
    }  
        
    }
