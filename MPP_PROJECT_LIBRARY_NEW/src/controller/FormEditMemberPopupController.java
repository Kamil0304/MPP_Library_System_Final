/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import dao.MemberDAO;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.Labeled;
import javafx.scene.control.TextField;
import model.Address;
import model.LibraryMember;
import util.FieldValidator;
import util.Message;

/**
 *
 * @author win7
 */
public class FormEditMemberPopupController extends SaveFormBaseController {

    @FXML
    private ComboBox<String> state;
    
    @FXML
    private ComboBox<String> city;
    
    @FXML
    private TextField firstName;

    @FXML
    private TextField lastName;
    @FXML
    private TextField id;
    
    @FXML
    private TextField phoneNumber;
    
    @FXML
    private Button add;  
    
    @FXML
    private Button update;
    
    @FXML
    private Button refresh;
    
    @FXML
    private TextField street;
    
    @FXML   
    private TextField zip;
    
    private MemberDAO memberDAO;  
       
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        
        state.getItems().clear();

        state.getItems().addAll(
                "IOWA",
                "Texas",
                "Illinoi",
                "Canada",
                "Ohio");
        
        city.getItems().clear();

        city.getItems().addAll(
                "Iowa City",
                "Los Angles",
                "San Fransisco",
                "Chicago",
                "San Diego");
        
