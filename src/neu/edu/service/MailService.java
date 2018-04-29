package neu.edu.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Service;

import neu.edu.dao.CustomerDao;
import neu.edu.dao.PersonDao;

@Service
public class MailService {

	 @Autowired
	 private MailSender mailSender;
	 
	 @Autowired
	private CustomerDao customerDao;
	 

	 @Autowired
		private PersonDao personDao;
	/**
	 * This method will send compose and send the message
	 */
	public void sendMail(String to, String subject, String body) {
		SimpleMailMessage message = new SimpleMailMessage();
		message.setTo(to);
		message.setSubject(subject);
		message.setText(body);
		mailSender.send(message);
	}



	public String getCustomerEmailId(int customerId) {
		int personId = customerDao.getPersonIdOfCustomer(customerId);
		if(personId != -1){
			String emailId = personDao.getEmailId(personId);
			if(emailId !=null){
				return emailId;
			}
		}
		
		return null;
	}
	
}
