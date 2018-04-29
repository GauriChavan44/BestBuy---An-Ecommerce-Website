 /**
 * 
 */

var authModule = angular.module("authModule");

authModule.controller('authController',
		function($scope, $rootScope, $location, authService, AUTH_EVENTS) {
			var authCtrl = this; // variable should be Same as controllerAs: 'authCtrl'
			authCtrl.message = "";
			authCtrl.error = false;

			authCtrl.credentials = {
				username : '',
				password : ''
			};

			authCtrl.clearCredentials = function() {
				authService.clearCredentials();
			}

			authCtrl.login = function() {
				//alert(authCtrl.credentials.username);
				authService.login(authCtrl.credentials, callbackSuccess,
						callbackError);
			};

			var callbackSuccess = function(data, headers) { // Status Code:200
				debugger;
				authCtrl.message = "Login Success";
				authService.setCredentials(data, headers);
				$rootScope.$broadcast(AUTH_EVENTS.loginSuccess);

				if (data.role == "admin") {
					$location.path("/admin");
				} else if (data.role == "customer") {
					$location.path("/customer");
				} else if (data.role == "seller") {
					debugger;
					$location.path("/seller");
				}
			};

			var callbackError = function(data, headers) {
				//		alert("login error");
				authCtrl.message = data.message;
				authCtrl.error = true;
				$rootScope.$broadcast(AUTH_EVENTS.loginFailed);

			};

		});

authModule.factory('authService', function($rootScope, $http, $timeout,
		$cookieStore, $window, $location, APP_CONSTANT, AUTH_EVENTS, Base64) {
	var authService = {};

	authService.login = function(data, callbackSuccess, callbackError) {

		var authdata = Base64.encode(data.username + ':' + data.password);
		//alert("ready for login");
		/* Use this for real authentication
		 ----------------------------------------------*/
		$http.post(

		APP_CONSTANT.REMOTE_HOST + '/auth', {

			//username : data.username,
			//password : data.password
		}, {
			headers : {
				'Authorization' : 'Basic ' + authdata
			}
		})
		//On Success of $http call
		.success(function(data, status, headers, config) {
			//alert("success");
			debugger;
			callbackSuccess(data, headers);

		}).error(function(data, status, headers, config) { // IF STATUS CODE NOT 200
			//		alert("error");
			callbackError(data, headers);
		});
	};

	authService.setCredentials = function(data, headers) {

		//Setting of Auth ID
		var authdata = data.username + ':' + data.personId;

		var authKey = APP_CONSTANT.DEMO ? 'dummy'
				: headers(APP_CONSTANT.AUTH_KEY);
		debugger;
		$rootScope.globals = {
			userSession : {

				personId : data.personId,
				sellerId : data.sellerId,
				customerId : data.customerId,
				adminId : data.adminId,
				username : data.username,
				role : data.usertype,
				authKey : authKey
			},

		};
		debugger;
		$http.defaults.headers.common[APP_CONSTANT.AUTH_KEY] = authKey; // jshint ignore:line            
		$cookieStore.put('globals', $rootScope.globals);
		//debugger;
		//$window.sessionStorage.setItem("globals", angular.toJson($rootScope.globals));
		//$window.sessionStorage.globals = $rootScope.globals;
	};

	authService.clearCredentials = function() {
		debugger;
		console.log('Logout clearCredentials');
		$rootScope.globals = {};
		$cookieStore.remove('globals');
		$rootScope.items = [];
		$rootScope.$broadcast(AUTH_EVENTS.logoutSuccess);

	};

	authService.isAuthenticated = function() {
		return !!$rootScope.globals.userSession.authKey;
	};

	authService.isAuthorized = function(authorizedRoles) {
		if (!angular.isArray(authorizedRoles)) {
			authorizedRoles = [ authorizedRoles ];
		}
		return (authService.isAuthenticated() && authorizedRoles
				.indexOf(Session.userRole) !== -1);
	};

	return authService;
});

authModule
		.factory(
				'Base64',
				function() {
					/* jshint ignore:start */

					var keyStr = 'ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/=';

					return {
						encode : function(input) {
							var output = "";
							var chr1, chr2, chr3 = "";
							var enc1, enc2, enc3, enc4 = "";
							var i = 0;

							do {
								chr1 = input.charCodeAt(i++);
								chr2 = input.charCodeAt(i++);
								chr3 = input.charCodeAt(i++);

								enc1 = chr1 >> 2;
								enc2 = ((chr1 & 3) << 4) | (chr2 >> 4);
								enc3 = ((chr2 & 15) << 2) | (chr3 >> 6);
								enc4 = chr3 & 63;

								if (isNaN(chr2)) {
									enc3 = enc4 = 64;
								} else if (isNaN(chr3)) {
									enc4 = 64;
								}

								output = output + keyStr.charAt(enc1)
										+ keyStr.charAt(enc2)
										+ keyStr.charAt(enc3)
										+ keyStr.charAt(enc4);
								chr1 = chr2 = chr3 = "";
								enc1 = enc2 = enc3 = enc4 = "";
							} while (i < input.length);

							return output;
						},

						decode : function(input) {
							var output = "";
							var chr1, chr2, chr3 = "";
							var enc1, enc2, enc3, enc4 = "";
							var i = 0;

							// remove all characters that are not A-Z, a-z, 0-9, +, /, or =
							var base64test = /[^A-Za-z0-9\+\/\=]/g;
							if (base64test.exec(input)) {
								window
										.alert("There were invalid base64 characters in the input text.\n"
												+ "Valid base64 characters are A-Z, a-z, 0-9, '+', '/',and '='\n"
												+ "Expect errors in decoding.");
							}
							input = input.replace(/[^A-Za-z0-9\+\/\=]/g, "");

							do {
								enc1 = keyStr.indexOf(input.charAt(i++));
								enc2 = keyStr.indexOf(input.charAt(i++));
								enc3 = keyStr.indexOf(input.charAt(i++));
								enc4 = keyStr.indexOf(input.charAt(i++));

								chr1 = (enc1 << 2) | (enc2 >> 4);
								chr2 = ((enc2 & 15) << 4) | (enc3 >> 2);
								chr3 = ((enc3 & 3) << 6) | enc4;

								output = output + String.fromCharCode(chr1);

								if (enc3 != 64) {
									output = output + String.fromCharCode(chr2);
								}
								if (enc4 != 64) {
									output = output + String.fromCharCode(chr3);
								}

								chr1 = chr2 = chr3 = "";
								enc1 = enc2 = enc3 = enc4 = "";

							} while (i < input.length);

							return output;
						}
					};

					/* jshint ignore:end */
				});
