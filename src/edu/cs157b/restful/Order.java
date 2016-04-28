//MANY SIDED ORDERS 

package edu.cs157b.restful;
import java.sql.Timestamp;
import java.util.*;

import javax.persistence.*;

@Entity
@Table(name="Order_info")
public class Order {
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO) 
	@Column(name="order_id")
	private int orderId; 
	
	@Column(name="order_price")
	private double orderPrice; 
	
	
	@Enumerated(EnumType.STRING)
	@Column(name="pizza_size")
	private PizzaSize pizzaSize;
	
	@Enumerated(EnumType.STRING)
	@Column(name="payment_method")
	private PaymentMethod paymentMethod;
	
	
	
	
	//need to add delivery time 
	
	
	//changed cascadetype here for restful service assignment		
	@ManyToOne (fetch = FetchType.EAGER)
    @JoinColumn(name="customer_id")
    private Customer customer;	
	
	
	
	@ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "join_table",
    joinColumns = {@JoinColumn(name="order_id")},
    inverseJoinColumns = {@JoinColumn(name = "topping_id")}
    )
	private List<Topping> toppings;
	
	
	@Column(name = "time_of_delivery")
	private Timestamp time;
	
	
	public Order(){
		toppings = new ArrayList<Topping>();		
	}
	
	
	public int getOrderId() {
		return orderId;
	}
	
	public void setOrderId(int orderId) {
		this.orderId = orderId;
	}
	
	
	public double getOrderPrice() {
		return orderPrice;
	}
	public void setOrderPrice(double orderPrice) {
		this.orderPrice = orderPrice;
	}
	
	
	public PizzaSize getPizzaSize() {
		return pizzaSize;
	}
	public void setPizzaSize(PizzaSize pizzaSize) {
		this.pizzaSize = pizzaSize;
	}
	
	
	public PaymentMethod getPaymentMethod() {
		return paymentMethod;
	}
	public void setPaymentMethod(PaymentMethod paymentMethod) {
		this.paymentMethod = paymentMethod;
	}
	
	public Customer getCustomer() {
		return customer;
	}
	public void setCustomer(Customer customer) {
		this.customer = customer;
	}
	
	@Temporal(TemporalType.TIMESTAMP)
	public Timestamp getDeliveryTime(){
		return this.time;
	}
	
	public void setDeliveryTime(Timestamp deliveryTime){
		this.time = deliveryTime;
	}
	
	public List<Topping> getToppings() {
		return toppings;
	}


	public void setToppings(Topping topping) {
		toppings.add(topping);
	}
	
	public String toString(){
		return customer.getUserName() + "::::order id: "+ this.orderId +", :::order price: "+this.orderPrice+" :::payment_method: "+ this.getPaymentMethod()+ 
				" :::time_of_delivery: "+ this.getDeliveryTime().toString();
	}
	
}
