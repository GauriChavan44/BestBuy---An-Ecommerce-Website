/**
 * 
 */
var customerModule = angular.module("customer.module");

customerModule.controller('customerController', function($scope, $rootScope,
		$location, customerService) {
	debugger;
	var customerCtrl = this;

	customerCtrl.message = "This is Customer Dashboard";

	customerCtrl.error = false;

	customerCtrl.productcategory = {
		applicances : 'applicances',
		computersandtablets : 'computersandtablets',
		cellphones : 'cellphones',
		moviesAndMusic : 'moviesAndMusic',
		healthFitnessAndBeauty : 'healthFitnessAndBeauty',
		homeAndOffice : 'homeAndOffice'
	};

	customerCtrl.searchProducts = function() {
		// alert("inside search products");
	};

	customerCtrl.checkOrders = function() {
		console.log('check Customer Orders');
		$location.path('/customer/checkOrders');
	}

	customerCtrl.checkCart = function() {
		console.log('check Customer Cart');
		$location.path('/customer/checkCart');
	}

	customerCtrl.checkOut = function() {
		console.log('Customer Checkout');
		$location.path('/customer/checkOut');
	}

	customerCtrl.category = function(productcategory) {
		// productcategory = "applicances";
		// alert("productcategory is : "+productcategory);
		if (productcategory == "applicances") {
			$location.path('/customer/productcategory/' + productcategory);
		}
		if (productcategory == "computersandtablets") {
			$location.path('/customer/productcategory/' + productcategory);
		}
		if (productcategory == "cellphones") {
			$location.path('/customer/productcategory/' + productcategory);
		}
		if (productcategory == "moviesAndMusic") {
			$location.path('/customer/productcategory/' + productcategory);
		}
		if (productcategory == "healthFitnessAndBeauty") {
			$location.path('/customer/productcategory/' + productcategory);
		}
		if (productcategory == "homeAndOffice") {
			$location.path('/customer/productcategory/' + productcategory);
		}
	};

});

customerModule.factory('customerService', function($http, $timeout, $location,
		APP_CONSTANT) {
	var customerService = {};

	return customerService;
});
