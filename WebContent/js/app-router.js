/**
 * 
 */

var configModule = angular
		.module('app')
		// Please dont not use [], dependency

		.config(function($routeProvider) {
			// alert($routeProvider);
			// $urlRouterProvider.otherwise('/login');
			$routeProvider
			// route for the home page
			.when('/', {
				templateUrl : 'partial/welcome.html',
				controller : 'welcomeController',
				controllerAs : 'welcomeCtrl'
			}).when('/login', {
				templateUrl : 'partial/login.html',
				controller : 'authController',
				controllerAs : 'authCtrl'
			}).when('/cart', {
				templateUrl : 'partial/cart.html',
				controller : 'cartController',
				controllerAs : 'cartCtrl'
			}).when('/checkout', {
				templateUrl : 'partial/checkout.html',
				controller : 'checkoutController',
				controllerAs : 'checkoutCtrl'
			}).when('/admin', {
				templateUrl : 'partial/adminDashboard.html',
				controller : 'adminController',
				controllerAs : 'adminCtrl'
			}).when('/admin/deactivateuser', {
				templateUrl : 'partial/admin-disableUser.html',
				controller : 'adminActionsController',
				controllerAs : 'adminActionsCtrl'
			}).when('/admin/activateuser', {
				templateUrl : 'partial/admin-enableUser.html',
				controller : 'adminActionsController',
				controllerAs : 'adminActionsCtrl'
			}).when('/admin/viewsellers', {
				templateUrl : 'partial/view-sellers.html', // create html pages
															// here
				controller : 'adminActionsController',
				controllerAs : 'adminActionsCtrl'
			}).when('/admin/viewcustomers', {
				templateUrl : 'partial/view-customers.html',
				controller : 'adminActionsController',
				controllerAs : 'adminActionsCtrl'
			}).when('/admin/addnewseller', {
				templateUrl : 'partial/add-new-seller.html',
				controller : 'adminActionsController',
				controllerAs : 'adminActionsCtrl'
			}).when('/seller', {
				templateUrl : 'partial/sellerDashboard.html',
				controller : 'sellerController',
				controllerAs : 'sellerCtrl'
			}).when('/seller/viewproducts', {
				templateUrl : 'partial/view-products.html',
				controller : 'sellerActionsController',
				controllerAs : 'sellerActionsCtrl',
			}).when('/seller/addnewproduct', {
				templateUrl : 'partial/add-product.html',
				controller : 'sellerActionsController',
				controllerAs : 'sellerActionsCtrl'
			}).when('/seller/vieworders', {
				templateUrl : 'partial/seller-view-orders.html',
				controller : 'sellerActionsController',
				controllerAs : 'sellerActionsCtrl'
			}).when('/customer', {
				templateUrl : 'partial/customerDashboard.html',
				controller : 'customerController',
				controllerAs : 'customerCtrl'
			}).when('/customer/checkOrders', {
				templateUrl : 'partial/checkOrderHistory.html',
				controller : 'customerActionsController',
				controllerAs : 'customerActionsCtrl'
			}).when('/customer/checkCart', {
				templateUrl : 'partial/cart.html',
				controller : 'customerActionsController',
				controllerAs : 'customerActionsCtrl'
			}).when('/customer/checkOut', {
				templateUrl : 'partial/checkout.html',
				controller : 'customerActionsController',
				controllerAs : 'customerActionsCtrl'
			}).when('/customer/payment', {
				templateUrl : 'partial/payment.html',
				controller : 'customerActionsController',
				controllerAs : 'customerActionsCtrl'
			}).when('/customer/productcategory/:productcategory', {
				templateUrl : 'partial/product-display.html',
				controller : 'productController',
				controllerAs : 'productCtrl'
			}).when('/product/productcategory/:productcategory', {
				templateUrl : 'partial/product-display-dashboard.html',
				controller : 'productDashboardController',
				controllerAs : 'productDashboardCtrl'
			}).when('/registration', {
				templateUrl : 'partial/registration.html',
				controller : 'registrationController',
				controllerAs : 'regCtrl'
			}).when('/logout', {
				redirectTo : '/'
			}).when('/dashboard', {
				templateUrl : 'dashboard.html',
				controller : 'dashboardController'
			}).when('/resume/add', {
				templateUrl : 'partial/resume-add.html',
				controller : 'addResumeController'
			}).when('/resume/view', {
				templateUrl : 'partial/personal.html',
				controller : 'personalController'
			}).when('/skill', {
				templateUrl : 'partial/skill.html',
				controller : 'skillContoller',
				controllerAs : 'skillCtrl'
			}).when('/personal', {
				templateUrl : 'partial/personal.html',
				controller : 'personalContoller',
				controllerAs : 'personalCtrl'
			}).otherwise({
				redirectTo : '/'
			});
		})

		.run(
				function($rootScope, $location, $cookieStore, $window, $http,
						AUTH_EVENTS, APP_CONSTANT) {
					// Management
					$rootScope
							.$on(
									'$locationChangeStart',
									function(event, next, current) {
										debugger;
										// redirect to login page if not logged
										// in
										console.log('Clicked on '
												+ $location.path());
										if (!($location.path() == '/'
												|| $location.path() == '/registration'
												|| $location.path() == '/login'
												|| $location.path() == '/admin'
												|| $location.path() == '/admin/deactivateuser'
												|| $location.path() == '/admin/activateuser'
												|| $location.path() == '/admin/viewsellers'
												|| $location.path() == '/admin/viewcustomers'
												|| $location.path() == '/admin/addnewseller'
												|| $location.path() == '/seller'
												|| $location.path() == '/seller/viewproducts'
												|| $location.path() == '/seller/addnewproduct'
												|| $location.path() == '/customer'
												|| $location.path() == '/customer/productDisplay'
												|| $location.path() == '/customer/checkOrders'
												|| $location.path() == '/customer/checkCart'
												|| $location.path() == '/customer/checkOut'
												|| $location.path() == ''
												|| $location.path() == '/product' 
												|| $location.path() == '/product/productcategory/applicances'
												|| $location.path() == '/product/productcategory/computersandtablets'
												|| $location.path() == '/product/productcategory/cellphones'
												|| $location.path() == '/product/productcategory/moviesAndMusic'
												|| $location.path() == '/product/productcategory/healthFitnessAndBeauty'
												|| $location.path() == '/product/productcategory/homeAndOffice')
												&& !$rootScope.globals.userSession) {
											debugger;
											console.log('Invalid Path')
											$location.path('/');
										} else if ($location.path() == '/logout') {
											debugger;
											$rootScope
													.$broadcast(AUTH_EVENTS.logoutSuccess);
										} else {
											debugger;
											console.log('Valid Path')

										}
									});

					$rootScope.$on(AUTH_EVENTS.loginFailed, function(event,
							next) {
						debugger;
						console.log('Login failed');

					});

					$rootScope.$on(AUTH_EVENTS.logoutSuccess, function(event,
							next) {
						debugger;
						console.log('Logout Success');
						$window.localStorage.removeItem("globals");
						$rootScope.userSession = null;
						$rootScope.$emit("CallParentMethod", {});

						// $scope.message = "Login failed";
					});

					$rootScope
							.$on(
									AUTH_EVENTS.loginSuccess,
									function(event, next) {
										debugger;
										// alert("going to dashboard");
										// $scope.message = "Login Success";
										console.log('Login success');
										$window.localStorage
												.setItem(
														"globals",
														angular
																.toJson($rootScope.globals));
										$rootScope.userSession = JSON
												.parse($window.localStorage
														.getItem("globals")).userSession;

										$rootScope
												.$emit("CallParentMethod", {});
										$location.path('/dashboard');
									});

					// keep user logged in after page refresh
					$rootScope.globals = $cookieStore.get('globals') || {};
					if ($rootScope.globals.userSession) {
						$http.defaults.headers.common[APP_CONSTANT.AUTH_KEY] = $rootScope.globals.userSession.authKey; // jshint
																														// ignore:line
						$window.localStorage.setItem("globals", angular
								.toJson($rootScope.globals));
						$rootScope.userSession = JSON
								.parse($window.localStorage.getItem("globals")).userSession;

					}
				})
