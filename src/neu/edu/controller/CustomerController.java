package neu.edu.controller;

import org.apache.log4j.Logger;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import neu.edu.controller.data.OrderedProducts;
import neu.edu.controller.data.PaymentInformationRequest;
import neu.edu.controller.data.RestLogicalErrorException;
import neu.edu.entity.OrderTable;
import neu.edu.entity.ProductTable;
import neu.edu.service.CustomerService;

@Controller
@Path("/customer")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class CustomerController {

	private Logger logger = Logger.getLogger(CustomerController.class);
	
	@Autowired
	CustomerService customerService;

	@GET
	@Path("/{category}/viewproducts")
	public Response viewSellerProductsofBestBuy(@PathParam("category") String category) {
		
		List<ProductTable> allProductList = customerService.listProductsBasedOnCategory(category);

		if (allProductList != null) {
			// System.out.println("Products exists!");
			logger.info("Customer viewed products successfully");
			return Response.ok().status(200).entity(allProductList).build();
		} else {
			// System.out.println("Products does not exists!");
			logger.error("Customer view products Failed due to no products");
			return Response.ok().status(422).entity(new RestLogicalErrorException("No Products")).build();
		}
	}

	@POST
	@Path("{id}/payment")
	public Response enterPaymentInformation(PaymentInformationRequest paymentInformationRequest,
			@PathParam("id") String id) throws RestLogicalErrorException {
		try{
			if (customerService.enterPaymentDetails(paymentInformationRequest, id)) {
				logger.info("Customer Payment done Successfully");
				return Response.ok().build();
			}
		}
		catch(RestLogicalErrorException re){
			logger.error("Customer Payment failed due to "+re.getResponseError().getMessage());
			return Response.ok().status(422).entity(re.getResponseError()).build();
		}
			logger.error("Customer Payment failed due to Unknown error");
			return Response.ok().status(422).entity(new RestLogicalErrorException("Unknown Error. Please try again"))
					.build();	
	}

	@POST
	@Path("{id}/order")
	public Response orderPlaced(OrderedProducts[] orderedProducts, @PathParam("id") String id) {

		// System.out.println("In orderPlaced Customer controller");
		try{
			if (customerService.orderPlacedDetails(orderedProducts, Integer.parseInt(id))) {
				logger.info("Customer placed order successfully");
				return Response.ok().build();
			}
		}
		catch(RestLogicalErrorException re){
			logger.error("Customer placing order Failed due to "+re.getResponseError().getMessage());
			return Response.ok().status(422).entity(re.getResponseError()).build();
		}

		logger.error("Customer placing order Failed due to Unknown error");
		return Response.ok().status(422).entity(new RestLogicalErrorException("Unknown Error. Please try again"))
				.build();

	}

	@GET
	@Path("{id}/orderhistory")
	public Response viewOrderHistory(@PathParam("id") String id) {

		// System.out.println("In orderPlaced Customer controller");
		List<OrderTable> orderList = customerService.viewOrderHistory(Integer.parseInt(id));

		if (orderList != null) {
			// System.out.println("Order exists!");
			logger.info("Customer Viewed History Successful");
			return Response.ok().status(200).entity(orderList).build();
		} else {
			// System.out.println("Order does not exists!");
			logger.error("Customer could not view orders due to no orders");
			return Response.ok().status(422).entity(new RestLogicalErrorException("No Orders")).build();
		}
	}
}
