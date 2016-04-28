package edu.cs157b.restful;

import javax.persistence.*;

@Embeddable
public class Address {
	@Column(name = "street")
	private String StreetName;
	
	@Column(name = "city")
	private String City; 
	
	@Column(name = "state")
	private String State; 
	
	@Column(name = "zipcode")
	private String ZipCode; 
	
	
	
	public String getStreetName() {
		return StreetName;
	}
	
	
	public void setStreetName(String streetName) {
		StreetName = streetName;
	}
	
	
	public String getCity() {
		return City;
	}
	
	
	public void setCity(String city) {
		City = city;
	}
	
	
	public String getState() {
		return State;
	}
	public void setState(String state) {
		State = state;
	}
	
	
	public String getZipCode() {
		return ZipCode;
	}
	
	
	public void setZipCode(String zipCode) {
		ZipCode = zipCode;
	}
	
	public String toString() {
		return "STREET: "+this.StreetName+"::: CITY: "+this.City+"::: STATE: "+ this.State+ "::: ZIP: "+this.ZipCode;
	}
	
	
	
	
	
	
	
}
