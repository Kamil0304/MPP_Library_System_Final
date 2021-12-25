/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;


import dao.SystemUserDAO;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import util.util;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.FlowPane;
import model.SystemUser;

/**
 * FXML Controller class
 *
 * @author mauro
 */
public class FormLoginController implements Initializable {

    /**
     * Initializes the controller class.
     */
    
    @FXML
    private TextField userNameField;
    @FXML
    private TextField passwordField;

    @FXML
    public Button cancelButton;

    @FXML
    private Button signInButton;
    

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
    }    

    

    @FXML
    public void handleCloseButtonAction(ActionEvent event) {
        Stage stage = (Stage) cancelButton.getScene().getWindow();

        util.log("Closing application");

        stage.close();
    }

    
    @FXML
    public void handleEnterPressed(KeyEvent event) {
        
        if (event.getCode() == KeyCode.ENTER) {
            handleSignInButtonAction();
        }
    }
    
    @FXML
    public void handleSignInButtonAction() {

        boolean isNull = true;
        boolean invalidUsername = true;
        boolean invalidPassword = true;
        
                
        Stage mainStage = new Stage();
        
        Stage stage = (Stage) cancelButton.getScene().getWindow();
        
        util.log("Signing in application");

        //Create a user class to verify if username and password 
        //informed at login are equal to DAO user credentials
        SystemUser user = new SystemUser();
        
        user.setUsername(userNameField.getText());
        user.setPassword(passwordField.getText());
        
        SystemUserDAO userDAO = new SystemUserDAO();
        
        if (!userDAO.isValidUser(user)) {

            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Library System");
            alert.setHeaderText("Invalid User/Password");
            alert.setContentText("Username or Password invalid!");

            Optional<ButtonType> result = alert.showAndWait();
            
            userNameField.requestFocus();

        }
        else {
            userDAO.setUser(user);
            user.setRole(userDAO.getUser(user.getUsername()).getRole());
            
            
            
            try {

                util.log("Starting Main Frame...");
                util.log("Setting FXML file");

            /*
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
            */

                FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/FormMain.fxml"));
                
                //Parent root = FXMLLoader.load(getClass().getResource("/view/FormMain.fxml"));
                
                FormMainController controller = new FormMainController(user);
                        
                loader.setController(controller);
                Parent root = (Parent) loader.load(); 
                
                controller.setUser(user);
                
                
                util.log("Loading FXML scene for Main Frame");

                util.log("Setting scene");
                Scene scene = new Scene(root);

                mainStage.setScene(scene);
                mainStage.setResizable(false);

                //closing previous screen (login screen)
                util.log("Closing previous form");
                stage.close();
                
                
                mainStage.show();
            }
            catch(Exception e) {
                e.printStackTrace();

            }

        }
            
    }
}
