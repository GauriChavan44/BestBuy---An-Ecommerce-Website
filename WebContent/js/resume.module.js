/**
 * 
 */
var resumeModule = angular.module("resume.module");

resumeModule.controller("addResumeController", function($scope, $rootScope,
		$location) {

	$scope.back = function() {
		$location.path("/dashboard");
	}

	$scope.save = function() {

		regCtrl.openComponentModal('Resume Added Successful');

		$location.path("/dashboard");
	}

	var openComponentModal = function(msgToDisplay) {
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