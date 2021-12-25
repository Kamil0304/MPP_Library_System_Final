package model;

//import com.sun.jndi.toolkit.dir.SearchFilter;
import java.io.Serializable;

public class Address implements Serializable{

	private String street;
	private String city;
	private String state;
	private String zip;

        public Address(String street,String city,String state,String zip){
            this.state = state;
            this.city = city;
            this.street = street;
            this.zip = zip;
        }
	public String getStreet() {
		return this.street;
	}

	public void setStreet(String street) {
		this.street = street;
	}

	public String getCity() {
		return this.city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getState() {
		return this.state;
	}

	public void setState(String state) {
		this.state = state;
	}

    public String getZip() {
        return zip;
    }

    public void setZip(String zip) {
        this.zip = zip;
    }

	

}