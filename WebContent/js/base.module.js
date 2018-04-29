/**
 * 
 */

var baseModule = angular.module('base.module', [ 'app' ])

.controller('skillContoller', function($scope) {
	var skillCtrl = this;
	$scope.message = "This is skill";
	skillCtrl.message = "This is fun";
	$scope.next = function() {

	};
}).controller('personalContoller', function($scope) {
	$scope.message = "This is personal";

});