        memberDAO = new MemberDAO();
    }
    
    public ComboBox<String> getState() {
		return state;
	}

	public void setState(ComboBox<String> state) {
		this.state = state;
	}

	public TextField getId() {
		return id;
	}

	public void setId(TextField id) {
		this.id = id;
	}

	public ComboBox<String> getCity() {
		return city;
	}

	public void setCity(ComboBox<String> city) {
		this.city = city;
	}

	public TextField getFirstName() {
		return firstName;
	}

	public void setFirstName(TextField firstName) {
		this.firstName = firstName;
	}

	public TextField getLastName() {
		return lastName;
	}

	public void setLastName(TextField lastName) {
		this.lastName = lastName;
	}

	public TextField getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(TextField phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public TextField getStreet() {
		return street;
	}

	public void setStreet(TextField street) {
		this.street = street;
	}

	public TextField getZip() {
		return zip;
	}

	public void setZip(TextField zip) {
		this.zip = zip;
	}

	public MemberDAO getMemberDAO() {
		return memberDAO;
	}

	public void setMemberDAO(MemberDAO memberDAO) {
		this.memberDAO = memberDAO;
	}
public void btnRefresh()
{
	}

	@FXML
    private void addMember(ActionEvent event) {
        //Fields Validation        
        if ( !validate() ) // if not valid don't continu, just return to the form
            return; 
        
        LibraryMember libraryMember = new LibraryMember();
        libraryMember.setFirstName(firstName.getText());
        libraryMember.setLastName(lastName.getText());
        libraryMember.setPhoneNumber(phoneNumber.getText());
        libraryMember.setAddress(new Address(street.getText(),city.getValue().toString(),state.getValue().toString(),zip.getText()));
        try {
            memberDAO.addMember(libraryMember);
            Message.showSuccessMessage("Add Member", "Saving Member Sucess", "");            
        } catch (IOException ex) {
            Message.showErrorMessage("Add Member", "Saving Member Failed. Exception message: ",  ex.getMessage());          
            
        }
        
    }

	@FXML
    public void updateMember(ActionEvent event) {
        //Fields Validation        
        if ( !validate() ) // if not valid don't continu, just return to the form
            return; 
        
        LibraryMember libraryMember = new MemberDAO().getLibraryMember(id.getText().trim());
        libraryMember.setFirstName(firstName.getText());
        libraryMember.setLastName(lastName.getText());
        libraryMember.setPhoneNumber(phoneNumber.getText());
        libraryMember.setAddress(new Address(street.getText(),city.getValue().toString(),state.getValue().toString(),zip.getText()));
        try {
            memberDAO.addMember(libraryMember);
            Message.showSuccessMessage("Add Member", "Saving Member Sucess", "");            
        } catch (IOException ex) {
            Message.showErrorMessage("Add Member", "Saving Member Failed. Exception message: ",  ex.getMessage());          
            
        }
        
    }
    
    @Override
    void validateAllFields() {
        
        //validate firstName
        if (!isValidFirstName()) {
            if (!invalidFields.contains("First Name")) {
                invalidFields.add("First Name");
                firstName.setStyle(INVALID_STYLE_BORDER);                
            }
        } else {
            invalidFields.remove("First Name");
            firstName.setStyle(VALID_STYLE_BORDER);
        }
        
        //validate last name
        if (!isValidLastName()) {
            if (!invalidFields.contains("Last Name")) {
                invalidFields.add("Last Name");
                lastName.setStyle(INVALID_STYLE_BORDER);                
            }
        } else {
            invalidFields.remove("Last Name");
            lastName.setStyle(VALID_STYLE_BORDER);
        }
        
        //validate phone number
        if (!isValidLastName()) {
            if (!invalidFields.contains("Phone Number")) {
                invalidFields.add("Phone Number");
                phoneNumber.setStyle(INVALID_STYLE_BORDER);                
            }
        } else {
            invalidFields.remove("Phone Number");
            phoneNumber.setStyle(VALID_STYLE_BORDER);
        }
        
        //validate state
        if (!isValidState()) {
            if (!invalidFields.contains("State")) {
                invalidFields.add("State");
                state.setStyle(INVALID_STYLE_BORDER);                
            }
        } else {
            invalidFields.remove("State");
            state.setStyle(VALID_STYLE_BORDER);
        }
        
        //validate City
        if (!isValidCity()) {
            if (!invalidFields.contains("City")) {
                invalidFields.add("City");
                city.setStyle(INVALID_STYLE_BORDER);                
            }
        } else {
            invalidFields.remove("City");
            city.setStyle(VALID_STYLE_BORDER);
        }
        
        //validate Street
        if (!isValidStreet()) {
            if (!invalidFields.contains("Street")) {
                invalidFields.add("Street");
                street.setStyle(INVALID_STYLE_BORDER);                
            }
        } else {
            invalidFields.remove("Street");
            street.setStyle(VALID_STYLE_BORDER);
        }
        
        //validate Zip
        if (!isValidZip()) {
            if (!invalidFields.contains("Zip")) {
                invalidFields.add("Zip");
                zip.setStyle(INVALID_STYLE_BORDER);                
            }
        } else {
            invalidFields.remove("Zip");
            zip.setStyle(VALID_STYLE_BORDER);
        }        
        
    }
    
    
     //Fields validation methods:
    
    //firstName:
    private boolean isValidFirstName(){
        String textValue = firstName.getText();
        if (!FieldValidator.isEmpty(textValue) && FieldValidator.isAlphabetOnly(textValue) )  
            return true;        
        return false;
    }
    
    //firstName:
    private boolean isValidLastName(){
        String textValue = lastName.getText();
        if (!FieldValidator.isEmpty(textValue) && FieldValidator.isAlphabetOnly(textValue) ) 
            return true;        
        return false;
    }
    
    //Phone Number:
    private boolean isValidPhoneNumber(){
        String textValue = phoneNumber.getText();
        if (!FieldValidator.isEmpty(textValue) && FieldValidator.isNumericOnly(textValue)) 
            return true;        
        return false;
    }
    
    //State field. To be valid it must be selected
    private boolean isValidState(){              
        return !FieldValidator.isEmpty(state.getValue());
    }
    
    //City field. To be valid it must be selected
    private boolean isValidCity(){              
        return !FieldValidator.isEmpty(city.getValue());
    }
    
    //City field. To be valid it must be non-empty
    private boolean isValidStreet(){              
        return !FieldValidator.isEmpty(street.getText());
    }
    
    //zip: 5 numbers only
    private boolean isValidZip(){
        String textValue = zip.getText();
        if (!FieldValidator.isEmpty(textValue) && FieldValidator.isNumericOnly(textValue) && textValue.length() == 5) 
            return true;        
        return false;
    }

	    
}


