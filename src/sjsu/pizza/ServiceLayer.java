package sjsu.pizza;

import java.lang.reflect.Type;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.List;

import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import edu.cs157b.restful.Address;
import edu.cs157b.restful.Customer;
import edu.cs157b.restful.DiscountedOrder;
import edu.cs157b.restful.Order;
import edu.cs157b.restful.PaymentMethod;
import edu.cs157b.restful.PizzaSize;
import edu.cs157b.restful.Topping;

/**
 * 
 * @author MD This class is similar to a service layer
 */

@Path("/database")
public class ServiceLayer {

	/**
	 * This method creates customer objects for persisting purposes later
	 */
	private Customer customer1 = new Customer();
	private Customer customer2 = new Customer();
	private Customer customer3 = new Customer();

	@GET
	@Produces(MediaType.TEXT_HTML)
	public String returnTitle() {
		return "<p> Welcome to pizza order system database :-)</p>";
	}

	public void createSampleCustomerObjects() {
		// customer 1

		Address address1 = new Address();
		address1.setStreetName("404 Dempsey");
		address1.setCity("Milpitas");
		address1.setState("CA");
		address1.setZipCode("45612");

		customer1.setUserName("md.islam");
		customer1.setPassword("abcd");
		customer1.setAddress(address1);

		// customer 2

		Address address2 = new Address();
		address2.setStreetName("501 Murphy Ranch");
		address2.setCity("Milpitas");
		address2.setState("CA");
		address2.setZipCode("95145");

		customer2.setUserName("islam.ashfaq");
		customer2.setPassword("abcd");
		customer2.setAddress(address2);

		// customer 3

		Address address3 = new Address();
		address3.setStreetName("404 File not found");
		address3.setCity("Kuwait");
		address3.setState("Abbasiya");
		address3.setZipCode("99999");

		customer3.setUserName("solid.snake");
		customer3.setPassword("abcd");
		customer3.setAddress(address3);

	}

	@Path("/populateCustomers")
	@POST
	public Response persistCustomers() {
		// construct objects
		createSampleCustomerObjects();

		// persist them customers
		Hw3DAO hw3dao = new ConcreteHw3DAO();
		hw3dao.insertCustomer(customer1);
		hw3dao.insertCustomer(customer2);
		hw3dao.insertCustomer(customer3);

		String output = "Customer's persisted";
		return Response.status(200).entity(output).build();

	}

	// preparing objects for persisting

	@Path("/populateTopping")
	@POST
	public void persistToppings() {
		// construct sample customers;
		createSampleCustomerObjects();

		Hw3DAO hw3dao = new ConcreteHw3DAO();
		Topping topping = new Topping();
		topping.setToppingName("Pepperoni");
		topping.setToppingPrice(5);
		hw3dao.insertTopping(topping);

		topping.setToppingName("Mushrooms");
		topping.setToppingPrice(1);
		hw3dao.insertTopping(topping);

		topping.setToppingName("Onions");
		topping.setToppingPrice(1);
		hw3dao.insertTopping(topping);

		topping.setToppingName("Sausage");
		topping.setToppingPrice(4);
		hw3dao.insertTopping(topping);

		topping.setToppingName("Bacon");
		topping.setToppingPrice(3);
		hw3dao.insertTopping(topping);

		topping.setToppingName("Extra cheese");
		topping.setToppingPrice(1);
		hw3dao.insertTopping(topping);

		topping.setToppingName("Black olives");
		topping.setToppingPrice(1);
		hw3dao.insertTopping(topping);

		topping.setToppingName("Green Peppers");
		topping.setToppingPrice(1);
		hw3dao.insertTopping(topping);

		topping.setToppingName("Pineapple");
		topping.setToppingPrice(2);
		hw3dao.insertTopping(topping);

		topping.setToppingName("Spinach");
		topping.setToppingPrice(3);
		hw3dao.insertTopping(topping);
	}

	public Order createSingleOrder(Customer customer, String pizzaSize, String paymentMethod, Topping[] toppings) {
		Order order = new Order();
		Hw3DAO hw3dao = new ConcreteHw3DAO();
		System.out.println(customer.getUserName() + " " + customer.getPassword());
		Customer customerFromDB = hw3dao.getCustomer(customer.getUserName(), customer.getPassword());
		// System.out.println(customerFromDB.toString());
		double pizzaPrice = 0;
		try {
			order.setPizzaSize(PizzaSize.valueOf(pizzaSize.toUpperCase()));
			pizzaPrice += PizzaSize.valueOf(pizzaSize.toUpperCase()).getPizzaPrice();
			order.setPaymentMethod(PaymentMethod.valueOf(paymentMethod.toUpperCase()));

			for (int i = 0; i < toppings.length; i++) {
				pizzaPrice += toppings[i].getToppingPrice();
				order.setToppings(toppings[i]);
			}

			order.setOrderPrice(pizzaPrice);
			order.setCustomer(customerFromDB);
			customerFromDB.setOrder(order);
			order.setDeliveryTime(new java.sql.Timestamp(Calendar.getInstance().getTime().getTime()));
			hw3dao.insertOrder(order);

		} catch (Exception e) {
			System.out.println("EXCEPTION THROWN");
			e.printStackTrace();
		}
		return order;
	}

