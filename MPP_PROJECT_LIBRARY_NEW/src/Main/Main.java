/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Main;


import dao.SystemUserDAO;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import static javafx.application.Application.STYLESHEET_MODENA;
import static javafx.application.Application.launch;
import static javafx.application.Application.setUserAgentStylesheet;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import model.SystemUser;
import util.*;
/**
 *
 * @author mauro
 */
public class Main extends Application {

    @Override
    public void start(Stage primaryStage) {
        
        util.log("Checking/creating data structure");
        util.createInitialStorageDirs();
        
        util.log("Creating initial database");
        util.createInitialDatabase();
        
        try {
        	
            setUserAgentStylesheet(STYLESHEET_MODENA);

            util.log("Starting application...");
            util.log("Setting FXML file for Login screen");
            Parent root = FXMLLoader.load(getClass().getResource("/view/FormLogin.fxml"));
            
            root.setStyle("-fx-background-color:  #998f87");

            util.log("Loading FXML scene");
            Scene scene = new Scene(root);

            util.log("Setting scene stage");
            primaryStage.setScene(scene);

            primaryStage.setResizable(false);

            //primaryStage.getIcons().add(new Image("Main/books.png"));
            primaryStage.setTitle("Library System 1.0 -->> @(Group_09)");
            
            util.log("Showing Login screen form");
            primaryStage.show();
            
        } catch(Exception e) {
            e.printStackTrace();  
	}
        
        
    }
    public static void main(String[] args) {
        launch(args);
    }
        
}
