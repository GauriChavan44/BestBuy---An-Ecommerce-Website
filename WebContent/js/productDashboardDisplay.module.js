/**
 * 
 */
/**
 * 
 */
var productDashboardModule = angular.module("productDashboardDisplay.module");

productDashboardModule.controller('productDashboardController', function($scope,$uibModal,$rootScope,$cookieStore,$location,$routeParams,productDashboardService) {
	debugger;
	var productDashboardCtrl = this;
	
	productDashboardCtrl.message = "This is Product Dashboard";
	
	productDashboardCtrl.error = false;

	var productcategory = $routeParams.productcategory;

	//show products
	productDashboardCtrl.showAllProducts = function(){
		productDashboardCtrl.showproduct = productDashboardService.showProductsToCustomers(productcategory,callbackSuccess, callbackError);
	};
	
	var callbackSuccess = function(data,headers) { // Status Code:200
		productDashboardCtrl.message = "View Products Success";
		productDashboardCtrl.showproduct = data;
    };
    
    var callbackError = function(data,headers) {
   // 	alert("error");
    	productDashboardCtrl.message = data.message;
    	productDashboardCtrl.error = true;   
    };
    
    //go back to customer dashboard
	//go back
    productDashboardCtrl.goBack = function() {
		$location.path('/');
	};
	
	//product is added to cart
	productDashboardCtrl.addToCart = function(productsObj){
		debugger;
		$rootScope.add(productsObj);
		productDashboardCtrl.openComponentModal('Product added to cart!');
		debugger;
	};


	productDashboardCtrl.openComponentModal = function(msgToDisplay) {
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

productDashboardModule.factory('productDashboardService', function($http,$timeout,APP_CONSTANT) {
	var productDashboardService = {};
	
	productDashboardService.showProductsToCustomers = function(productcategory,callbackSuccess, callbackError){
		
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
	
	return productDashboardService;
});
