package neu.edu.controller;
import org.apache.log4j.Logger;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import neu.edu.controller.data.ChangeUserStatusRequest;
import neu.edu.controller.data.RegistrationRequest;
import neu.edu.controller.data.RestLogicalErrorException;
import neu.edu.entity.*;
import neu.edu.service.AdminService;

@Controller
@Path("/admin")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class AdminController {

	private Logger logger = Logger.getLogger(AdminController.class);

	@Autowired
	AdminService adminService;

	@POST
	@Path("/addnewseller")
	public Response addNewSeller(RegistrationRequest registrationRequest) {

		try {
			if (adminService.registerSeller(registrationRequest)) {
				logger.info("New Seller added");
				return Response.ok().build();
			}
		} catch (RestLogicalErrorException re) {
				logger.error("New Seller Add Failed "+re.getResponseError().getMessage());
			return Response.ok().status(422).entity(re.getResponseError()).build();
		}

		return Response.ok().status(422).entity(new RestLogicalErrorException("Unknown Error. Please try again"))
				.build();
	}

	@GET
	@Path("/viewcustomers")
	public Response viewAllCustomers() {

		List<CustomerTable> customerList = adminService.listAllCustomers();
		//System.out.println("Customer list in controller is : " + customerList);
		if (customerList != null) {
		//	System.out.println("flag is null");
			logger.info("View Customer Successful");
			return Response.ok().status(200).entity(customerList).build();
		} else {
		//	System.out.println("flag is not null");
			logger.error("View Customer Failed due to no customers");
			return Response.ok().status(422).entity(new RestLogicalErrorException("No Customers")).build();
		}
	}

	@GET
	@Path("/viewsellers")
	public Response viewAllSellers() {
		List<SellerTable> allSellerList = adminService.listAllSellers();
	//	System.out.println("Seller list in controller is : " + allSellerList);
		if (allSellerList != null) {
		//	System.out.println("Seller exists!");
			logger.info("View Sellers Successful");
			return Response.ok().status(200).entity(allSellerList).build();
		} else {
		//	System.out.println("Seller does not exists!");
			logger.error("View Seller Failed due to no sellers");
			return Response.ok().status(422).entity(new RestLogicalErrorException("No Sellers")).build();
		}
	}

	@POST
	@Path("/deleteuser")
	public Response deleteUser(ChangeUserStatusRequest deactivateUserRequest) {

		if (adminService.deleteUserAccount(deactivateUserRequest)) {
		//	System.out.println("Username deleted!");
			return Response.ok().build();
		} else {
		//	System.out.println("Username not found! So account not deactivated");
			return Response.ok().status(422)
					.entity(new RestLogicalErrorException("User Name not found or incorrect! Enter again!")).build();
		}
	}

	@POST
	@Path("/deactivateuser")
	public Response deactivateUser(ChangeUserStatusRequest deactivateUserRequest) {

		if (adminService.deactivateUserAccount(deactivateUserRequest)) {
		//	System.out.println("Username deactivated!");
			logger.info("User deactivated successfully");
			return Response.ok().build();
		} else {
		//	System.out.println("Username still Active!");
			logger.error("User deactivation failed due to User Name not found or incorrect");
			return Response.ok().status(422)
					.entity(new RestLogicalErrorException("User Name not found or incorrect! Enter again!")).build();
		}
	}

	@POST
	@Path("/activateuser")
	public Response activateUser(ChangeUserStatusRequest changeUserStatusRequest) {

		if (adminService.activateUserAccount(changeUserStatusRequest)) {
		//	System.out.println("Username activated!");
			logger.info("User activated successfully");
			return Response.ok().build();
		} else {
		//	System.out.println("Username still Not Active!");
			logger.error("User activation failed due to User Name not found or incorrect");
			return Response.ok().status(422)
					.entity(new RestLogicalErrorException("User Name not found or incorrect! Enter again!")).build();
		}
	}

}
