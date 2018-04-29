/**
 * 
 */
'use strict';
// Step 1: declare modules
angular.module("authModule", []);
angular.module("base.module", []);
angular.module("welcome.module", []);
angular.module("registration.module", []);
angular.module("dashboard.module", []);
angular.module("resume.module", []);
angular.module("admin.module", []);
angular.module("adminActions.module", []);
angular.module("seller.module", []);
angular.module("sellerActions.module", []);
angular.module("customer.module", []);
angular.module("customerActions.module", []);
angular.module("productDisplay.module", []);
angular.module("cart.module", []);
angular.module("checkout.module", []);
angular.module("productDashboardDisplay.module", []);

angular.module('appCoreModule', [ 'ngRoute', 'ngCookies', 'ui.bootstrap' ]);
//Step 2: Register App
angular.module("app", [ 'appCoreModule', 'welcome.module',
		'registration.module', 'authModule', 'dashboard.module', 'base.module',
		'resume.module', 'admin.module', 'adminActions.module',
		'seller.module', 'sellerActions.module', 'customer.module',
		'customerActions.module', 'productDisplay.module', 'cart.module',
		'checkout.module','productDashboardDisplay.module' ]);
