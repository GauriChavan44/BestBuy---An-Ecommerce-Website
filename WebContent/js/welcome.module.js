/**
 * 
 */
var welcomeModule = angular.module("welcome.module");

welcomeModule.controller('welcomeController', function($location) {

	var welcomeCtrl = this;

	welcomeCtrl.error = false;

	welcomeCtrl.message = "This is Welcome Page";

	welcomeCtrl.productcategory = {
		applicances : 'applicances',
		computersandtablets : 'computersandtablets',
		cellphones : 'cellphones',
		moviesAndMusic : 'moviesAndMusic',
		healthFitnessAndBeauty : 'healthFitnessAndBeauty',
		homeAndOffice : 'homeAndOffice'
	};

	welcomeCtrl.category = function(productcategory) {
		debugger;
		if (productcategory == "applicances") {
			$location.path('/product/productcategory/' + productcategory);
		}
		if (productcategory == "computersandtablets") {
			$location.path('/product/productcategory/' + productcategory);
		}
		if (productcategory == "cellphones") {
			$location.path('/product/productcategory/' + productcategory);
		}
		if (productcategory == "moviesAndMusic") {
			$location.path('/product/productcategory/' + productcategory);
		}
		if (productcategory == "healthFitnessAndBeauty") {
			$location.path('/product/productcategory/' + productcategory);
		}
		if (productcategory == "homeAndOffice") {
			$location.path('/product/productcategory/' + productcategory);
		}
	};

	//view products
	welcomeCtrl.initMethod = function() {
		welcomeCtrl.products = welcomeService.viewproducts(callbackSuccess,
				callbackError);
	};

	var callbackSuccess = function(data, headers) { // Status Code:200
		welcomeCtrl.message = "View Products Success";
		welcomeCtrl.products = data;
	};

	var callbackError = function(data, headers) {
		welcomeCtrl.message = data.message;
		welcomeCtrl.error = true;
	};

});
