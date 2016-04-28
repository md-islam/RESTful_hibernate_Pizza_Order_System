package sjsu.pizza;

import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import edu.cs157b.restful.Customer;
import edu.cs157b.restful.Order;
import edu.cs157b.restful.Topping;
import edu.cs157b.util.HibernateUtil;

public class ConcreteHw3DAO implements Hw3DAO {
	private static SessionFactory sessionFactory = HibernateUtil.getSessionFactory();

	// persisting customers to database
	public Customer insertCustomer(Customer customer) {
		Session session = sessionFactory.openSession();
		try {
			session.beginTransaction();
			session.save(customer);
			session.flush();
			session.getTransaction().commit();
		} catch (HibernateException e) {
			if (session.getTransaction() != null) {
				session.getTransaction().rollback();
			}
			e.printStackTrace();
		} finally {
			session.close();
		}
		return customer;
	}

	// persisting orders to database
	public Order insertOrder(Order order) {
		Session session = sessionFactory.openSession();
		try {
			session.beginTransaction();
			session.save(order);
			session.getTransaction().commit();
		} catch (HibernateException e) {
			if (session.getTransaction() != null) {
				session.getTransaction().rollback();
			}
			e.printStackTrace();
		} finally {
			session.close();
		}
		return order;
	}

	public Order deleteOrder(Order order) {
		Session session = sessionFactory.openSession();
		try {
			session.beginTransaction();
			session.delete(order);
			session.getTransaction().commit();
		} catch (HibernateException e) {
			if (session.getTransaction() != null) {
				session.getTransaction().rollback();
			}
			e.printStackTrace();
		} finally {
			session.close();
		}
		return order;
	}
	
	@Override
	public Customer deleteCustomer(Customer customer) {
		Session session = sessionFactory.openSession();
		try {
			session.beginTransaction();
			session.delete(customer);
			session.getTransaction().commit();
		} catch (HibernateException e) {
			if (session.getTransaction() != null) {
				session.getTransaction().rollback();
			}
			e.printStackTrace();
		} finally {
			session.close();
		}
		return customer;
	}

	public Order updateOrderPrice(Order order) {
		Session session = sessionFactory.openSession();
		try {
			session.beginTransaction();
			session.flush();
			session.saveOrUpdate(order);
			session.getTransaction().commit();
		} catch (HibernateException e) {
			if (session.getTransaction() != null) {
				session.getTransaction().rollback();
			}
			e.printStackTrace();
		} finally {
			session.close();
		}
		return order;
	}
	
	public Customer updateCustomerName(Customer customer){
		Session session = sessionFactory.openSession();
		try {
			session.beginTransaction();
			session.flush();
			session.saveOrUpdate(customer);
			session.getTransaction().commit();
		} catch (HibernateException e) {
			if (session.getTransaction() != null) {
				session.getTransaction().rollback();
			}
			e.printStackTrace();
		} finally {
			session.close();
		}
		return customer;
	}

	// persisting toppings to database.
	public void insertTopping(Topping topping) {
		Session session = sessionFactory.openSession();
		try {
			session.beginTransaction();
			session.save(topping);
			session.getTransaction().commit();
		} catch (HibernateException e) {
			if (session.getTransaction() != null) {
				session.getTransaction().rollback();
			}
			e.printStackTrace();
		} finally {
			session.close();
		}
	}

	public Topping getTopping(String toppingName) {
		String queryString = "from Topping where topping = ?";
		Session session = sessionFactory.openSession();
		Topping acquiredTopping = null;
		try {
			session.beginTransaction();
			Query query = session.createQuery(queryString);
			query.setString(0, toppingName);
			acquiredTopping = (Topping) query.uniqueResult();

		} catch (HibernateException e) {
			if (session.getTransaction() != null) {
				session.getTransaction().rollback();
			}
			e.printStackTrace();
		} finally {
			session.close();
		}

		return acquiredTopping;

	}

	public List<Order> getAllOrders() {
		String queryString = "from Order as o";
		List<Order> orders = null;
		Session session = sessionFactory.openSession();
		try {
			session.beginTransaction();
			session.flush();
			session.clear();
			Query query = session.createQuery(queryString);
			orders = query.list();
			session.getTransaction().commit();
		} catch (HibernateException e) {
			if (session.getTransaction() != null) {
				session.getTransaction().rollback();
			}
			e.printStackTrace();
		} finally {
			session.close();
		}
		return orders;

	}

	public List<Customer> getAllCustomers() {
		String queryString = "from Customer as c";
		List<Customer> customers = null;
		Session session = sessionFactory.openSession();
		try {
			session.beginTransaction();
			session.flush();
			session.clear();
			Query query = session.createQuery(queryString);
			customers = query.list();
			session.getTransaction().commit();
		} catch (HibernateException e) {
			if (session.getTransaction() != null) {
				session.getTransaction().rollback();
			}
			e.printStackTrace();
		} finally {
			session.close();
		}
		return customers;
	}

	public Customer getCustomer(String username, String password) {
		String queryString = "from Customer where username = ? AND password = ?";
		Customer acquiredCustomer = null;
		Session session = sessionFactory.openSession();
		try {
			session.beginTransaction();
			Query query = session.createQuery(queryString);
			query.setString(0, username);
			query.setString(1, password);
			acquiredCustomer = (Customer) query.uniqueResult();

		} catch (HibernateException e) {

			if (session.getTransaction() != null) {
				session.getTransaction().rollback();
			}
			e.printStackTrace();
		} finally {
			session.close();
		}
		return acquiredCustomer;
	}

	public Customer getCustomerById(int customer_id) {
		String queryString = "from Customer where userId = ?";
		Customer acquiredCustomer = null;
		Session session = sessionFactory.openSession();
		try {
			session.beginTransaction();
			Query query = session.createQuery(queryString);
			query.setInteger(0, customer_id);
			acquiredCustomer = (Customer) query.uniqueResult();

		} catch (HibernateException e) {

			if (session.getTransaction() != null) {
				session.getTransaction().rollback();
			}
			e.printStackTrace();
		} finally {
			session.close();
		}
		return acquiredCustomer;
	}

	public Order getAnOrderById(int order_id) {
		String queryString = "from Order as o where o.orderId = ?";
		Order acquiredOrder = null;
		Session session = sessionFactory.openSession();
		try {
			session.beginTransaction();
			// session.flush();
			// session.clear();
			Query query = session.createQuery(queryString);
			query.setInteger(0, order_id);
			acquiredOrder = (Order) query.uniqueResult();
			session.getTransaction().commit();
		} catch (HibernateException e) {
			if (session.getTransaction() != null) {
				session.getTransaction().rollback();
			}
			e.printStackTrace();
		} finally {
			session.close();
		}
		return acquiredOrder;

	}

	

}
