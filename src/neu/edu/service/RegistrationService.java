package neu.edu.service;

import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import neu.edu.controller.data.RegistrationRequest;
import neu.edu.controller.data.RestLogicalErrorException;
import neu.edu.dao.AdminDao;
import neu.edu.dao.CustomerDao;
import neu.edu.dao.PersonDao;
import neu.edu.dao.SellerDao;
import neu.edu.entity.CustomerTable;
import neu.edu.entity.Person;

@Service
public class RegistrationService {

	@Autowired
	private AdminDao adminDao;
	
	@Autowired
	private CustomerDao customerDao;
	
	@Autowired
	private SellerDao sellerDao;
	
	@Autowired
	private PersonDao personDao;
	
	@Autowired
	private Person person;
	
	@Autowired
	private CustomerTable customer;

	public boolean register(RegistrationRequest registrationRequest) throws RestLogicalErrorException{

	//	System.out.println("In register service");
		if(registrationRequest.getUsername().isEmpty() || registrationRequest.getPassword().isEmpty() ||
				registrationRequest.getFirstName().isEmpty() || registrationRequest.getLastName().isEmpty() ||
				registrationRequest.getEmail().isEmpty()){
			throw new RestLogicalErrorException("Registration Parameters incomplete!");
		}
		if(adminDao.checkIfAdminUserNamePresent(registrationRequest.getUsername()) ||
				sellerDao.checkIfSellerUserNamePresent(registrationRequest.getUsername())||
				customerDao.checkIfCustomerUserNamePresent(registrationRequest.getUsername())){
			throw new RestLogicalErrorException("Username already Exists! Enter another Username!");
		}
		else{
			//Person person = new Person();
	//		System.out.println("Starting to create new person");
			
			person.setPersonId(new Random().nextInt(100000));
			person.setTitle(registrationRequest.getTitle());
			person.setFirstname(registrationRequest.getFirstName());
			person.setLastname(registrationRequest.getLastName());
			person.setEmailid(registrationRequest.getEmail());
			//person.setPhoneno(registrationRequest.getPhoneNo());
			person.setDob(registrationRequest.getDateOfBirth());
			person.setOccupationtype(registrationRequest.getOccupationType());
			
	//		System.out.println("Created new person with name : "+person.getFirstname());
			//CustomerTable customer = new CustomerTable();
	//		System.out.println("Starting to create new customer");
			
			customer.setCustomerId(new Random().nextInt(100));
			customer.setUsername(registrationRequest.getUsername());
			customer.setPassword(registrationRequest.getPassword());
			customer.setStatus("enable");
			customer.setUsertype("customer");
			customer.setPersonId(person.getPersonId());
	//		System.out.println("Created new customer with name : "+customer.getUsername());
			
			personDao.createPerson(person);
			customerDao.createCustomer(customer);
			
			return true;
		}
	}
}
