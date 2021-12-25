/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import util.util;
import Main.Main;
import java.util.Optional;
import javafx.application.Platform;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import model.SystemUser;

/**
 * FXML Controller class
 *
 * @author mauro
 */
public class FormMainController implements Initializable {

    public FormMainController(SystemUser user) {
        this.setUser(user);
    }
    
    @FXML
    private Button checkoutButton;

    @FXML
    private Button checkoutRecordButton;

    @FXML
    private Button addMemberButton;

    @FXML
    private Button editMemberButton;

    @FXML
    private Button addBookButton;

    @FXML
    private Button books;

    @FXML
    private Button logoffButton;

    @FXML
    public Button closeButton;
    
    @FXML
    private Label userLoggedLabel;
    
    private SystemUser user = new SystemUser();
    
    
    public void setUser(SystemUser u) {
        user = u; 
    }
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        String role;       
        
        util.log("Setting buttons according privileges");
        checkoutButton.setDisable(true);
        checkoutRecordButton.setDisable(true);
        addMemberButton.setDisable(true);
        editMemberButton.setDisable(true);
        addBookButton.setDisable(true);
        books.setDisable(true);
        
        if (user.getRole() == SystemUser.AuthorizationLevel.BOTH)
            role = "ADMINISTRATOR/LIBRARIAN";
        else
            role = user.getRole().toString();
        
        
        userLoggedLabel.setText("Wellcome " + user.getUsername() + " (" + role.toLowerCase() + ")");
        
        
        if ((user.getRole() == SystemUser.AuthorizationLevel.ADMINISTRATOR) || (user.getRole() == SystemUser.AuthorizationLevel.BOTH) ) {
            util.log("User has Administrator privileges");
            addMemberButton.setDisable(false);
            editMemberButton.setDisable(false);
            addBookButton.setDisable(false);
            books.setDisable(false);
        }

        if ((user.getRole() == SystemUser.AuthorizationLevel.LIBRARIAN) || (user.getRole() == SystemUser.AuthorizationLevel.BOTH) ) {
            util.log("User has Librarian privileges");
            checkoutButton.setDisable(false);
            checkoutRecordButton.setDisable(false);
            
        }
        
    }    
    
    
    @FXML
    public void handleLogoffButtonAction(ActionEvent event) {

        Main main = new Main();
        
        Stage mainStage = new Stage();
        
        Stage stage = (Stage) logoffButton.getScene().getWindow();
        
        util.log("Logging off application");
        
        main.start(mainStage);
        
        stage.close();

        
    }

    @FXML
    public void handleCloseButtonAction(ActionEvent event) {

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Library System");
        alert.setHeaderText("Close Library System");
        alert.setContentText("Are you sure you want close the system?");

        Optional<ButtonType> result = alert.showAndWait();
        
        if (result.get() == ButtonType.OK) {
            util.log("Closing application");

            util.log("Finishing application");

            Platform.exit();
        }
        
        
    }

    
    private void launchForm(String formURL) {
         
         Stage stage = new Stage();
        
         try {

            util.log("Starting " + formURL + "...");
            util.log("Setting FXML file");
            Parent root = FXMLLoader.load(getClass().getResource(formURL));
            
            root.setStyle("-fx-background-color:  #998f87");

            
            util.log("Loading scene");
            Scene scene = new Scene(root);

            util.log("Setting scene stage");
            stage.setScene(scene);

            stage.setResizable(false);
            //stage.setAlwaysOnTop(true);

            util.log("Showing " + formURL +  " form");
            stage.show();
            
        } catch(Exception e) {
            util.log(e.toString());
            e.printStackTrace();
	}      
        
    }
    
    @FXML
    public void handleCheckoutButtonAction(ActionEvent event) {
        launchForm("/view/FormCheckout.fxml");
    }

    public void handleCheckoutRecordButtonAction(ActionEvent event) {
        launchForm("/view/FormCheckoutRecord.fxml");
    }
    
    public void handleAddMemberButtonAction(ActionEvent event) {
        launchForm("/view/FormAddMember.fxml");
    }
    
    public void handleEditMemberButtonAction(ActionEvent event) {
        launchForm("/view/FormEditMember.fxml");
    }

    public void handleAddBookButtonAction(ActionEvent event) {
        launchForm("/view/FormAddBook.fxml");
    }
    
    public void handleGetAllBooksButtonAction(ActionEvent event) {
        launchForm("/view/books.fxml");
    }
    
}
