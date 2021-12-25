package model;

import java.io.Serializable;
import java.util.Date;

public class Fine implements Serializable{
    
    
        private int id;
	private Date fineDate;
	private double amount;

	public Date getFineDate() {
		return this.fineDate;
	}

	public void setFineDate(Date fineDate) {
		this.fineDate = fineDate;
	}

	public double getAmount() {
		return this.amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}

}