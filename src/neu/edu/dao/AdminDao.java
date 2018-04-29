package neu.edu.dao;

import java.util.List;

import neu.edu.entity.*;

public interface AdminDao {

	public AdminTable checkIfAdminLoggingIn(String username);

	public boolean checkIfAdminUserNamePresent(String username);

	public List<CustomerTable> getCustomerList();

	public List<SellerTable> getSellerTableList();

}
