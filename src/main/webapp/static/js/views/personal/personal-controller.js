function personalCtrl($scope, $http, $translate, 
	GET_USER_SONGS, GET_SONGS_BY_SINGER_ID, GET_SONGS_BY_ALBUM_ID, HANDLE_DEL_FAVOR_SONG, HANDLE_APPEND_FAVOR_SONG,
	HANDLE_DEL_MENU, HANDLE_EDIT_MENU, HANDLE_CREATE_MENU) {

	$scope.headInfo = {};
	$scope.songListModel = null;
	$scope.songsInfo = {};
	$scope.albumSongs = null;

	$scope.menusInfo = {show:'', detailShow:'', editShow:''};

	$http.post(GET_USER_SONGS, {})
		.success(function(data){
			if (data.code == 200) {
				$scope.menusInfo = data.clazz;
				$scope.menusInfo.show = true;
				$scope.detailShow = false;
			} else {
				$scope.menusInfo.show = false;
				alert(data.msg);
			}
		}).error(function(data){
			alert("ERROR personalCtrl")
		});

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

	$scope.delMenu = function(value) {
		var confirmDel = confirm($translate.instant("CONFIRM_DEL_MENU"));
		if (confirmDel == true) {
			console.log(value);
			$http.post(HANDLE_DEL_MENU, {'menuId':value})
				.success(function(data){
					if (data.code == 200) {
						for (var i = 0; i < $scope.menusInfo.length; i++) {
							if ($scope.menusInfo[i].menu.id == value) {
								i = $scope.menusInfo.length;
								$scope.menusInfo.splice(i, 1);
							}
						}
					} else {
						alert(msg);
					}
				}).error(function(data){
					alert("ERROR personalCtrl delMenu");
				});
		}
	}

	$scope.editMenu = function(value, index) {
		$scope.getIntoMenu(value);
		$scope.menusInfo.show = false;
		$scope.menusInfo.editShow = true;
		if (index == 1) {
			$scope.headInfo.valid = true;
			$scope.headInfo.btText = $translate.instant('SHARE_MENU');
		} else {
			$scope.headInfo.btText = $translate.instant('EDIT_MENU');
		}
	}

	$scope.doEditMenu = function() {
		var shareTitle = null, menuType = null;
		if ($scope.headInfo.id == null) {
			$http.post(HANDLE_CREATE_MENU, {'menuName':$scope.headInfo.name})
				.success(function(data){
					if (data.code == 200) {
						location.reload(true);
					} else {
						alert(data.msg);
					}
				}).error(function(data){
					alert("ERROR personalCtrl doEditMenu  creatMenu");
				});
		} else {
			if ($scope.headInfo.valid) {
				shareTitle = $scope.headInfo.shareTitle;
				menuType = $scope.headInfo.menuType;
			}
			$http.post(HANDLE_EDIT_MENU, {'menuId':$scope.headInfo.id, 'menuName':$scope.headInfo.name, 'shareTitle':shareTitle,
											'menuType':menuType, 'valid':$scope.headInfo.valid})
				.success(function(data){
					if (data.code == 200) {
						$scope.menusInfo.show = true;
					} else {
						alert(data.msg);
					}
				}).error(function(data){
					alert("ERROR personalCtrl doEditMenu editMenu");
				});
		}
	}

	$scope.creatMenu = function() {
		$scope.headInfo.name = null;
		$scope.headInfo.valid = false;
		$scope.headInfo.shareTitle = null;
		$scope.headInfo.menuType = null;

		$scope.menusInfo.show = false;
		$scope.menusInfo.editShow = true;
		$scope.headInfo.btText = $translate.instant('CREATE_MENU');
	}

	$scope.setStyle=function(index){
		var str=document.getElementById("menusId"+index);
	    str.style.color="white";
		str.style.backgroundColor="#16BC5C"
	}

	$scope.getRidOfStyle=function(index){
		var str=document.getElementById("menusId"+index);
	    str.style.color="#333333";
		str.style.backgroundColor=""
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