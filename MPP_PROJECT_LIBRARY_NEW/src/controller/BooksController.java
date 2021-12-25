/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import dao.BookDao;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import javafx.util.Callback;
import model.Book;

/**
 * FXML Controller class
 *
 * @author win7
 */
public class BooksController implements Initializable {
    @FXML
    private TableView booksTableView;

    private BookDao bookDao = new BookDao();

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        List<Book> books = bookDao.getAllBooks();
        TableColumn isbn = getTableColumnByName(booksTableView, "ISBN");
        TableColumn title = getTableColumnByName(booksTableView, "Title");
        TableColumn Authors = getTableColumnByName(booksTableView, "Authors");
        TableColumn add_copy = getTableColumnByName(booksTableView, "Add Copy");
        booksTableView.setItems(FXCollections.observableList(books));
        isbn.setCellValueFactory(new PropertyValueFactory<Book, String>("isbn"));
        title.setCellValueFactory(new PropertyValueFactory<Book, String>("title"));
        add_copy.setCellValueFactory(new PropertyValueFactory<Book, String>("isbn"));

        add_copy.setCellFactory(new Callback<TableColumn<Book, String>, TableCell<Book, String>>() {
            @Override
            public TableCell<Book, String> call(TableColumn<Book, String> btnCol) {
                return new TableCell<Book, String>() {
                    final Button button = new Button();
                    
                    {
                        button.setText("Add Copy");
                    }

                    @Override
                    public void updateItem(final String isbn, boolean empty) {
                        if (isbn != null) {
                            button.setOnAction(new EventHandler<ActionEvent>() {
                                @Override
                                public void handle(ActionEvent event) {
                                    showAddCopyForm(bookDao.getBookByIsbn(isbn));
                                
                                }
                            });
                            this.setGraphic(button);
                        }
                    }
                };
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
public void refreshTableView()
{
	booksTableView.refresh();
}

    public void showAddCopyForm(Book book) {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/view/FormAddBookCopy.fxml"));
        try {
            Parent root = (Parent) fxmlLoader.load();
            FormAddBookCopyController controller = fxmlLoader.<FormAddBookCopyController>getController();
            controller.getIsbnField().setText(book.getIsbn());
            controller.getTitleField().setText(book.getTitle());
            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.show();
        } catch (Exception e) {

        }
    }

}
