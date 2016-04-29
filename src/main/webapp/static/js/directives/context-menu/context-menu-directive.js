angular.module('FM.directive.contextMenu', []).directive('conextMenuTest', function($window){
	return {
		restirct:'EA',
		scope:{
			menuId:'=',
			songId:'=',
			userMenus:'=',
		},
		template:'<div style="width:120px;height:auto;z-index:10;position:absolute;left:0;top:0;border:1px solid black;">' +
					'<div ng-if="menuId==null?false:true" ng-click="delMenuSong()">{{"DEL_MENU_SONG" | translate}}</div>' + 
					'<div class="row"><div class="col-md-12">{{"APPEND_MENU_SONG" | translate}}</div></div>' +
					'<div class="row" id="contextMenu{{$index}}" ng-repeat="menu in userMenus" ng-click="appendMenuSong(menu.id)">' + 
						'<div class="col-md-offset-2 col-md-10">{{menu.name | translate}}</div>' +
					'</div>' + 
				'</div>',
		controller:function($scope, $http, $translate, 
			HANDLE_APPEND_MENU_SONG, HANDLE_DEL_MENU_SONG) {

			$scope.delMenuSong = function() {
				$http.post(HANDLE_DEL_MENU_SONG, {'menuId':$scope.menuId, 'songId':$scope.songId})
					.success(function(data){
						if (data.code == 200) {

						} else {
							alert(data.msg);
						}
					}).error(function(data){
						alert("ERROR conextMenu delMenuSong");
					});
			}

			$scope.appendMenuSong = function(value) {
				$http.post(HANDLE_APPEND_MENU_SONG, {'menuId':value, 'songId':$scope.songId})
					.success(function(data){
						if (data.code == 200) {

						} else {
							alert(data.msg);
						}
					}).error(function(data){
						alert("ERROR conextMenu appendMenuSong");
					});
			}
		}
	}
});