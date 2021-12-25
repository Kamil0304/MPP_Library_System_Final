/*
  This class is the controller base class for all Saving Forms
  For each SubClass implement this base class and in order to use validate method correctly,
    the SubClass should add the invalid name of the field to list invalidFields
    (e.g. add listner to each field, and if not valid add it to the list invalidFields)
 */
package controller;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import util.util;

/**
 *
 * @author mauro
 */
public abstract class SaveFormBaseController implements Initializable {

    @FXML
    Button cancelButton;//any saving form contain cancel button

    List<String> invalidFields;//to add the invlaid fields    
    private final Alert errorValidationAlert;//the object that will show the error if the fields are invalid
    
    static final String INVALID_STYLE_TEXT = "-fx-text-fill: red;-fx-font-weight: bold;";
    static final String VALID_STYLE_TEXT = "-fx-text-fill: black;-fx-font-weight: black;";
    static final String INVALID_STYLE_BACKGROUND = "-fx-background-color: red;";
    static final String VALID_STYLE_BACKGROUND = "-fx-background-color: white;";
    static final String INVALID_STYLE_BORDER = "-fx-border-color: red;";
    static final String VALID_STYLE_BORDER = "-fx-border-color: gray;";
    

    SaveFormBaseController() {
        invalidFields = new ArrayList<>();
        errorValidationAlert = new Alert(Alert.AlertType.ERROR);
        errorValidationAlert.setTitle("Error Dialog");
        errorValidationAlert.setHeaderText("Following fields are invalid: ");
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
    }

    @FXML
    public void handleCancelButtonAction(ActionEvent event) {
        Stage stage = (Stage) cancelButton.getScene().getWindow();

        util.log("Cancelling action/form");

        stage.close();
    }

    /*
      Should be called from Save action of the saving form
      return true if all field are valid
      if, there are invalid field -> show message and return false
     */
    boolean validate() {
        validateAllFields();
        if (!invalidFields.isEmpty()) {
            errorValidationAlert.setContentText(invalidFields.toString());
            errorValidationAlert.showAndWait();
            return false;
        }
        return true;
    }
    
    //this metod should be overriden in each subclass to add the non valid fields to list invalidFields
    //if the field is valid, remove it from the list (either it added before or not) if not added before, nothing removed
    abstract  void validateAllFields();

}
