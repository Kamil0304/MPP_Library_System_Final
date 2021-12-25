/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import dao.BookCopyDao;
import dao.BookDao;
import dao.CheckoutEntryDAO;
import dao.CheckoutRecordDAO;
import dao.MemberDAO;
import java.io.File;
import java.io.FileWriter;
import java.net.URL;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import model.Book;
import model.BookCopy;
import model.CheckoutEntry;
import model.CheckoutRecord;
import model.LibraryMember;
import util.FieldValidator;
import util.Message;
import util.util;
import java.time.LocalDate;

/**
 * FXML Controller class
 *
 * @author mauro
 */
public class FormCheckoutController extends SaveFormBaseController {

    @FXML
    private Text firstName;//set when member ID selected    
    
    @FXML
    private Text lastName;
    
    private LocalDate local;
    
    @FXML
    private Button exportButton;

    @FXML
    private ChoiceBox<String> boxBook;

    @FXML
    private ComboBox<String> member;
    
    @FXML
    private DatePicker dueDate;

    MemberDAO memberDAO = new MemberDAO();

    BookDao bookDAO = new BookDao();

    CheckoutRecordDAO checkoutRecordDAO = new CheckoutRecordDAO();

    CheckoutEntryDAO checkoutEntryDAO = new CheckoutEntryDAO();
    
    BookCopyDao bookCopyDao = new BookCopyDao();    
   
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {        
        //Initialize members combobox
        List<LibraryMember> members = memberDAO.getAllLibraryMembers();
        List<String> member_ids = new ArrayList<>();
        for (LibraryMember member : members) {
            member_ids.add(member.getId());
        }
        member.setItems(FXCollections.observableArrayList(member_ids));
        
        //set onchange action of comboBox
        member.valueProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue ov, String oldValue, String newValue) {              
                //when selection changed -> fill the fields first name and last name
                LibraryMember selectedMember = memberDAO.getLibraryMember(newValue);
                firstName.setText("First Name: " + selectedMember.getFirstName());
                lastName.setText("Last Name: " + selectedMember.getLastName());
            }
        });

        List<Book> books = bookDAO.getAllBooks();
        List<String> book_titles = new ArrayList<>();
        List<String> book_isbns = new ArrayList<>();
        for (Book book : books) {
            List<BookCopy> copies = bookCopyDao.getBookCopiesByISBN(book.getIsbn());
            if(copies.isEmpty())continue;
            book_titles.add(book.getTitle());
            book_isbns.add(book.getIsbn());
        }
        boxBook.setItems(FXCollections.observableArrayList(book_isbns));
        //boxBook.setItems(FXCollections.observableArrayList(book_titles));
    }

    @FXML
    public void handleSubmitAction() throws Exception{
        //Fields Validation        
        if ( !validate() ) // if not valid don't continu, just return to the form
            return; 
        
        CheckoutRecord checkoutRecord = new CheckoutRecord();
        LibraryMember libraryMember = new LibraryMember();
        libraryMember.setId(member.getValue().toString());
        CheckoutEntry checkoutEntry = new CheckoutEntry();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMM dd, yyyy");
        local = LocalDate.now();
        checkoutEntry.setCheckoutDate(formatter.format(local));
        checkoutEntry.setDueDate(formatter.format(dueDate.getValue()));
        checkoutRecord.setLibraryMember(libraryMember);
        BookCopy bookCopy = new BookCopy();
        List<BookCopy> copies = bookCopyDao.getBookCopiesByISBN(boxBook.getValue().toString());
        if(!copies.isEmpty()){//new
            bookCopy.setAvailable(false);
            bookCopy.setIsbn(copies.get(0).getIsbn());
            bookCopy.setCopynumber(copies.get(0).getCopynumber());
            Book book = new Book();
            book.setIsbn(boxBook.getValue().toString());
            bookCopy.setBook(book);
            bookCopyDao.addBookCopy(bookCopy);
        }
        checkoutEntry.setBookCopy(bookCopy);
        
        try {
            String checkoutRecordID = checkoutRecordDAO.addCheckoutRecord(checkoutRecord);
            checkoutRecord.setId(checkoutRecordID);
            checkoutEntry.setCheckoutRecord(checkoutRecord);
            checkoutEntryDAO.addCheckoutEntry(checkoutEntry);
            Message.showSuccessMessage("Add Checkout Record", "Saving Checkout Record Sucess", "");            
        } catch (Exception e) {
           Message.showErrorMessage("Add Checkout Record", "Saving Checkout Record Failed. Exception message: ",  e.getMessage());  
        }
    }

    @FXML
    public void handleExportButtonAction(ActionEvent event) {

        Stage stage = new Stage();

        util.log("Exporting data to file .csv...");

        String defaultFileName = "LibrarySystemExport.csv";

        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Save file");
        fileChooser.setInitialFileName(defaultFileName);
        File savedFile = fileChooser.showSaveDialog(stage);
        if (savedFile != null) {
            try {
                saveFileRoutine(savedFile);
            } catch (Exception e) {
                e.printStackTrace();
                //actionStatus.setText("An ERROR occurred while saving the file!");
                return;
            }
            //actionStatus.setText("File saved: " + savedFile.toString());
        } else {
            //actionStatus.setText("File save cancelled.");
        }

        util.log("Export complete");
    }

    private void saveFileRoutine(File file)
            throws Exception {

        try {
            // Creates a new file and writes the txtArea contents into it
            String txt = "Text content";

            file.createNewFile();
            FileWriter writer = new FileWriter(file);
            writer.write(txt);
            writer.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    void validateAllFields() {        
        //validate member
        if (!isValidMember()) {
            if (!invalidFields.contains("ID")) {
                invalidFields.add("ID");
                member.setStyle(INVALID_STYLE_BORDER);                
            }
        } else {
            invalidFields.remove("ID");
            member.setStyle(VALID_STYLE_BORDER);
        }
        
        //validate Book
        if (!isValidBook()) {
            if (!invalidFields.contains("Book")) {
                invalidFields.add("Book");
                boxBook.setStyle(INVALID_STYLE_BORDER);                
            }
        } else {
            invalidFields.remove("Book");
            boxBook.setStyle(VALID_STYLE_BORDER);
        }
        
        //validate Due Date
        if (!isValidDueDate()) {
            if (!invalidFields.contains("Due Date")) {
                invalidFields.add("Due Date");
                dueDate.setStyle(INVALID_STYLE_BORDER);
            }
        } else {
            invalidFields.remove("Due Date");
             dueDate.setStyle(VALID_STYLE_BORDER);
        }
        
    }
    
    //Fields validation methods:
    
    //ComboBox (ID, member). To be valid it must be selected
    private boolean isValidMember(){       
        return !FieldValidator.isEmpty(member.getValue());
    }
    
    //Book field. To be valid it must be selected
    private boolean isValidBook(){        
        return !FieldValidator.isEmpty(boxBook.getValue());
    }
    
    //Due Date field. To be valid it must be filled
    private boolean isValidDueDate(){       
        return FieldValidator.isValidDatePickerValue(dueDate.getValue());       
    }

}
