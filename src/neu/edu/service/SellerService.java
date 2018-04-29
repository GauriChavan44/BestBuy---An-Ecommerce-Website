package neu.edu.service;

import java.util.List;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import neu.edu.controller.data.AddNewProductRequest;
import neu.edu.controller.data.OrderedProducts;
import neu.edu.controller.data.RestLogicalErrorException;
import neu.edu.controller.data.UpdateOrderStatusRequest;
import neu.edu.dao.SellerDao;
import neu.edu.entity.OrderTable;
import neu.edu.entity.ProductTable;
import neu.edu.entity.SellerTable;

@Service
public class SellerService {

	@Autowired
	SellerDao sellerDao;

	@Autowired
	ProductTable product;

	public List<ProductTable> listSellerProducts(int id) {

		List<ProductTable> productList = sellerDao.viewSellerProducts(id);
		// System.out.println("ProductTable list in service is : "+productList);
		return productList;
	}

	public List<ProductTable> listAllProducts() {

		List<ProductTable> productList = sellerDao.viewAllProducts();
		// System.out.println("ProductTable list in service is : "+productList);
		return productList;
	}

	public List<ProductTable> listAllProductsByCategory(String category) {

		List<ProductTable> productList = sellerDao.viewAllProductsByCategory(category);
		// System.out.println("ProductTable list in service is : "+productList);
		return productList;
	}

	public boolean addNewProductForBestBuy(AddNewProductRequest addNewProductRequest, int id)
			throws RestLogicalErrorException {

		if(addNewProductRequest.getModelNo().isEmpty() || addNewProductRequest.getCategory().isEmpty() ||
				addNewProductRequest.getName().isEmpty() || addNewProductRequest.getPrice().toString().isEmpty()
				|| addNewProductRequest.getColor().isEmpty()){
			throw new RestLogicalErrorException("Product Details Incomplete!");
		}
		
		
		product.setProductId(new Random().nextInt(1000000));
		product.setModelNo(addNewProductRequest.getModelNo());
		product.setCategory(addNewProductRequest.getCategory());
		product.setName(addNewProductRequest.getName());
		product.setPrice(addNewProductRequest.getPrice());
		product.setColor(addNewProductRequest.getColor());
		product.setSellerId(id);

		ProductTable p = sellerDao.createProduct(product);
		// System.out.println("p is : "+p.getName());
		return true;
	}

	public List<OrderTable> listSellerOrders(int sellerId) {
		List<OrderTable> orderList = sellerDao.viewSellerOrders(sellerId);
		// System.out.println("OrderTable list in service is : "+orderList);
		return orderList;
	}

	public boolean updateStatusOfOrder(UpdateOrderStatusRequest updateOrderStatusRequest) throws RestLogicalErrorException{

		List<OrderTable> orderList = sellerDao.getOrderListbyOrderId(updateOrderStatusRequest.getOrderId());
		OrderTable order = orderList.get(0);
		order.setStatus("Shipped");

		boolean flag = sellerDao.changeOrderStatusRequest(order);
		return flag;

	}
}
