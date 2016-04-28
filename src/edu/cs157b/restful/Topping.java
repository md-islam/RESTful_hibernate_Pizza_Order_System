package edu.cs157b.restful;

import java.util.*;

import javax.persistence.*;

@Entity
@Table(name = "topping")
public class Topping {

	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="topping_id")
	private int toppingId; 
	
	@Column(name="topping")
	private String toppingName; 
	
	@Column(name="topping_price")
	private double toppingPrice; 
	
	
	@ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "join_table",
    joinColumns = {@JoinColumn(name="topping_id")},
    inverseJoinColumns = {@JoinColumn(name = "order_id")}
    )
	private List<Order> orders;
	
	
	public Topping(){
		orders = new ArrayList<Order>();		
	}
	
	
	public int getToppingId() {
		return toppingId;
	}
	
	
	public void setToppingId(int toppingId) {
		this.toppingId = toppingId;
	}
	
	
	public String getToppingName() {
		return toppingName;
	}
	
	
	public void setToppingName(String toppingName) {
		this.toppingName = toppingName;
	}
	
	
	
	public double getToppingPrice() {
		return toppingPrice;
	}
	
	
	public void setToppingPrice(double toppingPrice) {
		this.toppingPrice = toppingPrice;
	}
	
	
	public String toString(){
		return toppingId+" "+toppingName+" "+toppingPrice;
	}
	
	public List<Order> getOrders(){
		return orders;
	}
	
	public void setOrder(Order order){
		orders.add(order);
	}
	
	
	
}
