//package sjsu.pizza;
//
//import org.hibernate.*;
//import edu.cs157b.util.HibernateUtil;
//
//public class PizzaDAO {
//    private static SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
//	
//	public Customer create(Customer c)
//	{   Session session = sessionFactory.openSession();
//		session.beginTransaction();
//		
//	    session.save(c);
//	   
//	    session.getTransaction().commit();
//	    session.close();
//	    return c;
//	}
//}
