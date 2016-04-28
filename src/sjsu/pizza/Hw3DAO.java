package sjsu.pizza;

import java.util.List;

import edu.cs157b.restful.Customer;
import edu.cs157b.restful.Order;
import edu.cs157b.restful.Topping;

public interface Hw3DAO {
	Customer insertCustomer(Customer customer);
	void insertTopping(Topping topping);
	Topping getTopping(String toppingName);
	Order insertOrder(Order order);
	List<Order> getAllOrders();
	Customer getCustomer(String username, String password);
	Order getAnOrderById(int order_id);
	Customer getCustomerById(int customer_id);
	Order updateOrderPrice(Order order);
	Order deleteOrder(Order order);
	List<Customer> getAllCustomers();
	Customer updateCustomerName(Customer customer);
	Customer deleteCustomer(Customer customer);
}
