package neu.edu.dao;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import neu.edu.entity.OrderTable;
import neu.edu.entity.ProductTable;
import neu.edu.entity.SellerTable;

@Repository
public class SellerDaoImplementation implements SellerDao{

	@Autowired
	SessionFactory sessionFactory;
	
	@Override
	public SellerTable checkIfSellerLoggingIn(String username) {
		
		Session session = sessionFactory.openSession();
		
		Query query = session.createQuery("from SellerTable where username=:un");
		query.setString("un", username);
		
		List<SellerTable> sellerUserName = query.list();
		if (sellerUserName.size() > 0) {
			return sellerUserName.get(0);
		}
		try {
			session.close();
		} catch (Exception ex) {

		}
		return null;
	}

	@Override
	public boolean checkIfSellerUserNamePresent(String username) {
		
		Session session = sessionFactory.openSession();
		
		Query query = session.createQuery("from SellerTable where username=:un");
		query.setString("un", username);
		
		List<SellerTable> sellerUserName = query.list();
		if (sellerUserName.size() > 0) {
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
	public SellerTable createSeller(SellerTable seller) {
		
		Session session = sessionFactory.getCurrentSession();
		session.persist(seller);
		return seller;
	}

	@Override
	@Transactional
	public boolean deleteSellerAccount(SellerTable seller) {
	//	System.out.println("seller for deletion is : "+seller);
		Session session = sessionFactory.getCurrentSession();
		session.delete(seller);
	//	System.out.println("seller after deletion is : "+seller);
		return true;
	}

	@Override
	public List<SellerTable> getSellerFromUserName(String username) {
		Session session = sessionFactory.openSession();
		Query query = session.createQuery("from SellerTable where username=:un");
		query.setString("un", username);
		
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

	@Override
	@Transactional
	public boolean changeSellerAccountStatusRequest(SellerTable seller) {
		Session session = sessionFactory.getCurrentSession();
		session.update(seller);
		return true;
	}

	@Override
	public List<ProductTable> viewAllProducts() {
		
		Session session = sessionFactory.openSession();
		Query query = session.createQuery("from ProductTable");
		
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
	public List<ProductTable> viewSellerProducts(int id) {
		
		Session session = sessionFactory.openSession();
		Query query = session.createQuery("from ProductTable where sellerId=:id");
		query.setInteger("id", id);
		
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
	public ProductTable createProduct(ProductTable product) {
		Session session = sessionFactory.getCurrentSession();
		session.persist(product);
		return product;
	}

	@Override
	public List<ProductTable> viewAllProductsByCategory(String category) {
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
	public List<OrderTable> viewSellerOrders(int sellerId) {
		Session session = sessionFactory.openSession();
		Query query = session.createQuery("from OrderTable where sellerId=:id");
		query.setInteger("id", sellerId);
		
		List<OrderTable> orderList = query.list();
	//	System.out.println("orderList in dao is : "+orderList);
		try{
			session.close();
		}
		catch(Exception ex){
			return null;
		}
		return orderList;
	}

	@Override
	@Transactional
	public boolean changeOrderStatusRequest(OrderTable order) {
		Session session = sessionFactory.getCurrentSession();
		session.update(order);
		return true;
	}

	@Override
	public List<OrderTable> getOrderListbyOrderId(int orderId) {
		Session session = sessionFactory.openSession();
		Query query = session.createQuery("from OrderTable where orderId=:id");
		query.setInteger("id", orderId);
		//query.setInteger("id1", sellerId);
		
		List<OrderTable> orderList = query.list();
	//	System.out.println("orderList in dao is : "+orderList);
		try{
			session.close();
		}
		catch(Exception ex){
			return null;
		}
		return orderList;
	}

	
}
