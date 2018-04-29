package neu.edu.dao;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import neu.edu.entity.*;

@Repository
public class AdminDaoImplementation implements AdminDao{
	
	@Autowired
	SessionFactory sessionFactory;

	public AdminTable checkIfAdminLoggingIn(String username) {
		
		Session session = sessionFactory.openSession();
		
		Query query = session.createQuery("from AdminTable where username=:un");
		query.setString("un", username);
		
		List<AdminTable> adminUserName = query.list();
		if (adminUserName.size() > 0) {
			return adminUserName.get(0);
		}
		try {
			session.close();
		} catch (Exception ex) {

		}
		return null;
		
	}
	
	//Used while checking if user with same username is created
	@Override
	public boolean checkIfAdminUserNamePresent(String username) {
		
		Session session = sessionFactory.openSession();
		
		Query query = session.createQuery("from AdminTable where username=:un");
		query.setString("un", username);
		
		List<AdminTable> adminUserName = query.list();
		if (adminUserName.size() > 0) {
			return true;
		}
		try {
			session.close();
		} catch (Exception ex) {

		}
		return false;
	}


	@Override
	public List<CustomerTable> getCustomerList() {
		
		Session session = sessionFactory.openSession();
		Query query = session.createQuery("from CustomerTable");
		
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
	public List<SellerTable> getSellerTableList() {
		Session session = sessionFactory.openSession();
		Query query = session.createQuery("from SellerTable");
		
		List<SellerTable> sellerList = query.list();
	//	System.out.println("sellerList in dao is : "+sellerList);
		try{
			session.close();
		}
		catch(Exception ex){
			return null;
		}
		return sellerList;
	}

}
