/*
This class contains static method for validaion the values of the field
 */
package util;

import java.time.LocalDate;

public class FieldValidator {

    public static boolean isEmpty(String value) {

        return (value == null) || (value.trim().equals(""));
    }
    
    public static boolean isNumericOnly (String fieldValue){
        
        return fieldValue.matches("\\d+");  //return true if digits only      
    }
    
    //return true if the string contain only letters (i.e. A-Z or a-z)   
    public static boolean isAlphabetOnly (String fieldValue){
        
        return fieldValue.matches("[A-Za-z ]+");      
    }
    
    public static boolean isValidDatePickerValue(LocalDate datePickerValue){
       return datePickerValue != null;  
    }
    
    //String contain only numbers or alphabet
    public static boolean isAlphaNumeric (String fieldValue){
       return fieldValue.matches("^[a-zA-Z0-9]*$");      
    }
    

}
