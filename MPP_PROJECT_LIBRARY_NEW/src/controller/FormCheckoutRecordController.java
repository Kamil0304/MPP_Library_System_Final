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
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.util.Callback;
import model.Book;
import model.BookCopy;
import model.CheckoutEntry;
import util.util;
import model.CheckoutRecord;
import model.LibraryMember;

/**
 * FXML Controller class
 *
 * @author mauro
 */
public class FormCheckoutRecordController extends SaveFormBaseController {

    @FXML
    private ComboBox member;

    @FXML
    private ComboBox book;

    @FXML
    private TableView tableView;

    @FXML
    private Button export;

    @FXML
    private Button ok;

    private CheckoutRecord checkoutRecord = new CheckoutRecord();

    MemberDAO memberDAO = new MemberDAO();

    CheckoutRecordDAO checkoutRecordDAO = new CheckoutRecordDAO();

    CheckoutEntryDAO entryDAO = new CheckoutEntryDAO();

    BookCopyDao bookCopyDAO = new BookCopyDao();

    BookDao bookDao = new BookDao();

    /**
     * Initializes the controller class.
     */

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        loadTableView();
        tableView.getSelectionModel().selectedItemProperty()
                .addListener((observable, oldValue, newValue) -> handleTableViewDoubleClickAction());

        BookDao books = new BookDao();
        List<Book> bookList = new ArrayList<Book>();
        bookList.addAll(books.getAllBooks());

        MemberDAO members = new MemberDAO();
        List<LibraryMember> memberList = new ArrayList<LibraryMember>();
        memberList.addAll(members.getAllLibraryMembers());

        book.getItems().add("select");
        book.setValue("select");
        member.getItems().add("select");
        member.setValue("select");
        for (Book b : bookList) {
            book.getItems().add(b.getIsbn());
        }

        for (LibraryMember m : memberList) {
            member.getItems().add(m.getId());
        }

    }

    public void loadTableView() {
        List<CheckoutEntry> entries = entryDAO.getAllCheckoutEntries();
        for (int i = 0; i < entries.size(); i++) {
            entries.get(i).setCheckoutRecord(checkoutRecordDAO.getCheckoutRecordByID(entries.get(i).getCheckoutRecord()));
            entries.get(i).getBookCopy().setBook(bookDao.getBookByIsbn(entries.get(i).getBookCopy().getIsbn()));
            entries.get(i).getCheckoutRecord().setLibraryMember(memberDAO.getLibraryMember(entries.get(i).getCheckoutRecord().getLibraryMember().getId()));
        }

        int flag = 1;
        ArrayList<CheckoutEntry> filteredEntries = new ArrayList<>();
        for (int i = 0; i < entries.size(); i++) {
            flag = 1;
            if (member.getValue() != null && !member.getValue().toString().equals("select") && !member.getValue().toString().equals(entries.get(i).getCheckoutRecord().getLibraryMember().getId())) {
                flag = 0;
            }

            if (book.getValue() != null && !book.getValue().toString().equals("select") && !book.getValue().toString().equals(entries.get(i).getBookCopy().getBook().getIsbn())) {
                flag = 0;
            }
            
            if(flag == 1){
                filteredEntries.add(entries.get(i));
            }
        }

        tableView.setItems(FXCollections.observableList(filteredEntries));
        
        TableColumn checkoutID = getTableColumnByName(tableView, "CheckoutID");
        TableColumn member = getTableColumnByName(tableView, "Member");
        TableColumn book = getTableColumnByName(tableView, "Book");
        TableColumn Checkout_Date = getTableColumnByName(tableView, "Checkout Date");
        TableColumn Return_Date = getTableColumnByName(tableView, "Return Date");
        
        
        
        checkoutID.setCellValueFactory(new PropertyValueFactory<CheckoutEntry, String>("id"));
        member.setCellValueFactory(new PropertyValueFactory<CheckoutEntry, String>("checkoutRecord"));
        book.setCellValueFactory(new PropertyValueFactory<CheckoutEntry, String>("bookCopy"));
        Checkout_Date.setCellValueFactory(new PropertyValueFactory<CheckoutEntry, String>("checkoutDate"));
        Return_Date.setCellValueFactory(new PropertyValueFactory<CheckoutEntry, String>("dueDate"));

        member.setCellFactory(new Callback<TableColumn<CheckoutEntry, CheckoutRecord>, TableCell<CheckoutEntry, CheckoutRecord>>() {
            @Override
            public TableCell<CheckoutEntry, CheckoutRecord> call(TableColumn<CheckoutEntry, CheckoutRecord> p) {
                final TableCell<CheckoutEntry, CheckoutRecord> cell = new TableCell<CheckoutEntry, CheckoutRecord>() {
                    @Override
                    public void updateItem(final CheckoutRecord item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty) {
                            this.setText("");
                        } else {
                            this.setText(item.getLibraryMember().getFirstName());
                        }
                    }
                };
                return cell;
            }
        });

        book.setCellFactory(new Callback<TableColumn<CheckoutEntry, BookCopy>, TableCell<CheckoutEntry, BookCopy>>() {
            @Override
            public TableCell<CheckoutEntry, BookCopy> call(TableColumn<CheckoutEntry, BookCopy> p) {
                final TableCell<CheckoutEntry, BookCopy> cell = new TableCell<CheckoutEntry, BookCopy>() {
                    @Override
                    public void updateItem(final BookCopy item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty) {
                            this.setText("");
                        } else {
                            this.setText(item.getBook().getTitle());
                        }
                    }
                };
                return cell;
            }
        });
    }

    @FXML
    public void handleMemberFilterAction(ActionEvent event) {
        //book.getSelectionModel().clearSelection();
        loadTableView();
        //book.setValue(null);
    }

    @FXML
    public void handleBookFilterAction(ActionEvent event) {
        //member.getSelectionModel().clearSelection();
        loadTableView();
        //member.setValue(null);
    }

    public void handleTableViewDoubleClickAction() {

        tableView.setOnMousePressed((MouseEvent event) -> {
            if (event.isPrimaryButtonDown() && event.getClickCount() == 2) {

                int rowIndex = tableView.getSelectionModel().getSelectedIndex();
                ObservableList rowList = (ObservableList) tableView.getItems().get(rowIndex);

                //Retrieving CheckoutID
                String checkoutKey = rowList.get(0).toString();

                Stage stage = new Stage();

                util.log("Loading detailed record form...");

                try {
                    String formURL = "FormCheckoutRecordDetails.fxml";

                    Parent root = FXMLLoader.load(getClass().getResource("/view/" + formURL));

                    root.setStyle("-fx-background-color:  #8EC6E7");

                    util.log("Loading " + formURL);
                    Scene scene = new Scene(root);

                    util.log("Setting scene stage");
                    stage.setScene(scene);

                    stage.setResizable(false);
                    stage.setAlwaysOnTop(true);

                    util.log("Showing " + formURL);
                    stage.show();

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

    }

    private <T> TableColumn<T, ?> getTableColumnByName(TableView<T> tableView, String name) {
        for (TableColumn<T, ?> col : tableView.getColumns()) {
            if (col.getText().equals(name)) {
                return col;
            }
        }
        return null;
    }

    @Override
    void validateAllFields() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
