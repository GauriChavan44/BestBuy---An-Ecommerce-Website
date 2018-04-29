/**
 * 
 */
/**
 * 
 */
var productModule = angular.module("productDisplay.module");

productModule.controller('productController', function($scope,$uibModal,$rootScope,$cookieStore,$location,$routeParams,productService) {
	debugger;
	var productCtrl = this;
	
	productCtrl.message = "This is Product Dashboard";
	
	productCtrl.error = false;

	var productcategory = $routeParams.productcategory;

	//show products
	productCtrl.showProducts = function(){
		productCtrl.showproduct = productService.showProductsToCustomers(productcategory,callbackSuccess, callbackError);
	};
	
	var callbackSuccess = function(data,headers) { // Status Code:200
		productCtrl.message = "View Products Success";
		productCtrl.showproduct = data;
    };
    
    var callbackError = function(data,headers) {
   // 	alert("error");
    	productCtrl.message = data.message;
    	productCtrl.error = true;   
    };
    
    //go back to customer dashboard
	//go back
    productCtrl.goBack = function() {
		$location.path('/customer');
	};
	
	//product is added to cart
	productCtrl.addToCart = function(productsObj){
		debugger;
		$rootScope.add(productsObj);
		productCtrl.openComponentModal('Product added to cart!');
		debugger;
	};


	productCtrl.openComponentModal = function(msgToDisplay) {
		var modalInstance = $uibModal.open({
			animation : true,
			component : 'successComponent',
			resolve : {
				msg : function() {
					return msgToDisplay;
				}
			}
		});

	};
});

productModule.factory('productService', function($http,$timeout,APP_CONSTANT) {
	var productService = {};
	
	productService.showProductsToCustomers = function(productcategory,callbackSuccess, callbackError){
		
		$http.get(
    			
    			APP_CONSTANT.REMOTE_HOST+'/seller/viewProductsByCategory/'+productcategory
    			)
    	//		On Success of $http call
    			.success(function (data, status, headers, config) {
    	//			alert("data is : "+data);  				
    				callbackSuccess(data,headers);
    				
    			})
    			.error(function (data, status, headers, config) { // IF STATUS CODE NOT 200
    	//			alert("error in service");
    				callbackError(data,headers);
    			});
		
	}
	
	return productService;
});
