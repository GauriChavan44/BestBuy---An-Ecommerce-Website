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

import neu.edu.controller.data.AddNewProductRequest;
import neu.edu.controller.data.RestLogicalErrorException;
import neu.edu.controller.data.UpdateOrderStatusRequest;
import neu.edu.entity.OrderTable;
import neu.edu.entity.ProductTable;
import neu.edu.service.SellerService;

@Controller
@Path("/seller")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class SellerController {

	private Logger logger = Logger.getLogger(SellerController.class);
	
	@Autowired
	SellerService sellerService;
	
	@GET
	@Path("/{id}/viewproducts")
	public Response viewSellerProductsofBestBuy(@PathParam("id") String id){
	//	System.out.println("id is : "+id);
		List<ProductTable> allProductList = sellerService.listSellerProducts(Integer.parseInt(id));
	//	System.out.println("allProductList list in controller is : "+allProductList);
		if(allProductList != null){
	//		System.out.println("Products exists!");
			logger.info("Seller viewed products successfully");
			return Response.ok().status(200).entity(allProductList).build();
		}
		else{
	//		System.out.println("Products does not exists!");
			logger.error("Seller view products failed due to no products");
			return Response.ok().status(422).entity(new RestLogicalErrorException("No Products")).build();
		}
	}
	
	@GET
	@Path("/{id}/vieworders")
	public Response viewSellerOrdersofBestBuy(@PathParam("id") String id){
	//	System.out.println("id is : "+id);
		List<OrderTable> allOrderList = sellerService.listSellerOrders(Integer.parseInt(id));
	//	System.out.println("allOrderList list in controller is : "+allOrderList);
		if(allOrderList != null){
	//		System.out.println("Products exists!");
			logger.info("Seller viewed orders successfully");
			return Response.ok().status(200).entity(allOrderList).build();
		}
		else{
	//		System.out.println("Products does not exists!");
			logger.error("Seller view orders failed due to no orders");
			return Response.ok().status(422).entity(new RestLogicalErrorException("No Orders")).build();
		}
	}

	
	@GET
	@Path("/viewAllProducts")
	public Response viewAllProductsofBestBuy(){
		List<ProductTable> allProductList = sellerService.listAllProducts();
	//	System.out.println("allProductList list in controller is : "+allProductList);
		if(allProductList != null){
	//		System.out.println("Products exists!");
			logger.info("All products displayed successfully");
			return Response.ok().status(200).entity(allProductList).build();
		}
		else{
	//		System.out.println("Products does not exists!");
			logger.error("All products display failed due to No products");
			return Response.ok().status(422).entity(new RestLogicalErrorException("No Products")).build();
		}
	}
	
	@GET
	@Path("/viewProductsByCategory/{category}")
	public Response viewProductsofBestBuy(@PathParam("category") String category){
		List<ProductTable> allProductList = sellerService.listAllProductsByCategory(category);
	//	System.out.println("allProductList list in controller is : "+allProductList);
		if(allProductList != null){
	//		System.out.println("Products exists!");
			logger.info("Seller viewed products by category successfully");
			return Response.ok().status(200).entity(allProductList).build();
		}
		else{
	//		System.out.println("Products does not exists!");
			logger.error("Seller viewed products by category Failed due to no products");
			return Response.ok().status(422).entity(new RestLogicalErrorException("No Products")).build();
		}
	}
	
	@POST
	@Path("/{id}/addnewproduct")
	public Response addNewProduct(AddNewProductRequest addNewProductRequest,@PathParam("id") String id){
		
		try{
			if(sellerService.addNewProductForBestBuy(addNewProductRequest,Integer.parseInt(id))){
	//			System.out.println("Seller added new product successfully!");
				logger.info("Seller added new product Successfully");
				return Response.ok().build();
			}
		}
		catch(RestLogicalErrorException re){
			logger.error("Seller adding new product Failed due to "+re.getResponseError().getMessage());
			return Response.ok().status(422).entity(re.getResponseError()).build();
		}

		logger.error("Seller adding new product Failed due to unknown reason");
		return Response.ok().status(422).entity(new RestLogicalErrorException("Unknown Error. Please try again")).build();
	}
	
	@POST
	@Path("/updatestatus")
	public Response updateOrderStatus(UpdateOrderStatusRequest updateOrderStatusRequest){
		try{
			if(sellerService.updateStatusOfOrder(updateOrderStatusRequest)){
				//		System.out.println("Status of Order Updated!");
						logger.info("Seller updated product status Successfully");
						return Response.ok().build();
					}
		}
		catch(RestLogicalErrorException re){
			logger.error("Seller updation of product status Failed due to "+re.getResponseError().getMessage());
			return Response.ok().status(422).entity(re.getResponseError()).build();
		}
			logger.error("Seller updation of product status Failed due to unknown reason");
			return Response.ok().status(422).entity(new RestLogicalErrorException("User Name not found or incorrect! Enter again!")).build();
		
	}
	
}
