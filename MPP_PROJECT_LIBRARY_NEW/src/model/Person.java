package model;

import java.io.Serializable;

public class Person implements Serializable{

    private String id;
    private String firstName;
    private String lastName;
    private String phoneNumber;
    private Address address;    
    
	
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }


    public String getFirstName() {
        return this.firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return this.lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPhoneNumber() {
        return this.phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
    
     public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

}
