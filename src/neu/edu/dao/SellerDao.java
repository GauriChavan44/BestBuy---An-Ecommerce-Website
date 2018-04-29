package neu.edu.dao;

import java.util.List;

import neu.edu.entity.OrderTable;
import neu.edu.entity.ProductTable;
import neu.edu.entity.SellerTable;

public interface SellerDao {

	public SellerTable checkIfSellerLoggingIn(String username);

	public boolean checkIfSellerUserNamePresent(String username);// to avoid
																	// creation
																	// of user
																	// with same
																	// username

	public SellerTable createSeller(SellerTable seller);// creating new seller
														// account

	public boolean deleteSellerAccount(SellerTable seller);// deleting seller
															// account

	public List<SellerTable> getSellerFromUserName(String username);// get list
																	// of all
																	// user with
																	// username

	public boolean changeSellerAccountStatusRequest(SellerTable seller);// for
																		// activating
																		// and
																		// deactivating
																		// users

	public List<ProductTable> viewAllProducts();// seller can view all products

	public List<ProductTable> viewSellerProducts(int id);

	public ProductTable createProduct(ProductTable product);

	public List<ProductTable> viewAllProductsByCategory(String category);

	public List<OrderTable> viewSellerOrders(int sellerId);

	public boolean changeOrderStatusRequest(OrderTable order);

	public List<OrderTable> getOrderListbyOrderId(int orderId);

}