	// preparing orders to persist to the system

	@Path("/populateOrders")
	@POST
	public void persistOrders() {

		// construct sample customers;
		createSampleCustomerObjects();
			Order order4 = createSingleOrder(customer3, "LARGE", "MASTER",
				new Topping[] { getTopping("Bacon"), getTopping("Mushrooms") });

		
	}

	/**
	 * This method gets Topping by depending on DAO class and returns it
	 * 
	 * @param toppingName
	 * @return topping object from database
	 * 
	 */
	public Topping getTopping(String toppingName) {
		Hw3DAO hw3dao = new ConcreteHw3DAO();
		return hw3dao.getTopping(toppingName);
	}

	/**
	 * Getting all customers from database
	 * 
	 * @return
	 */
	@Path("/customers")
	@GET
	@Produces(MediaType.TEXT_PLAIN)
	public Response getCustomers() {
		Hw3DAO hw3dao = new ConcreteHw3DAO();
		List<Customer> customers = hw3dao.getAllCustomers();
		String response = "";
		int i = 1;
		for (Customer c : customers) {
			response += i + ". " + c.toString() + "\n";
			i++;
		}
		String finalResponse = "All Customers\n" + response;
		return Response.status(200).entity(finalResponse).build();
	}

	@Path("/orders")
	@GET
	@Produces(MediaType.TEXT_PLAIN)
	public Response getOrders() {
		Hw3DAO hw3dao = new ConcreteHw3DAO();
		List<Order> orders = hw3dao.getAllOrders();
		String response = "";
		for (Order o : orders) {
			response += o.toString() + "\n";
			// System.out.println(o.toString());
		}
		String finalResponse = "All Orders\n" + response;
		return Response.status(200).entity(finalResponse).build();
	}
	
	@Path("/discountedorders")
	@GET
	@Produces(MediaType.TEXT_PLAIN)
	public Response getDiscountedOrders() {
		Hw3DAO hw3dao = new ConcreteHw3DAO();
		List<Order> orders = hw3dao.getAllOrders();
		String response = "";
		for (Order o : orders) {
			
			if(o instanceof DiscountedOrder)
			{
				response += o.toString() + "\n";
			}
			// System.out.println(o.toString());
		}
		String finalResponse = "All Discounted Orders\n" + response;
		return Response.status(200).entity(finalResponse).build();
	}

	/**
	 * Retrieve order by id
	 * 
	 * @param x
	 * @return
	 */
	@Path("/orders/{order_id}")
	@GET
	@Produces(MediaType.TEXT_PLAIN)
	public Response getOrderById(@PathParam("order_id") int id) {
		Hw3DAO hw3dao = new ConcreteHw3DAO();
		Order order = hw3dao.getAnOrderById(id);
		String response = order.toString();
		String finalResponse = "All Orders\n" + response;
		return Response.status(200).entity(finalResponse).build();
	}

	/**
	 * Get customer by an ID
	 */
	@Path("/customers/{id}")
	@GET
	@Produces(MediaType.TEXT_PLAIN)
	public Response getCustomerById(@PathParam("id") int id) {
		Hw3DAO hw3dao = new ConcreteHw3DAO();
		Customer customer = hw3dao.getCustomerById(id);
		String response = customer.toString();
		String finalResponse = "All Orders\n" + response;
		return Response.status(200).entity(finalResponse).build();
	}

	/**
	 * Add a new order for a customer_id.
	 * http://localhost:8080/RestfulService/rest/database/orders?type=discounted
	 * &customer_id=2&payment_method=VISA&pizza_size=SMALL&topping_1=Mushrooms&
	 * topping_2=Onions&topping_3=sausage
	 */

	@Path("/orders")
	@POST
	@Produces(MediaType.TEXT_PLAIN)
	public Response addOrder(@QueryParam("type") String type, @QueryParam("customer_id") int id,
			@QueryParam("payment_method") String paymentMethod, @QueryParam("pizza_size") String pizzaSize,
			@QueryParam("topping_1") String topping1, @QueryParam("topping_2") String topping2,
			@QueryParam("topping_3") String topping3) {

		Topping[] toppings = new Topping[] { getTopping(topping1), getTopping(topping2), getTopping(topping3) };
		if (type.equals("discounted")) {
			createOrderForCustomer(new DiscountedOrder(), id, pizzaSize, paymentMethod, toppings);
		} else if (type.equals("regular")) {
			createOrderForCustomer(new Order(), id, pizzaSize, paymentMethod, toppings);
		} else {
			createOrderForCustomer(new Order(), id, pizzaSize, paymentMethod, toppings);
		}
		return Response.status(200).entity("order persisted").build();
	}

