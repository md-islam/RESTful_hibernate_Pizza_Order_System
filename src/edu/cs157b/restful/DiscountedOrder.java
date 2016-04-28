package edu.cs157b.restful;
import javax.persistence.*;


@Entity
public class DiscountedOrder extends Order{
	
	private double discountedRate;

	public double getDiscountedRate() {
		return super.getOrderPrice();
	}

	public void setDiscountedRate(double rate) {
		this.discountedRate = rate - rate * 0.10;
	}
	
	
	public String toString(){
//		return super.getCustomer().getUserName() + "order id: "+ super.getOrderId() +", discounted order price: "+this.getDiscountedRate();
		return super.toString();
	}
	 
		
		
}
