function filtrateCtrl($scope, $http, $translate, 
	GET_DISCOVERY_SONGS, GET_SONGS_BY_SINGER_ID, GET_SONGS_BY_ALBUM_ID, HANDLE_DEL_FAVOR_SONG, HANDLE_APPEND_FAVOR_SONG) {
	$scope.selectedMenuType = null;
	$scope.staticMenuType = {
		menu:[
			{type : [
				{name : 'ALL', value : $translate.instant('ALL')},
			]},

			{type : [
				{name: 'SADNESS', value : $translate.instant('SADNESS')},
				{name: 'QUIETNESS', value : $translate.instant('QUIETNESS')},
				{name: 'HAPPINESS', value : $translate.instant('HAPPINESS')},
			]},

			{type : [
				{name: 'HEARTBROKEN', value : $translate.instant('HEARTBROKEN')},
				{name: 'MISSING', value : $translate.instant('MISSING')},
				{name: 'SWEETIE', value : $translate.instant('SWEETIE')},
			]},

			{type : [
				{name: 'INSPIRATION', value : $translate.instant('INSPIRATION')},
				{name: 'CATHARSIS', value : $translate.instant('CATHARSIS')},
				{name: 'WARMING', value : $translate.instant('WARMING')},
			]},

			{type : [
				{name: 'LONELINESS', value : $translate.instant('LONELINESS')},
				{name: 'REBELLIOUS', value : $translate.instant('REBELLIOUS')},
				{name: 'EXCEPTATION', value : $translate.instant('EXCEPTATION')},
			]},
		]};

	$scope.menusInfo = {show:'', detailShow:''};

	$scope.headInfo = {};
	$scope.songListModel = null;
	$scope.songsInfo = {};
	$scope.albumSongs = null;

	$scope.getIntoTypeMenu = function(value) {
		angular.forEach($scope.staticMenuType.menu, function(obj){
			angular.forEach(obj.type, function(obk){
				var str = "#menuTypeId" + obk.name;
				$(str).removeClass("btn-success");
			});
		})
		var str = "#menuTypeId" + value;
		$(str).addClass("btn-success");

		$http.post(GET_DISCOVERY_SONGS, {'menuType':value})
			.success(function(data){
				if (data.code == 200) {
					$scope.menusInfo = data.clazz;
					$scope.menusInfo.show = true;
					$scope.detailShow = false;
					angular.forEach($scope.menusInfo, function(obj){
						if (obj.songs != null && obj.songs.length > 0) {
							obj.menu.num = $translate.instant('TOTAL_CONTAIN') + obj.songs.length + $translate.instant('NUMBER');
						}
					});
				} else {
					alert(data.msg);
				}
			}).error(function(data){
				alert("ERROR filtrateCtrl getIntoTypeMenu");
			});
	}

	$http.post(GET_DISCOVERY_SONGS, {})
		.success(function(data){
			if (data.code == 200) {
				$scope.menusInfo = data.clazz;
				$scope.menusInfo.show = true;
				$scope.detailShow = false;
				angular.forEach($scope.menusInfo, function(obj){
					if (obj.songs != null && obj.songs.length > 0) {
						obj.menu.num = $translate.instant('TOTAL_CONTAIN') + obj.songs.length + $translate.instant('NUMBER');
					}
				});
			} else {
				alert(data.msg);
			}
		}).error(function(data){
			alert("ERROR filtrateCtrl");
		});



	$scope.setStyle=function(index){
		var str=document.getElementById("disMenusId"+index);
	    str.style.color="white";
		str.style.backgroundColor="#16BC5C"
	}
	$scope.getRidOfStyle=function(index){
		var str=document.getElementById("disMenusId"+index);
	    str.style.color="#333333";
		str.style.backgroundColor=""
	}

	$scope.getIntoMenu = function(value) {
		angular.forEach($scope.menusInfo, function(obj){
			if (obj.menu.id == value) {
				$scope.headInfo = obj.menu;
				if (obj.songs != null) {
					$scope.songsInfo = obj.songs;
					$scope.headInfo.num = obj.songs.length;
					$scope.headInfo.num = $translate.instant('TOTAL') + $scope.headInfo.num + $translate.instant('NUMBER');
				}
				$scope.headInfo.isMenu = 1; // isMenu 1 用户歌单  其余专辑列表
				if ($scope.headInfo.createdAtStr != null) {
					$scope.headInfo.createdAtStr += " " + $translate.instant("MENU_CREATED");
				}
				$scope.headInfo.isSinger = -1; //isSinger 1 精确查找歌手  
				$scope.songListModel = obj;
				$scope.menusInfo.detailShow = true;
			}
		});
	}

	$scope.playAllSongs = function(songList) {
		if (songList.isSinger == "1" && $scope.albumSongs != null) {
			angular.forEach($scope.albumSongs, function(obj){
				if (obj.album.id == songList.albumId) {
					$scope.$parent.songListModel = obj;
				}
			});
		} else {
			if ($scope.songListModel == null) {
				$scope.getIntoMenu(songList);
			}
			$scope.$parent.songListModel = $scope.songListModel;
		}
		$scope.$parent.songListModel.index = songList.index;
	}

	$scope.getSongsBySingerId = function(value) {
		$http.post(GET_SONGS_BY_SINGER_ID, {'singerId':value})
			.success(function(data){
				if (data.code == 200) {
					$scope.headInfo = data.clazz.singer;
					if (data.clazz.albumSongs != null) {
						var songsNum = 0;
						$scope.headInfo.albumsNum = data.clazz.albumSongs.length;
						angular.forEach(data.clazz.albumSongs,function(obj){
							songsNum += obj.songs.length;
							if (obj.album.createdAtStr != null) {
								obj.album.createdAtStr += " " + $translate.instant("ALBUM_PUBLISHED");
							}
						});
						$scope.headInfo.songsNum = songsNum;
					}
					$scope.headInfo.isMenu = -1;
					$scope.headInfo.isSinger = 1;
					$scope.albumSongs = data.clazz.albumSongs;
				} else {
					alert(data.msg);
				}
			}).error(function(data){
				alert("ERROR heartCtrl getSongsBySingerId");
			});
	}


	$scope.getSongsByAlbumId = function(value) {
		$http.post(GET_SONGS_BY_ALBUM_ID, {'albumId':value})
			.success(function(data){
				if (data.code == 200) {
					$scope.headInfo = data.clazz.album;
					if (data.clazz.songs != null) {
						$scope.headInfo.num = data.clazz.songs.length;
						$scope.headInfo.num = $translate.instant('TOTAL') + $scope.headInfo.num + $translate.instant('NUMBER');
					}
					$scope.headInfo.isMenu = -1;
					$scope.headInfo.isSinger = -1;
					if ($scope.headInfo.createdAtStr != null) {
						$scope.headInfo.createdAtStr += " " + $translate.instant("ALBUM_PUBLISHED");
					}
					$scope.songsInfo = data.clazz.songs;
					$scope.songListModel = data.clazz;
				} else {
					alert(data.msg);
				}
			}).error(function(data){
				alert("ERROR heartCtrl getSongsByAlbumId")
			});
	}

	$scope.delFavor = function(value) {
		$http.post(HANDLE_DEL_FAVOR_SONG, {'songId':value})
			.success(function(data){
				if (data.code == 200) {

				} else {
					alert(data.msg);
				}
			}).error(function(data){
				alert("ERROR heartCtrl delFavor");
			});
	}

	$scope.appendFavor = function(value) {
		$http.post(HANDLE_APPEND_FAVOR_SONG, {'songId':value})
			.success(function(data){
				console.log(value);
				if (data.code == 200) {

				} else {
					alert(data.msg);
				}
				console.log(data);
			}).error(function(data){
				alert("ERROR heartCtrl appendFavor");
			});
	}
}