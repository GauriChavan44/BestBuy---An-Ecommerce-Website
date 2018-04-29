/**
 * 
 */

var adminModule = angular.module("admin.module");

adminModule.controller('adminController', function($scope, $rootScope,
		$location, adminService) {
	debugger;

	var adminCtrl = this;

	adminCtrl.messageDash = "This is Admin dashboard";

	adminCtrl.username = {
		username : ''
	};

	//redirect to disable user page
	adminCtrl.disableUser = function() {
	//	alert("inside disable user admin module");
		console.log('Disable Seller');
		$location.path('/admin/deactivateuser');
	}

	//redirect to enable user page
	adminCtrl.enableUser = function() {
	//	alert("inside enable user admin module");
		console.log('Disable Seller');
		$location.path('/admin/activateuser');
	}

	adminCtrl.viewSeller = function() {
	//	alert("inside view seller");
		console.log('Inside View Seller');
		$location.path('/admin/viewsellers');
	}

	adminCtrl.viewCustomer = function() {
	//	alert("inside view customer");
		console.log('Inside View customer');
		$location.path('/admin/viewcustomers');
	}

	adminCtrl.addSeller = function() {
		$location.path('/admin/addnewseller');
	}

	$scope.addResume = function() {
		console.log('Add Resume');
		$location.path('/resume/add');

	};
	$scope.viewResume = function(resumeObj) {
		console.log(resumeObj.id);
		$location.path('/resume/view');

	};

	var callbackDashboardListSuccess = function(data, headers) { // Status Code:200
		$scope.resumes = data;
		console.log($scope.resumes);
	};

	var callbackDashboardListError = function(data, headers) {
		$scope.message = data.message;
		$scope.error = true;
	};

});

adminModule.service('adminService', function($http, $timeout, APP_CONSTANT) {
	debugger;
	var adminService = {};

	adminService.listOfResume = function(id, callback, callbackError) {

		$http.get(APP_CONSTANT.REMOTE_HOST + '/user/' + id + '/resume')
		// On Success of $http call
		.success(function(data, status, headers, config) {
			callback(data, headers);
		}).error(function(data, status, headers, config) { // IF
			// STATUS
			// CODE
			// NOT
			// 200
			callbackError(data, headers);
		});
	};

	return adminService;
})
