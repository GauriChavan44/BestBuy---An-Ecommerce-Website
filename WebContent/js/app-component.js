/**
 * 
 */
var configModule = angular.module('app'); // Please dont not use [], dependency 

configModule.component('successComponent', {
	templateUrl : 'popup/success-ok.html',
	bindings : {
		resolve : '<',
		close : '&',
		dismiss : '&'
	},
	controller : function() {
		var $ctrl = this;

		$ctrl.$onInit = function() {
			$ctrl.msg = $ctrl.resolve.msg;
		};

		$ctrl.ok = function() {
			$ctrl.close();
		};

		$ctrl.cancel = function() {
			$ctrl.dismiss({
				$value : 'cancel'
			});
		};
	}
})