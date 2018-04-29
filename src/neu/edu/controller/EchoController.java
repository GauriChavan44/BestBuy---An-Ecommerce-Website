
package neu.edu.controller;

import java.util.Date;

import javax.annotation.security.PermitAll;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import neu.edu.service.MailService;

@Controller
@Path("/echo")
@Consumes(MediaType.APPLICATION_JSON)
public class EchoController {
	
	private Logger logger = Logger.getLogger(EchoController.class);
 
	@Autowired
	private MailService mailService;
	
	@GET
	public String getDate(@QueryParam("msg") String msg){
		
		logger.debug("Echo Called");
		return new Date().toString() + ";msg="+msg;
		
	}
	
	
	@GET
	@PermitAll
	@Path("/public/{id}")
	public String getDatePublic(@PathParam("id") String id){//,@QueryParam("msg") String msg){
		System.out.println("inside public method");
		String emailId = mailService.getCustomerEmailId(Integer.parseInt(id));
		if(emailId==null){
			System.out.println("email null");
			logger.warn("User has not specified email id");
			return null;
		}
		else{
			System.out.println("email not null");
			logger.info("Echo Called");
			logger.trace("Echo Called");
			logger.debug("Echo Called");
			logger.error("");
			logger.warn("");

			mailService.sendMail(emailId, "Shipping of Order", "Your Best Buy Order has been shipped! Congratulations!");
			return new Date().toString() + ";msg=";
		}
		
		
	}
	
	@GET
	@Path("/deny")
	public String getDateDeny(@QueryParam("msg") String msg){
		return new Date().toString() + ";msg="+msg;
		
	}

}