	/**
	 * Add customer to database along with address
	 * 
	 * SAMPLE URL:
	 * http://localhost:8080/RestfulService/rest/database/customers?username=
	 * spartan.sammy&password=mgs&city=kuwait&state=Farwaniya&street=shuwaikh&
	 * zip=12345
	 */
	@Path("/customers")
	@POST
	@Produces(MediaType.TEXT_PLAIN)
	public Response addCustomer(@QueryParam("username") String username, @QueryParam("password") String password,
			@QueryParam("city") String city, @QueryParam("state") String state, @QueryParam("street") String street,
			@QueryParam("zip") String zip) {
		// stub for persisting individual customers
		Customer c = new Customer();
		Address a = new Address();
		a.setStreetName(street);
		a.setCity(city);
		a.setState(state);
		a.setZipCode(zip);

		c.setUserName(username);
		c.setPassword(password);
		c.setAddress(a);

		Hw3DAO hw3dao = new ConcreteHw3DAO();
		hw3dao.insertCustomer(c);
		return Response.status(200).entity("customer persisted").build();

	}

	/**
	 * Update orders with a price
	 * 
	 * @param order_id
	 * @param new_price
	 * @return
	 */
	@Path("/orders/{id}/{new_price}")
	@PUT
	@Produces(MediaType.TEXT_PLAIN)
	public Response updateOrder(@PathParam("id") int order_id, @PathParam("new_price") double new_price) {

		Hw3DAO hw3dao = new ConcreteHw3DAO();
		Order order = hw3dao.getAnOrderById(order_id);
		order.setOrderPrice(new_price);
		hw3dao.updateOrderPrice(order);
		return Response.status(200).entity("order updated").build();
	}

	/**
	 * Update customers to a new name
	 */
	@Path("/customers/{id}/{new_name}")
	@PUT
	@Produces(MediaType.TEXT_PLAIN)
	public Response updateCustomer(@PathParam("id") int id, @PathParam("new_name") String new_name) {
		Hw3DAO hw3dao = new ConcreteHw3DAO();
		Customer customer = hw3dao.getCustomerById(id);
		int user_id = customer.getUserId();
		String old_name = customer.getUserName();
		customer.setUserName(new_name);
		Customer updatedCustomer = hw3dao.updateCustomerName(customer);
		return Response.status(200).entity("customer with id::=> " + user_id + " -and name-" + old_name
				+ "-- is updated to -->" + updatedCustomer.getUserName()).build();
	}

	/**
	 * Delete a customer by id
	 */
	@Path("/customers/{id}")
	@DELETE
	@Produces(MediaType.TEXT_PLAIN)
	public Response deleteCustomerById(@PathParam("id") int id) {
		Hw3DAO hw3dao = new ConcreteHw3DAO();
		Customer customer = hw3dao.getCustomerById(id);
		hw3dao.deleteCustomer(customer);
		return Response.status(200).entity(":::DELETED CUSTOMER RECORD=> " + customer.toString()).build();
	}

	/**
	 * Delete an order by ID
	 * 
	 * @param order_id
	 * @return
	 */
	@Path("/orders/{id}")
	@DELETE
	@Produces(MediaType.TEXT_PLAIN)
	public Response deleteOrderById(@PathParam("id") int order_id) {
		Hw3DAO hw3dao = new ConcreteHw3DAO();
		Order order = hw3dao.getAnOrderById(order_id);
		hw3dao.deleteOrder(order);
		return Response.status(200).entity(":::DELETED ORDER RECORD=> " + order.toString()).build();
	}

	public Order createOrderForCustomer(Order order, int customer_id, String pizzaSize, String paymentMethod,
			Topping[] toppings) {
		// Order order = new Order();
		Hw3DAO hw3dao = new ConcreteHw3DAO();
		Customer customerFromDB = hw3dao.getCustomerById(customer_id);
		double pizzaPrice = 0;
		try {
			order.setPizzaSize(PizzaSize.valueOf(pizzaSize.toUpperCase()));
			pizzaPrice += PizzaSize.valueOf(pizzaSize.toUpperCase()).getPizzaPrice();
			order.setPaymentMethod(PaymentMethod.valueOf(paymentMethod.toUpperCase()));

			for (int i = 0; i < toppings.length; i++) {
				if (toppings[i] != null) {
					pizzaPrice += toppings[i].getToppingPrice();
					order.setToppings(toppings[i]);
				}
			}
			if (order instanceof DiscountedOrder) {
				order.setOrderPrice(pizzaPrice - (pizzaPrice * 0.10));
			} else {
				order.setOrderPrice(pizzaPrice);
			}

			order.setCustomer(customerFromDB);
			customerFromDB.setOrder(order);
			order.setDeliveryTime(new java.sql.Timestamp(Calendar.getInstance().getTime().getTime()));
			hw3dao.insertOrder(order);

		} catch (Exception e) {
			System.out.println("EXCEPTION THROWN");
			e.printStackTrace();
		}
		return order;
	}
}
