angular.module('FM.i18n.czh', ['pascalprecht.translate']).config(function($translateProvider) {
	$translateProvider.translations('czh',{
		HOME : '土豆',
		LOGIN : '登錄',
		SEARCH : '搜索',
		HEART_LIST : '紅心兆赫',
		PERSONAL_LIST : '私人兆赫',
		FILTRATE_LIST : '淘歌',
	});
});