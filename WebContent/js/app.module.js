/**
 * 
 */
var configModule = angular.module('app'); // Please dont not use [], dependency 

configModule.controller("applicationContoller", function($rootScope, $scope,
		$window) {
	debugger;
	console.log("inside applicationContoller");
	$rootScope.userSession;
	$rootScope.showCustomerMenu = false;

	$scope.init = function() {
		$scope.parentmethod();
	}

	$rootScope.$on("CallParentMethod", function() {
		$scope.parentmethod();
	});

	$scope.parentmethod = function() {
		// task

		var globals = JSON.parse($window.localStorage.getItem("globals"));
		if (globals) {
			console.log('globals exits');
			$('div#guest').hide();
			$('div#logout').show();
			$rootScope.userSession = globals.userSession;
			//    	    	    if($rootScope.userSession.role === 'customer'){
			//    	    	    		console.log('customer is valid');
			//    	    	    		$rootScope.showCustomerMenu = true;
			//    	    	    		console.log($scope.showCustomerMenu);
			//    	    	    		$('div#custMenu').show();
			//    	    	    		// $scope.$digest()
			//    	    	    }
			//
		} else {
			//    	 		$('div#custMenu').hide();
			//    	 		$('div#merchantMenu').hide();
			$('div#guest').show();
			$('div#logout').hide();
			console.log('globals note exits');
		}

	}
	$rootScope.items = [];

	$rootScope.itemsToAdd = [ {
		productId : '',
		modelNo : '',
		category : '',
		name : '',
		price : '',
		color : ''
	} ];

	$rootScope.add = function(itemToAdd) {
		debugger;
		var index = $scope.itemsToAdd.indexOf(itemToAdd);

		$rootScope.itemsToAdd.splice(index, 1);

		$rootScope.items.push(angular.copy(itemToAdd))
	}

});
