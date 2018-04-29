package neu.edu.controller;

import org.apache.log4j.Logger;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import neu.edu.controller.data.RegistrationRequest;
import neu.edu.controller.data.RestLogicalErrorException;
import neu.edu.service.RegistrationService;

@Controller
@Path("/registration")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class RegistrationController {
	
	private Logger logger = Logger.getLogger(RegistrationController.class);
	
	@Autowired
	private RegistrationService registrationService;
	
	@POST
	public Response registerCustomer(RegistrationRequest registrationRequest){
	//	System.out.println("in register controller");
		try{
			if(registrationService.register(registrationRequest)){
				logger.info("Customer Registration Successful");
				return Response.ok().build();
			}
		}
		catch(RestLogicalErrorException re){
			logger.error("Customer Registration Failed due to "+re.getResponseError().getMessage());
			return Response.ok().status(422).entity(re.getResponseError()).build();
		}
		
		logger.error("Customer Registration Failed due to unknown error");
		return Response.ok().status(422).entity(new RestLogicalErrorException("Unknown Error. Please try again")).build();
	}
	
	
}
