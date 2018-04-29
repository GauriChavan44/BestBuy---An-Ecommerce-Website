package neu.edu.service;

import java.util.List;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import neu.edu.controller.data.OrderedProducts;
import neu.edu.controller.data.PaymentInformationRequest;
import neu.edu.controller.data.RestLogicalErrorException;
import neu.edu.dao.CustomerDao;
import neu.edu.entity.OrderProductTable;
import neu.edu.entity.OrderTable;
import neu.edu.entity.PaymentInformationTable;
import neu.edu.entity.ProductTable;

@Service
public class CustomerService {

	@Autowired
	private CustomerDao customerDao;

	@Autowired
	private PaymentInformationTable payment;

	@Autowired
	private OrderTable order;

	@Autowired
	private OrderProductTable orderproduct;

	public List<ProductTable> listProductsBasedOnCategory(String category) {

		List<ProductTable> productList = customerDao.viewProductsByCategory(category);

		return productList;
	}

	public boolean enterPaymentDetails(PaymentInformationRequest paymentInformationRequest, String id) throws RestLogicalErrorException{
	//	System.out.println("Inside enter payment details");
		if(paymentInformationRequest.getCardType().isEmpty() || paymentInformationRequest.getCardNumber().isEmpty() ||
				paymentInformationRequest.getNameOnCard().isEmpty() || paymentInformationRequest.getExpiryDate().isEmpty()){
	//		System.out.println("inside if of payment details incomplete");
			throw new RestLogicalErrorException("Payment details incomplete!");
		}else{
	//		System.out.println("inside else of payment details complete");
			payment.setPaymentId(new Random().nextInt(1000000));
			payment.setCardType(paymentInformationRequest.getCardType());
			payment.setCardNumber(paymentInformationRequest.getCardNumber());
			payment.setNameOnCard(paymentInformationRequest.getNameOnCard());
			payment.setExpiryDate(paymentInformationRequest.getExpiryDate());
			payment.setCvv(paymentInformationRequest.getCvv());
			payment.setCustomerId(Integer.parseInt(id));

			customerDao.createPaymentDetails(payment);
			return true;
		}
		
		
	}

	public boolean orderPlacedDetails(OrderedProducts[] orderedProducts, int customerId) throws RestLogicalErrorException{

		// System.out.println("In orderPlacedDetails Customer service");
		order.setOrderId(new Random().nextInt(80000));
		order.setStatus("Placed");
		order.setCustomerId(customerId);

		for (OrderedProducts op : orderedProducts) {
			order.setSellerId(op.getSellerId());
		}
		customerDao.createOrder(order);

		for (OrderedProducts op : orderedProducts) {
			orderproduct.setOrderProductTableId(new Random().nextInt(70000));
			orderproduct.setOrderId(order.getOrderId());
			orderproduct.setProductId(op.getProductId());
			order.setSellerId(op.getSellerId());

			customerDao.createOrderProduct(orderproduct);
		}
		return true;
	}

	public List<OrderTable> viewOrderHistory(int customerId) {
		List<OrderTable> orderList = customerDao.getOrderHistoryOfCustomer(customerId);
		// System.out.println("Order list in customer service is : "+orderList);
		return orderList;

	}
}
