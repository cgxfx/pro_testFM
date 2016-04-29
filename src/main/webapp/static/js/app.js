angular.module('FM.Directive',['FM.directive.contentImg','FM.directive.songsList','FM.directive.albumsList','FM.directive.listHead','FM.directive.musicTransmit',
								'FM.directive.albumNameList','FM.directive.singerNameList', 'FM.directive.contextMenu']);
angular.module('FM.Controller', ['FM.controller.app']);
angular.module('FM.i18n', ['FM.i18n.zh', 'FM.i18n.czh']);

angular.module("FMApp",['ui.router', 'FM.config', 'FM.Directive', 'FM.Controller', 'FM.i18n'])
	.config(function($stateProvider, $urlRouterProvider){
	
	$urlRouterProvider.otherwise('/');
	
	
	$stateProvider.state('home', {
		url : '/',
		templateUrl : 'js/views/home/home.html',
	})
	.state('login', {
		url : '/login',
		templateUrl : 'js/views/login/login.html',
		controller : loginCtrl,
	})
	.state('heart', {
		url : '/heart',
		templateUrl : 'js/views/heart/heart.html',
		controller : heartCtrl,
	})
	.state('personal', {
		url : '/personal',
		templateUrl : 'js/views/personal/personal.html',
		controller : personalCtrl,
	})
	.state('filtrate', {
		url : '/filtrate',
		templateUrl : 'js/views/filtrate/filtrate.html',
		controller : filtrateCtrl,
	})
	.state('search', {
		url : '/search',
		templateUrl : 'js/views/search/search.html',
		controller : searchCtrl,
	})
	
});