/**
 * 
 */
var sellerModule = angular.module("seller.module");

sellerModule.controller('sellerController', function($scope, $rootScope,
		$location, sellerService) {
	var sellerCtrl = this;

	sellerCtrl.message = "This is Seller Dashboard";

	sellerCtrl.viewProducts = function() {
		// alert("inside seller module");
		console.log('View products');
		$location.path('/seller/viewproducts');
	}

	sellerCtrl.addProducts = function() {
		// alert("inside seller module");
		console.log('Add new products');
		$location.path('/seller/addnewproduct');
	}

	sellerCtrl.viewOrders = function() {
		$location.path('/seller/vieworders');
	}

});

sellerModule.factory('sellerService', function($http, $timeout, APP_CONSTANT) {
	var sellerService = {};

	return sellerService;
});