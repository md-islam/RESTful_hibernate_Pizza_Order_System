package edu.cs157b.restful;

public enum PizzaSize {
	SMALL(3.0),
	MEDIUM(5.0),
	LARGE(7.0);
	
	private double pizzaPrice; 
	
	private PizzaSize(double pizzaPrice){
		this.pizzaPrice = pizzaPrice;
	}
	
	public double getPizzaPrice(){
		return this.pizzaPrice;
	}
}
