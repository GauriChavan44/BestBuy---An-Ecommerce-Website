package neu.edu.dao;

import java.util.List;

import neu.edu.entity.CustomerTable;
import neu.edu.entity.OrderProductTable;
import neu.edu.entity.OrderTable;
import neu.edu.entity.PaymentInformationTable;
import neu.edu.entity.ProductTable;

public interface CustomerDao {

	public boolean checkIfCustomerUserNamePresent(String username);

	public CustomerTable checkIfCustomerLoggingIn(String username);// checks if
																	// customer
																	// is
																	// logging
																	// in

	public CustomerTable createCustomer(CustomerTable customer);

	public boolean deleteCustomerAccount(CustomerTable customer);

	public boolean changeCustomerAccountStatusRequest(CustomerTable customer);

	public List<CustomerTable> getCustomerFromUserName(String username);

	public List<ProductTable> viewProductsByCategory(String category);

	public void createPaymentDetails(PaymentInformationTable payment);

	public void createOrder(OrderTable order);

	public void createOrderProduct(OrderProductTable orderproduct);

	public List<OrderTable> getOrderHistoryOfCustomer(int customerId);

	public int getPersonIdOfCustomer(int customerId);

}
