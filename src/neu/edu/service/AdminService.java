package neu.edu.service;

import java.util.List;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import neu.edu.controller.data.ChangeUserStatusRequest;
import neu.edu.controller.data.RegistrationRequest;
import neu.edu.controller.data.RestLogicalErrorException;
import neu.edu.dao.AdminDao;
import neu.edu.dao.CustomerDao;
import neu.edu.dao.PersonDao;
import neu.edu.dao.SellerDao;
import neu.edu.entity.CustomerTable;
import neu.edu.entity.Person;
import neu.edu.entity.SellerTable;

@Service
public class AdminService {

	@Autowired
	private AdminDao adminDao;

	@Autowired
	private SellerDao sellerDao;

	@Autowired
	private CustomerDao customerDao;

	@Autowired
	private Person person;

	@Autowired
	private PersonDao personDao;

	@Autowired
	private SellerTable seller;

	public boolean registerSeller(RegistrationRequest registrationRequest) throws RestLogicalErrorException {

		if (registrationRequest.getUsername().isEmpty() || registrationRequest.getPassword().isEmpty() || registrationRequest.getFirstName().isEmpty()
				|| registrationRequest.getLastName().isEmpty() || registrationRequest.getEmail().isEmpty()) {
			throw new RestLogicalErrorException("Registration Parameters incomplete!");
		}
		if (adminDao.checkIfAdminUserNamePresent(registrationRequest.getUsername())
				|| sellerDao.checkIfSellerUserNamePresent(registrationRequest.getUsername())
				|| customerDao.checkIfCustomerUserNamePresent(registrationRequest.getUsername())) {
			throw new RestLogicalErrorException("Username already Exists! Enter another Username!");
		} else {
			// System.out.println("Starting to create new person");

			person.setPersonId(new Random().nextInt(100000));
			person.setTitle(registrationRequest.getTitle());
			person.setFirstname(registrationRequest.getFirstName());
			person.setLastname(registrationRequest.getLastName());
			person.setEmailid(registrationRequest.getEmail());
			// person.setPhoneno(registrationRequest.getPhoneNo());
			person.setDob(registrationRequest.getDateOfBirth());
			person.setOccupationtype(registrationRequest.getOccupationType());

			seller.setSellerId(new Random().nextInt(1000));
			seller.setUsername(registrationRequest.getUsername());
			seller.setPassword(registrationRequest.getPassword());
			seller.setStatus("enable");
			seller.setUsertype("seller");
			seller.setPersonId(person.getPersonId());

			personDao.createPerson(person);
			sellerDao.createSeller(seller);

			return true;

		}
	}

	public List<CustomerTable> listAllCustomers() {

		List<CustomerTable> customerList = adminDao.getCustomerList();
		// System.out.println("Customer list in service is : "+customerList);
		return customerList;
	}

	public List<SellerTable> listAllSellers() {

		List<SellerTable> sellerList = adminDao.getSellerTableList();
		// System.out.println("SellerTable list in service is : "+sellerList);
		return sellerList;
	}

	public boolean deleteUserAccount(ChangeUserStatusRequest deactivateUserRequest) {

		if (sellerDao.checkIfSellerUserNamePresent(deactivateUserRequest.getUsername())) {
			List<SellerTable> seller = sellerDao.getSellerFromUserName(deactivateUserRequest.getUsername());
			List<Person> person = personDao.getPerson(seller.get(0).getPersonId());
			Person p = person.get(0);

			boolean flag = sellerDao.deleteSellerAccount(seller.get(0));
			// System.out.println("Seller account deactivated : "+flag);
			personDao.deletePersonAccount(p);
			// System.out.println("Delete done!!!");
			return flag;
		} else if (customerDao.checkIfCustomerUserNamePresent(deactivateUserRequest.getUsername())) {
			// System.out.println("Customer account");

			List<CustomerTable> customer = customerDao.getCustomerFromUserName(deactivateUserRequest.getUsername());
			List<Person> person = personDao.getPerson(customer.get(0).getPersonId());
			Person p = person.get(0);

			boolean flag = customerDao.deleteCustomerAccount(customer.get(0));
			// System.out.println("Customer account deactivated : "+flag);
			personDao.deletePersonAccount(p);
			// System.out.println("Delete done!!!");
			return flag;
		}
		return false;
	}

	public boolean deactivateUserAccount(ChangeUserStatusRequest deactivateUserRequest) {
		if (sellerDao.checkIfSellerUserNamePresent(deactivateUserRequest.getUsername())) {
			List<SellerTable> sellerList = sellerDao.getSellerFromUserName(deactivateUserRequest.getUsername());
			SellerTable seller = sellerList.get(0);
			seller.setStatus("disable");

			boolean flag = sellerDao.changeSellerAccountStatusRequest(seller);
			return flag;
		} else if (customerDao.checkIfCustomerUserNamePresent(deactivateUserRequest.getUsername())) {
			List<CustomerTable> customerList = customerDao.getCustomerFromUserName(deactivateUserRequest.getUsername());
			CustomerTable customer = customerList.get(0);
			customer.setStatus("disable");

			boolean flag = customerDao.changeCustomerAccountStatusRequest(customer);
			return flag;
		}

		return false;
	}

	public boolean activateUserAccount(ChangeUserStatusRequest changeUserStatusRequest) {
		if (sellerDao.checkIfSellerUserNamePresent(changeUserStatusRequest.getUsername())) {
			List<SellerTable> sellerList = sellerDao.getSellerFromUserName(changeUserStatusRequest.getUsername());
			SellerTable seller = sellerList.get(0);
			seller.setStatus("enable");

			boolean flag = sellerDao.changeSellerAccountStatusRequest(seller);
			return flag;
		} else if (customerDao.checkIfCustomerUserNamePresent(changeUserStatusRequest.getUsername())) {
			List<CustomerTable> customerList = customerDao
					.getCustomerFromUserName(changeUserStatusRequest.getUsername());
			CustomerTable customer = customerList.get(0);
			customer.setStatus("enable");

			boolean flag = customerDao.changeCustomerAccountStatusRequest(customer);
			return flag;
		}

		return false;
	}

}
