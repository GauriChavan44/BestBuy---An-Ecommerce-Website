/**
 * 
 */
angular.module('app').constant('AUTH_EVENTS', {
	loginSuccess : 'auth-login-success',
	logoutSuccess : 'auth-logout-success',
	loginFailed : 'auth-login-falied',
	notAuthenticated : 'auth-not-authenticated',
	notAuthorized : 'auth-not-authorized',
	sessionTimeout : 'auth-session-timeout',

}).constant('USER_ROLES', {
	admin : 'admin_role',
	customer : 'customer_role',
	guest : 'public_role'
}).constant('APP_CONSTANT', {
	DEMO : false,
	AUTH_KEY : 'auth-token',
	REMOTE_HOST : 'http://localhost:8080/BestBuyWebsite_v1/rest'
}).constant('ADMIN_EVENTS', {
	disableUserSuccess : 'user-disabled',
	disableUserFailed : 'user-not-disabled',
	logoutSuccess : 'auth-logout-success'
});
