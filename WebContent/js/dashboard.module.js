/**
 * 
 */

var dashboardModule = angular.module("dashboard.module");

dashboardModule.controller('dashboardController', function($scope, $rootScope,
		$location, dashboardService) {

	$scope.messageDash = "This is Dashboard";

	$scope.init = function() {
		$scope.resumes = dashboardService.listOfResume(
				$rootScope.globals.userSession.id,
				callbackDashboardListSuccess, callbackDashboardListError);

	};

	$scope.viewAllProducts = function() {

	}

	$scope.addResume = function() {
		console.log('Add Resume');
		$location.path('/resume/add');

	};
	$scope.viewResume = function(resumeObj) {
		console.log(resumeObj.id);
		$location.path('/resume/view');

	};

	var callbackDashboardListSuccess = function(data, headers) { // Status
																	// Code:200
		$scope.resumes = data;
		console.log($scope.resumes);
	};

	var callbackDashboardListError = function(data, headers) {
		$scope.message = data.message;
		$scope.error = true;
	};

});

dashboardModule.service('dashboardService', function($http, $timeout,
		APP_CONSTANT) {
	var dashboardService = {};

	dashboardService.listOfResume = function(id, callback, callbackError) {
		if (APP_CONSTANT.DEMO) {
			console.log('ID -->' + id);
			/*
			 * Dummy authentication for testing, uses $timeout to simulate api
			 * call ----------------------------------------------
			 */
			$timeout(function() {

				var response;

				response = [ {
					resumeId : 12,
					name : "Coming to US",
					desc : "-",
					dateOfCreation : '20/Dec/2016'
				}, {
					resumeId : 23,
					name : "After Semester 1",
					desc : "testing app",
					dateOfCreation : '18/May/2016'
				}, {
					resumeId : 43,
					name : "For Co-op",
					desc : "",
					dateOfCreation : '1/Nov/2016'
				} ];

				callback(response);
			}, 1000);
		} else {

			/*
			 * Use this for real authentication
			 * ----------------------------------------------
			 */
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
		}

	};

	return dashboardService;
})
