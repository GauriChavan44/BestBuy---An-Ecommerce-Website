package neu.edu.dao;

import java.util.List;
import java.util.Random;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import neu.edu.entity.*;

@Repository
public class CustomerDaoImplementation implements CustomerDao{

	@Autowired
	SessionFactory sessionFactory;
	
	@Autowired
	Person person;
	
	@Autowired
	CustomerTable customer;

	@Override
	public boolean checkIfCustomerUserNamePresent(String username) {
		
		Session session = sessionFactory.openSession();
		
		Query query = session.createQuery("from CustomerTable where username=:un");
		query.setString("un", username);
		
		List<CustomerTable> customers = query.list();
		if (customers.size() > 0) {
			return true;
		}
		try {
			session.close();
		} catch (Exception ex) {

		}
		return false;
	}

	@Override
	@Transactional
	public CustomerTable createCustomer(CustomerTable customer) {
		Session session = sessionFactory.getCurrentSession();
		session.persist(customer);
		return customer;
	}

	//Used while checking if user with same username is created
	@Override
	public CustomerTable checkIfCustomerLoggingIn(String username) {
		Session session = sessionFactory.openSession();
		
		Query query = session.createQuery("from CustomerTable where username=:un");
		query.setString("un", username);
	//	System.out.println("checking if it is customer in dao");
		List<CustomerTable> customerUserName = query.list();
	//	System.out.println("customer with username : "+username +" is : "+customerUserName);
		if (customerUserName.size() > 0) {
			return customerUserName.get(0);
		}
		try {
			session.close();
		} catch (Exception ex) {

		}
		return null;
	}

	@Override
	@Transactional
	public boolean deleteCustomerAccount(CustomerTable customer) {
		Session session = sessionFactory.getCurrentSession();
		session.delete(customer);
		return true;
	}

	@Override
	public List<CustomerTable> getCustomerFromUserName(String username) {
		Session session = sessionFactory.openSession();
		Query query = session.createQuery("from CustomerTable where username=:un");
		query.setString("un", username);
		
		List<CustomerTable> customerList = query.list();
	//	System.out.println("customerList in dao is : "+customerList);
		try{
			session.close();
		}
		catch(Exception ex){
			return null;
		}
		return customerList;
	}

	@Override
	@Transactional
	public boolean changeCustomerAccountStatusRequest(CustomerTable customer) {
		Session session = sessionFactory.getCurrentSession();
		session.update(customer);
		return true;
	}

	@Override
	public List<ProductTable> viewProductsByCategory(String category) {
		Session session = sessionFactory.openSession();
		Query query = session.createQuery("from ProductTable where category=:ct");
		query.setString("ct", category);
		
		List<ProductTable> productList = query.list();
	//	System.out.println("productList in dao is : "+productList);
		try{
			session.close();
		}
		catch(Exception ex){
			return null;
		}
		return productList;
	}

	@Override
	@Transactional
	public void createPaymentDetails(PaymentInformationTable payment) {
		Session session = sessionFactory.getCurrentSession();
		session.persist(payment);
	}


	@Override
	@Transactional
	public void createOrder(OrderTable order) {
		// TODO Auto-generated method stub
	//	System.out.println("creating order");
		Session session = sessionFactory.getCurrentSession();
		session.persist(order);
	}

	@Override
	@Transactional
	public void createOrderProduct(OrderProductTable orderproduct) {
		// TODO Auto-generated method stub
	//	System.out.println("creating orderproduct");
		Session session = sessionFactory.getCurrentSession();
		session.persist(orderproduct);
	}

	@Override
	public List<OrderTable> getOrderHistoryOfCustomer(int customerId) {
		
		Session session = sessionFactory.openSession();
		Query query = session.createQuery("from OrderTable where customerId=:ct");
		query.setInteger("ct", customerId);
		
		List<OrderTable> orderList = query.list();
	//	System.out.println("productList in dao is : "+orderList);
		try{
			session.close();
		}
		catch(Exception ex){
			return null;
		}
		return orderList;
	}

	@Override
	public int getPersonIdOfCustomer(int customerId) {
Session session = sessionFactory.openSession();
		
		Query query = session.createQuery("from CustomerTable where customerId=:un");
		query.setInteger("un", customerId);
		
		List<CustomerTable> customers = query.list();
		if (customers.size() > 0) {
			return customers.get(0).getPersonId();
		}
		try {
			session.close();
		} catch (Exception ex) {

		}
		return -1;
	}





}
