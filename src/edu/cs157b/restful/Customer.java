//ONE SIDED CUSTOMER  -----> MANY SIDED ORDER (ONE TO MANY)
package edu.cs157b.restful;

import javax.persistence.*;
import java.util.*;

@Entity
@Table(name = "customer_info")

public class Customer {
	@Id 
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="customer_id")
	private int userId; 
	
	@Column(name = "username",unique=true)
	private String username; 
	
	@Column(name = "password")
	private String password; 
	
	
	@Column(name = "address")
	private Address address;
	
	@OneToMany(mappedBy="customer", targetEntity= Order.class, fetch = FetchType.EAGER,
			cascade = CascadeType.ALL)
	private List<Order> orders;
	
	
	
	public int getUserId() {
		return userId;
	}
	
	public Customer(){
		this.orders = new ArrayList<Order>();
	}
	
	public void setUserId(int userId) {
		this.userId = userId;
	}

	
	
	public String getUserName() {
		return this.username;
	}

	public void setUserName(String userName) {
		this.username = userName;
	}

	
	public String getPassword() {
		return this.password;
	}
	
	public void setPassword(String password) {
		this.password = password;
	}
	
	
	public Address getAddress() {
		return this.address;
	}

	public void setAddress(Address address) {
		this.address = address;
	}

	public String toString() {
		return "USER_ID: "+this.userId+"::: USERNAME: "+ this.username + "::: PASSWORD: "+ this.password 
				+ "\n::: ADDRESS: "+ this.address.toString();
	}

	public List<Order> getOrders() {
		return this.orders;
	}

	public void setOrder(Order order) {
		this.orders.add(order);
	}
	
	
	
}
