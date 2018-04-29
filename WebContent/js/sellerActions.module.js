/**
 * 
 */
var sellerActionsModule = angular.module("sellerActions.module");

sellerActionsModule
		.controller(
				'sellerActionsController',
				function($scope, $uibModal, $rootScope, $location,
						sellerActionsService) {

					var sellerActionsCtrl = this;

					sellerActionsCtrl.message = "Seller adding products";

					sellerActionsCtrl.error = false;

					sellerActionsCtrl.productdetails = {
						modelNo : "",
						category : "",
						name : "",
						price : "",
						color : "",
						sellerId : ""
					};
					
					sellerActionsCtrl.customer = {
							customerId : ""
					};

					// go back
					sellerActionsCtrl.goBack = function() {
						$location.path('/seller');
					};
					
					//send email to customer
					sellerActionsCtrl.sendEmail = function(customerId){
						sellerActionsService.sendEmailToCustomer(customerId);
						sellerActionsCtrl
						.openComponentModal('Email sent to Customer!');
					}
					
					// view products
					sellerActionsCtrl.initMethodGetProducts = function() {
						// alert("1 :
						// "+$rootScope.globals.userSession.sellerId);
						sellerActionsCtrl.products = sellerActionsService
								.viewAllProducts(
										$rootScope.globals.userSession.sellerId,
										callbackSuccess, callbackError);
					};

					var callbackSuccess = function(data, headers) { // Status
																	// Code:200
						sellerActionsCtrl.message = "View Products Success";
						sellerActionsCtrl.products = data;
					};

					var callbackError = function(data, headers) {
						sellerActionsCtrl.message = data.message;
						sellerActionsCtrl.error = true;
					};

					// view orders
					sellerActionsCtrl.initMethodGetOrders = function() {
						sellerActionsCtrl.products = sellerActionsService
								.viewAllOrders(
										$rootScope.globals.userSession.sellerId,
										callbackSuccessOrders,
										callbackErrorOrders);
					}

					var callbackSuccessOrders = function(data, headers) { // Status
																			// Code:200
						sellerActionsCtrl.message = "View Orders Success";
						sellerActionsCtrl.orders = data;
					};

					var callbackErrorOrders = function(data, headers) {
						sellerActionsCtrl.message = data.message;
						sellerActionsCtrl.error = true;
					};

					// seller changes status of order
					sellerActionsCtrl.changeStatus = function(productsObj) {
						debugger;
						sellerActionsService.changeOrderStatus(productsObj,
								$rootScope.globals.userSession.sellerId,
								callbackSuccessOrderStatus,
								callbackErrorOrderStatus);
					};

					var callbackSuccessOrderStatus = function(data, headers) { // Status
																				// Code:200
						sellerActionsCtrl.message = "View Orders Success";
						sellerActionsCtrl
								.openComponentModal('Status of order Changed Successfully!');
						sellerActionsCtrl.initMethodGetOrders();
						// sellerActionsCtrl.orders = data;
					};

					var callbackErrorOrderStatus = function(data, headers) {
						sellerActionsCtrl.message = data.message;
						sellerActionsCtrl.error = true;
					};

					// add product
					sellerActionsCtrl.add = function() {
						console.log(sellerActionsCtrl.productdetails);
						sellerActionsService.addProduct(
								sellerActionsCtrl.productdetails,
								$rootScope.globals.userSession.sellerId,
								callbackSuccess1, callbackError1);

					};

					var callbackSuccess1 = function(data, headers) { // Status
																		// Code:200

						// regCtrl.openComponentModal('Registration
						// Successful');
						// alert("product adding successfull");
						// debugger;
						sellerActionsCtrl
								.openComponentModal('New Product added successfully!');
						$location.path('/seller');

					};

					var callbackError1 = function(data, headers) {
						sellerActionsCtrl.message = data.message;
						sellerActionsCtrl.error = true;

					};

					sellerActionsCtrl.cancel = function() {
						$location.path('/');
					}

					sellerActionsCtrl.openComponentModal = function(
							msgToDisplay) {
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

sellerActionsModule.factory('sellerActionsService', function($http, $timeout,
		$rootScope, APP_CONSTANT) {
	var sellerActionsService = {};
	// var id = $rootScope.globals.userSession.sellerId;
	debugger;
	sellerActionsService.viewAllProducts = function(id, callbackSuccess,
			callbackError) {

		$http.get(

		APP_CONSTANT.REMOTE_HOST + '/seller/' + id + '/viewproducts')
		// On Success of $http call
		.success(function(data, status, headers, config) {
			// alert("data is : "+data);
			debugger;
			callbackSuccess(data, headers);

		}).error(function(data, status, headers, config) { // IF STATUS CODE
			// NOT 200
			// alert("error in service");
			callbackError(data, headers);
		});
	};

	sellerActionsService.addProduct = function(data, id, callbackSuccess1,
			callbackError1) {
		$http.post(
				APP_CONSTANT.REMOTE_HOST + '/seller/' + id + '/addnewproduct',
				data

		)
		// On Success of $http call
		.success(function(data, status, headers, config) {
			callbackSuccess1(data, headers);
		}).error(function(data, status, headers, config) { // IF
			// STATUS
			// CODE
			// NOT
			// 200
			callbackError1(data, headers);
		});

	};

	sellerActionsService.viewAllOrders = function(id, callbackSuccessOrders,
			callbackErrorOrders) {
		$http.get(

		APP_CONSTANT.REMOTE_HOST + '/seller/' + id + '/vieworders')
		// On Success of $http call
		.success(function(data, status, headers, config) {
			// alert("data is : "+data);
			debugger;
			callbackSuccessOrders(data, headers);

		}).error(function(data, status, headers, config) { // IF STATUS CODE
			// NOT 200
			// alert("error in service");
			callbackErrorOrders(data, headers);
		});
	}

	sellerActionsService.changeOrderStatus = function(data, id,
			callbackSuccessOrderStatus, callbackErrorOrderStatus) {
		$http.post(APP_CONSTANT.REMOTE_HOST + '/seller/updatestatus', data

		)
		// On Success of $http call
		.success(function(data, status, headers, config) {
			callbackSuccessOrderStatus(data, headers);
		}).error(function(data, status, headers, config) { // IF
			// STATUS
			// CODE
			// NOT
			// 200
			callbackErrorOrderStatus(data, headers);
		});

	};
	
	
	sellerActionsService.sendEmailToCustomer = function(id){
		$http.get(APP_CONSTANT.REMOTE_HOST + '/echo/public/'+ id

		)
	}

	return sellerActionsService;
});