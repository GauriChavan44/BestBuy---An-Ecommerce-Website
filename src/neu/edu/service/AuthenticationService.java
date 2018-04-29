package neu.edu.service;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import neu.edu.controller.data.RestLogicalErrorException;
import neu.edu.controller.data.UserSession;
import neu.edu.dao.AdminDao;
import neu.edu.dao.CustomerDao;
import neu.edu.dao.SellerDao;
import neu.edu.entity.AdminTable;
import neu.edu.entity.CustomerTable;
import neu.edu.entity.SellerTable;

@Service
public class AuthenticationService {

	@Autowired
	private CustomerDao customerDao;

	@Autowired
	private AdminDao adminDao;

	@Autowired
	private SellerDao sellerDao;

	public UserSession checkWhichUserLoggingIn(String username, String password) throws RestLogicalErrorException {
		UserSession userSession = null;
		System.out.println("Inside authentication service");

		if (adminDao.checkIfAdminLoggingIn(username) instanceof AdminTable) {
			AdminTable admin = adminIsLoggingIn(username);
			if (admin != null) {
				System.out.println("Inside admin in  service");
				userSession = new UserSession();
				userSession.setAdminId(admin.getAdminId());
				userSession.setRole("admin");
				userSession.setPersonId(admin.getPersonid());
				userSession.setUsername(admin.getUsername());
				userSession.setSuccess(true);

			} else {
				RestLogicalErrorException authResponseErr = new RestLogicalErrorException("Invalid User");
				throw authResponseErr;
			}
			return userSession;
		}

		else if (customerDao.checkIfCustomerLoggingIn(username) instanceof CustomerTable) {

			CustomerTable customer = customerIsLoggingIn(username);
			if (customer != null) {
				userSession = new UserSession();
				userSession.setCustomerId(customer.getCustomerId());
				userSession.setRole("customer");
				userSession.setPersonId(customer.getPersonId());
				userSession.setUsername(customer.getUsername());
				userSession.setSuccess(true);

			} else {
				RestLogicalErrorException authResponseErr = new RestLogicalErrorException("Invalid User");
				throw authResponseErr;
			}
			return userSession;
		}

		else if (sellerDao.checkIfSellerLoggingIn(username) instanceof SellerTable) {
			// System.out.println("checking if user is seller");
			SellerTable seller = sellerIsLoggingIn(username);
			if (seller != null) {
				userSession = new UserSession();
				userSession.setSellerId(seller.getSellerId());
				userSession.setRole("seller");
				userSession.setPersonId(seller.getPersonId());
				userSession.setUsername(seller.getUsername());
				userSession.setSuccess(true);

			} else {
				RestLogicalErrorException authResponseErr = new RestLogicalErrorException("Invalid User");
				throw authResponseErr;
			}
			return userSession;
		}
		return userSession;

	}

	public AdminTable adminIsLoggingIn(String username) {
		// System.out.println("Admin has logged in");
		AdminTable admin = adminDao.checkIfAdminLoggingIn(username);
		return admin;
	}

	public CustomerTable customerIsLoggingIn(String username) {
		// System.out.println("Customer has logged in");
		CustomerTable customer = customerDao.checkIfCustomerLoggingIn(username);
		return customer;
	}

	public SellerTable sellerIsLoggingIn(String username) {
		// System.out.println("Seller has logged in");
		SellerTable seller = sellerDao.checkIfSellerLoggingIn(username);
		return seller;
	}

	/**
	 * sessionId = MD5(userId + ipAddr) + prngRandomNumber
	 * 
	 * @param userSession
	 * @return
	 */
	public String generateAuthenticationToken(UserSession userSession, String ipAddress) {
		String token = null;

		try {
			String prngRandomNumber = null;
			// Algo for generating Random Uniquie Number - Using Cryptograpy
			SecureRandom prng = SecureRandom.getInstance("SHA1PRNG");

			// Algo for generating hash Generating - One way function
			MessageDigest sha = MessageDigest.getInstance("SHA-1");

			// Generation A prngRandomNumber
			String randomNum = new Integer(prng.nextInt()).toString();

			byte[] result = sha.digest(randomNum.getBytes());
			prngRandomNumber = convertByteArrayToHexString(result);
			System.out.println("Random number: " + randomNum);
			System.out.println("Message digest SHA-1: " + prngRandomNumber);

			MessageDigest md5 = MessageDigest.getInstance("MD5");
			String toBeHashed = userSession.getPersonId() + ipAddress + userSession.getUsername();
			byte[] md5HashByte = md5.digest(toBeHashed.getBytes());

			String md5hash = convertByteArrayToHexString(md5HashByte);

			System.out.println("Message digest MD5: " + prngRandomNumber);
			token = md5hash + prngRandomNumber;

		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			System.out.println("SHA1PRNG,SHA-1 algo Doesnt exists");
		}

		if (token == null) {
			token = UUID.randomUUID().toString();
		}
		System.out.println("Token: " + token);

		return token;
	}

	private String convertByteArrayToHexString(byte[] arrayBytes) {
		StringBuffer stringBuffer = new StringBuffer();
		for (int i = 0; i < arrayBytes.length; i++) {
			stringBuffer.append(Integer.toString((arrayBytes[i] & 0xff) + 0x100, 16).substring(1));
		}
		return stringBuffer.toString();
	}
}
