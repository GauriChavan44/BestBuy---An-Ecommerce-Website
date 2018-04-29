/**
 * 
 */
var adminactionsModule = angular.module("adminActions.module");

adminactionsModule
		.controller(
				'adminActionsController',
				function($scope, $uibModal, $rootScope, $location,
						adminActionsService) {

					var adminActionsCtrl = this;

					adminActionsCtrl.registration = {
						title : "",
						firstName : "",
						lastName : "",
						username : "",
						password : "",
						email : "",
						dateOfBirth : "",
						occupationType : ""
					};

					adminActionsCtrl.dobOptions = {
						maxDate : new Date(),
						popup : false,
						format : 'dd-MMMM-yyyy',
						altInputFormats : [ 'M!/d!/yyyy' ]
					};

					adminActionsCtrl.setUpDob = function() {
						var todayDate = new Date();
						adminActionsCtrl.dobOptions.maxDate = new Date(
								todayDate.getFullYear() - 18, todayDate
										.getMonth(), todayDate.getDate());
						adminActionsCtrl.registration.dateOfBirth = adminActionsCtrl.dobOptions.maxDate;
					};

					adminActionsCtrl.dateOptions = {
						dateDisabled : disabled,
						formatYear : 'yy',
						maxDate : adminActionsCtrl.dobOptions.maxDate,
						startingDay : 1
					};

					// calling today function
					adminActionsCtrl.setUpDob();

					// Disable weekend selection
					function disabled(data) {
						// var date = data.date, mode = data.mode;
						// return mode === 'day'
						// && (date.getDay() === 0 || date.getDay() === 6);
						return false;
					}
					adminActionsCtrl.open = function() {
						adminActionsCtrl.dobOptions.popup = true;
					};

					adminActionsCtrl.error = false;

					adminActionsCtrl.message = "This is Admin Actions dashboard";

					adminActionsCtrl.credentials = {
						username : ''
					};

					adminActionsCtrl.activateCredentials = {
						username : ''
					};

					// deactivate user
					adminActionsCtrl.disableSeller = function() {
						adminActionsService.deactivateSeller(
								adminActionsCtrl.credentials,
								callbackSuccessDeactivateSeller,
								callbackErrorDeactivateSeller);
					};

					var callbackSuccessDeactivateSeller = function(data,
							headers) { // Status Code:200
						adminActionsCtrl.message = "Seller Deactivated";
						adminActionsCtrl
								.openComponentModal('User Disabled Successfully!');
						$location.path('/admin');
					};

					var callbackErrorDeactivateSeller = function(data, headers) {
						adminActionsCtrl.message = data.message;
						adminActionsCtrl
						.openComponentModal('User Not Disabled!');
						adminActionsCtrl.error = true;
					};

					// activate user
					adminActionsCtrl.enableUser = function() {
						adminActionsService.activateSeller(
								adminActionsCtrl.activateCredentials,
								callbackSuccessActivateSeller,
								callbackErrorActivateSeller);
					};

					var callbackSuccessActivateSeller = function(data, headers) { // Status
																					// Code:200
						debugger;
						adminActionsCtrl.message = "Seller Activated";
						adminActionsCtrl
								.openComponentModal('User Enabled Successfully!');
						$location.path('/admin');
					};

					var callbackErrorActivateSeller = function(data, headers) {
						adminActionsCtrl.message = data.message;
						adminActionsCtrl
						.openComponentModal('User Not Enabled!');
						adminActionsCtrl.error = true;
					};

					// go back
					adminActionsCtrl.goBack = function() {
						$location.path('/admin');
					};

					// view sellers
					adminActionsCtrl.init = function() {
						adminActionsCtrl.sellers = adminActionsService
								.viewseller(callbackSuccess, callbackError);
					};

					var callbackSuccess = function(data, headers) { // Status
																	// Code:200
						adminActionsCtrl.message = "View Sellers Success";
						adminActionsCtrl.sellers = data;
					};

					var callbackError = function(data, headers) {
						adminActionsCtrl.message = data.message;
						adminActionsCtrl.error = true;
					};

					// view customers
					adminActionsCtrl.customerInitMethod = function() {
						adminActionsCtrl.customers = adminActionsService
								.viewcustomer(callbackSuccessCustomer,
										callbackErrorCustomer);
					};

					var callbackSuccessCustomer = function(data, headers) { // Status
																			// Code:200
						adminActionsCtrl.message = "View Customers Success";
						adminActionsCtrl.customers = data;
					};

					var callbackErrorCustomer = function(data, headers) {
						adminActionsCtrl.message = data.message;
						adminActionsCtrl.error = true;
					};

					// add new seller
					adminActionsCtrl.register = function() {
						console.log(adminActionsCtrl.registration);
						adminActionsService.register(
								adminActionsCtrl.registration,
								callbackSuccessRegistration,
								callbackErrorRegistration);
					}

					var callbackSuccessRegistration = function(data, headers) { // Status
						// Code:200
						adminActionsCtrl
								.openComponentModal('Seller Added Successfully!');
						$location.path('/admin');
					};

					var callbackErrorRegistration = function(data, headers) {
						adminActionsCtrl.message = data.message;
						adminActionsCtrl.error = true;
					};

					// cancel adding new seller
					adminActionsCtrl.cancel = function() {
						$location.path('/admin');
					}

					adminActionsCtrl.openComponentModal = function(msgToDisplay) {
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

adminactionsModule
		.factory('adminActionsService',
				function($http, $timeout, APP_CONSTANT) {

					var adminActionsService = {};

					// view all sellers
					adminActionsService.viewseller = function(callbackSuccess,
							callbackError) {

						$http.get(

						APP_CONSTANT.REMOTE_HOST + '/admin/viewsellers')
						// On Success of $http call
						.success(function(data, status, headers, config) {
							// alert("data is : "+data);
							message: 'View seller successful'
							callbackSuccess(data, headers);
							
						}).error(function(data, status, headers, config) { // IF
																			// STATUS
																			// CODE
																			// NOT
																			// 200
		//					alert("error in service");
							message: 'View seller error'
							callbackError(data, headers);
						});
					};

					// view all customers
					adminActionsService.viewcustomer = function(
							callbackSuccess, callbackError) {

						$http.get(

						APP_CONSTANT.REMOTE_HOST + '/admin/viewcustomers')
						// On Success of $http call
						.success(function(data, status, headers, config) {
							// alert("data is : "+data);
							message: 'View customer successful'
							callbackSuccess(data, headers);

						}).error(function(data, status, headers, config) { // IF
																			// STATUS
																			// CODE
																			// NOT
																			// 200
		//					alert("error in service");
							message: 'View customer error'
							callbackError(data, headers);
						});
					};

					// deactivate seller
					adminActionsService.deactivateSeller = function(data,
							callbackSuccessDeactivateSeller,
							callbackErrorDeactivateSeller) {
						$http.post(
								APP_CONSTANT.REMOTE_HOST
										+ '/admin/deactivateuser', data

						)
						// On Success of $http call
						.success(function(data, status, headers, config) {
							message: 'User deactivated'
							callbackSuccessDeactivateSeller(data, headers);
						}).error(function(data, status, headers, config) { // IF
							// STATUS
							// CODE
							// NOT
							// 200
							message: 'User not deactivated'
							callbackErrorDeactivateSeller(data, headers);
						});
					};

					// activate seller
					adminActionsService.activateSeller = function(data,
							callbackSuccessActivateSeller,
							callbackErrorActivateSeller) {
						$http.post(
								APP_CONSTANT.REMOTE_HOST
										+ '/admin/activateuser', data

						)
						// On Success of $http call
						.success(function(data, status, headers, config) {
							debugger;
							message: 'User activated'
							callbackSuccessActivateSeller(data, headers);
						}).error(function(data, status, headers, config) { // IF
							// STATUS
							// CODE
							// NOT
							// 200
							message: 'User not activated'
							callbackErrorActivateSeller(data, headers);
						});
					};

					adminActionsService.register = function(data, callback,
							callbackError) {

						$http.post(
								APP_CONSTANT.REMOTE_HOST
										+ '/admin/addnewseller', data

						)
						// On Success of $http call
						.success(function(data, status, headers, config) {
							message: 'Registration registered successful'
							callback(data, headers);
						}).error(function(data, status, headers, config) { // IF
							// STATUS
							// CODE
							// NOT
							// 200
							message: 'Registration was not successful'
							callbackError(data, headers);
						});
					};

					return adminActionsService;
				});
