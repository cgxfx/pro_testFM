angular.module("FM.controller.app", []).controller('appCtrl', function($rootScope, $scope, $translate, $http, $window,
		GET_OR_REGISTER_VISITOR){
	$rootScope.Authenticated = -1;

	$scope.songListModel = {
		index : '',
		menu : {
			id : '',
			visitorId : '',
			userId : '',
			name : '',
			avatrImg : '',
			createdAt : '',
			valid : '',
			shareTitle : '',
			menuType : ''
		},
		album : {
			id : '',
			singerId : '',
			singerName : '',
			name : '',
			publishTime : '',
			intro : '',
			avatrImg : '',
			archive : ''
		},
		songs : [{
			id : '',
			albumId : '',
			albumName : '',
			singerId : '',
			singerName : '',
			name : '',
			lyric : '',
			avatrImg : '',
			intro : '',
			linkUrl : '',
			archive : '',
			archiveColor : ''
		}],
	}

	$translate.use('zh');
	
	$scope.titleMouseOver = function(temp) {
		$("#" + temp).css("backgroundColor", "#F0EEE8");
	};
	
	$scope.titleMouseLeave = function(temp) {
		$("#" + temp).css("backgroundColor", "");
	};
	
	angular.element($window).bind('resize', function() {
		$scope.MainPageWidth = $("#MainPage").width();
		$scope.windowHeight=$window.innerHeight;
		$scope.$apply();
	});
	
	$http.post(GET_OR_REGISTER_VISITOR)
		.success(function(data){
			if (data.code == 200) {
				if (data.clazz.id != null) {
					$rootScope.Authenticated = 1;
				} else {
					console.log("0000000000");
				}
			} else {
				alert(data.msg);
			}
		}).error(function(data){
			alert("ERROR GET_OR_REGISTER_VISITOR");
		});
});
