/**
 * 
 */
var customerActionsModule = angular.module("customerActions.module");

customerActionsModule.controller('customerActionsController', function($scope,
		$uibModal, $rootScope, $location, customerActionsService) {
	var customerActionsCtrl = this;

	customerActionsCtrl.initMethod = function() {
		customerActionsCtrl.items = $rootScope.items;
	}

	customerActionsCtrl.items = [];

	customerActionsCtrl.itemsToAdd = [ {
		productId : '',
		modelNo : '',
		category : '',
		name : '',
		price : '',
		color : ''
	} ];

	customerActionsCtrl.cardDetails = {
		cardType : '',
		cardNumber : '',
		nameOnCard : '',
		expiryDate : '',
		cvv : '',
	};

	customerActionsCtrl.orderHistory = {
		orderId : '',
		status : '',
		sellerId : '',
		customerId : ''
	};

	// display customers order history
	customerActionsCtrl.initMethodGetOrders = function() {
		debugger;
		customerActionsService.getCustomerOrderHistory(
				$rootScope.globals.userSession.customerId,
				callbackSuccessOrderHistory, callbackErrorOrderHistory);
	}

	var callbackSuccessOrderHistory = function(data, headers) { // Status
		// Code:200
		debugger;
		customerActionsCtrl.message = "Order History Displayed Successfully";
		customerActionsCtrl.orders = data;
	};

	var callbackErrorOrderHistory = function(data, headers) {
		debugger;
		customerActionsCtrl.message = data.message;
		customerActionsCtrl.error = true;
	};

	// remove item from cart
	customerActionsCtrl.removeItem = function() {
		var index = customerActionsCtrl.itemsToAdd
				.indexOf(customerActionsCtrl.items);
		customerActionsCtrl.items.splice(index, 1);

	}

	// checkout and go to payment page
	customerActionsCtrl.checkOut = function() {
		
		if($rootScope.items.length==0) {
			customerActionsCtrl
			.openComponentModal('First add items to checkout!');
		}
		else{
			$location.path('/customer/payment');
		}	
	}

	var callbackSuccessOrder = function(data, headers) { // Status Code:200
		debugger;
		customerActionsCtrl.message = "Order Placed Successfully";
		customerActionsCtrl.products = data;
	};

	var callbackErrorOrder = function(data, headers) {
		debugger;
		customerActionsCtrl.message = data.message;
		customerActionsCtrl.error = true;
	};

	// to get the total price of cart while checking out
	customerActionsCtrl.getTotal = function() {
		debugger;
		var total = 0;
		for (var i = 0; i < $rootScope.items.length; i++) {
			var product = $rootScope.items[i];
			total += (product.price);
		}
		// alert("total is : "+total);
		return total;
	}

	// customer enters payment information
	customerActionsCtrl.pay = function() {
		customerActionsService.addPaymentDetails(
				customerActionsCtrl.cardDetails,
				$rootScope.globals.userSession.customerId, callbackSuccess,
				callbackError);
		debugger;
		
	}

	var callbackSuccess = function(data, headers) { // Status Code:200
		customerActionsCtrl
				.openComponentModal('Payment Information Added Successfully');
		customerActionsCtrl.message = "Payment Information Added Successfully";
		customerActionsCtrl.products = data;
		customerActionsService.orderPlaced($rootScope.items,
				$rootScope.globals.userSession.customerId,
				callbackSuccessOrder, callbackErrorOrder);
		$rootScope.items = [];
		debugger;
		customerActionsCtrl.openComponentModal('Order Placed Successfully');
		console.log('Order Placed Successfully');
		$location.path('/customer');
	};

	var callbackError = function(data, headers) {
		customerActionsCtrl.message = data.message;
		customerActionsCtrl.error = true;
	};

	// customer cancels payment information
	customerActionsCtrl.cancel = function() {
		$location.path('/customer');
	}

	// go back
	customerActionsCtrl.goBack = function() {
		$location.path('/customer');
	}

	customerActionsCtrl.openComponentModal = function(msgToDisplay) {
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

customerActionsModule.factory('customerActionsService', function($http,
		$timeout, $location, APP_CONSTANT) {
	debugger;
	var customerActionsService = {};

	customerActionsService.addPaymentDetails = function(data, id,
			callbackSuccess, callbackError) {
		$http.post(APP_CONSTANT.REMOTE_HOST + '/customer/' + id + '/payment',
				data

		)
		// On Success of $http call
		.success(function(data, status, headers, config) {
			callbackSuccess(data, headers);
		}).error(function(data, status, headers, config) { // IF
			// STATUS
			// CODE
			// NOT
			// 200
			callbackError(data, headers);
		});
	};

	customerActionsService.orderPlaced = function(data, id,
			callbackSuccessOrder, callbackErrorOrder) {
		debugger;
		$http.post(APP_CONSTANT.REMOTE_HOST + '/customer/' + id + '/order',
				data

		)
		// On Success of $http call
		.success(function(data, status, headers, config) {
			debugger;
			callbackSuccessOrder(data, headers);
		}).error(function(data, status, headers, config) { // IF
			// STATUS
			// CODE
			// NOT
			// 200
			debugger;
			callbackErrorOrder(data, headers);
		});
	}

	customerActionsService.getCustomerOrderHistory = function(id,
			callbackSuccessOrderHistory, callbackErrorOrderHistory) {

		$http.get(

		APP_CONSTANT.REMOTE_HOST + '/customer/' + id + '/orderhistory')
		// On Success of $http call
		.success(function(data, status, headers, config) {
			// alert("data is : "+data);
			debugger;
			callbackSuccessOrderHistory(data, headers);

		}).error(function(data, status, headers, config) { // IF STATUS CODE
															// NOT 200
			alert("error in service");
			callbackErrorOrderHistory(data, headers);
		});

	}

	return customerActionsService;
});
