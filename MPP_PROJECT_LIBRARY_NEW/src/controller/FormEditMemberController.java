/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import dao.BookDao;
import dao.MemberDAO;

import java.net.URL;
import java.util.Arrays;
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
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import javafx.util.Callback;
import model.Address;
import model.Book;
import model.LibraryMember;
import model.Person;

/**
 * FXML Controller class
 *
 * @author win7
 */
public class FormEditMemberController implements Initializable {

    @FXML
    private TableView memberTableView;

    private BookDao bookDao = new BookDao();
    private MemberDAO mdao = new MemberDAO();
    LibraryMember member=  new LibraryMember();
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        List<LibraryMember> members = mdao.getAllLibraryMembers();
        //System.out.println(members.toArray());
        memberTableView.setItems(FXCollections.observableList(members));
        TableColumn firstName = getTableColumnByName(memberTableView, "firstName");
        TableColumn lastName = getTableColumnByName(memberTableView, "lastName");
       TableColumn phoneNumber = getTableColumnByName(memberTableView, "phoneNumber");
        TableColumn edit_member = getTableColumnByName(memberTableView, "edit member");
      
     //   TableColumn street = getTableColumnByName(memberTableView, "street");
        
       // System.out.println(new PropertyValueFactory<LibraryMember, String>("street"));
        //System.out.println(new PropertyValueFactory<LibraryMember, String>("firstName"));
        //street.setCellFactory(new PropertyValueFactory<LibraryMember, Address>("street"));
        //street.setCellFactory(null);
        firstName.setCellValueFactory(new PropertyValueFactory<LibraryMember, String>("firstName"));
        lastName.setCellValueFactory(new PropertyValueFactory<LibraryMember, String>("lastName"));
        phoneNumber.setCellValueFactory(new PropertyValueFactory<LibraryMember, String>("phoneNumber"));
        edit_member.setCellValueFactory(new PropertyValueFactory<LibraryMember, String>("id"));
      // street.setCellFactory(new PropertyValueFactory<LibraryMember, String>("street"));
        edit_member.setCellFactory(new Callback<TableColumn<LibraryMember, String>, TableCell<LibraryMember, String>>() {
            @Override
            public TableCell<LibraryMember, String> call(TableColumn<LibraryMember, String> btnCol)
            {
                return new TableCell<LibraryMember, String>() {
                    final Button button = new Button();
                    {
                        button.setText("Edit Member");
                    }
      
                    @Override
                    public void updateItem( String id, boolean empty) {
                        if (id != null) {
                            button.setOnAction(new EventHandler<ActionEvent>() {
                                @Override
                                public void handle(ActionEvent event) {
                                    showEditMemberForm(mdao.getLibraryMember(id));
                                
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
    public void btnRefresh()
    {
    	memberTableView.refresh();
    }
    public void showEditMemberForm(LibraryMember member) {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/view/UpdateMember.fxml"));
        try {
            Parent root = (Parent) fxmlLoader.load();
            FormAddMemberController controller = fxmlLoader.<FormAddMemberController>getController();

            controller.getId().setText(member.getId());
            controller.getFirstName().setText(member.getFirstName());
            controller.getLastName().setText(member.getLastName());
            controller.getPhoneNumber().setText(member.getPhoneNumber());
            controller.getStreet().setText(member.getAddress().getStreet());
            controller.getZip().setText(member.getAddress().getZip());
            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.show();
        } catch (Exception e) {
        		System.out.println("Hello");
        }
    }

}
