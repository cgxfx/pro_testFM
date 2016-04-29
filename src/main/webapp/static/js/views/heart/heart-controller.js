function heartCtrl($scope, $translate, $http,
	GET_VISITOR_SONGS, GET_SONGS_BY_SINGER_ID, GET_SONGS_BY_ALBUM_ID,
	HANDLE_APPEND_FAVOR_SONG, HANDLE_DEL_FAVOR_SONG) {
	$scope.headInfo = {};
	$scope.songListModel = null;
	$scope.songsInfo = {};
	$scope.albumSongs = {};

	$scope.playAllSongs = function(songList) {
		if (songList.isSinger == "1" && $scope.albumSongs != null) {
			angular.forEach($scope.albumSongs, function(obj){
				if (obj.album.id == songList.albumId) {
					$scope.$parent.songListModel = obj;
				}
			});
		} else {
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

	$http.post(GET_VISITOR_SONGS)
		.success(function(data){
			if (data.code == 200) {
				$scope.headInfo = data.clazz.menu;
				if (data.clazz.songs != null) {
					$scope.songsInfo = data.clazz.songs;
					$scope.headInfo.num = data.clazz.songs.length;
					$scope.headInfo.num = $translate.instant('TOTAL') + $scope.headInfo.num + $translate.instant('NUMBER');
				}
				$scope.headInfo.isMenu = 1; // isMenu 1 用户歌单  其余专辑列表
				if ($scope.headInfo.createdAtStr != null) {
					$scope.headInfo.createdAtStr += " " + $translate.instant("MENU_CREATED");
				}
				$scope.headInfo.isSinger = -1; //isSinger 1 精确查找歌手  
				$scope.songListModel = data.clazz;
			} else {
				alert(data.msg);
			}
		}).error(function(data){
			alert("ERROR heartCtrl playAllSongs");
		});
}

