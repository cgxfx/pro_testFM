function searchCtrl($scope, $http, $translate,
	GET_SONGS_BY_SONG_NAME, GET_SINGERS_BY_SINGER_NAME, GET_ALBUMS_BY_ALBUM_NAME,
	GET_SONGS_BY_SINGER_ID, GET_SONGS_BY_ALBUM_ID, HANDLE_APPEND_FAVOR_SONG, HANDLE_DEL_FAVOR_SONG) {
	$scope.searchTxt;
	$scope.Type = "SONG";
	$scope.nextInfo = false;
	$scope.songsInfo = {'show':false};
	$scope.singersInfo = {'show':false};
	$scope.albumsInfo = {'show':false};

	$scope.headInfo = {};
	$scope.songListModel = {};
	$scope.songsInfo = {};
	$scope.albumSongs = null;

	$scope.viewCtrl1 = function() {
		$scope.nextInfo = false;

		$scope.songsInfo.show = true;
		$scope.singersInfo.show = true;
		$scope.albumsInfo.show = true;
	}

	$scope.viewCtrl2 = function() {
		$scope.nextInfo = true;

		$scope.songsInfo.show = false;
		$scope.singersInfo.show = false;
		$scope.albumsInfo.show = false;
	}

	$scope.searchFun = function() {
		if ($scope.Type == 'SONG') {
			$http.post(GET_SONGS_BY_SONG_NAME, {'songName':$scope.searchTxt})
				.success(function(data){
					if (data.code == 200) {
						$scope.songsInfo = data.clazz;
						$scope.viewCtrl1();
					} else {
						alert(data.msg);
					}
				}).error(function(data){
					alert("ERROR searchCtrl searchFun SONG");
				});
		} else if ($scope.Type == 'SINGER') {
			$http.post(GET_SINGERS_BY_SINGER_NAME, {'singerName':$scope.searchTxt})
				.success(function(data){
					if (data.code == 200) {
						$scope.singersInfo = data.clazz;
						$scope.viewCtrl1();
					} else {
						alert(data.msg);
					}
				}).error(function(data){
					alert("ERROR searchCtrl searchFun SINGER");
				});
		} else if ($scope.Type == 'ALBUM') {
			$http.post(GET_ALBUMS_BY_ALBUM_NAME, {'albumName':$scope.searchTxt})
				.success(function(data){
					if (data.code == 200) {
						$scope.albumsInfo = data.clazz;
						$scope.viewCtrl1();
					} else {
						alert(data.msg);
					}
				}).error(function(data){
					alert("ERROR searchCtrl searchFun ALBUM");
				});
		}
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
					$scope.viewCtrl2();
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
					$scope.viewCtrl2();
				} else {
					alert(data.msg);
				}
			}).error(function(data){
				alert("ERROR heartCtrl getSongsByAlbumId")
			});
	}

	$scope.searchBy=function(value){
		if(value==0){
			$scope.Type="SONG";
			
			serByName.style.backgroundColor="#FFCE82";
			serBySinger.style.backgroundColor="";
			serByAlbums.style.backgroundColor="";
		}else if(value==1){
			$scope.Type="SINGER";
			
			serByName.style.backgroundColor="";
			serBySinger.style.backgroundColor="#FFCE82";
			serByAlbums.style.backgroundColor="";
		}else{
			$scope.Type="ALBUM";
			
			serByName.style.backgroundColor="";
			serBySinger.style.backgroundColor="";
			serByAlbums.style.backgroundColor="#FFCE82";
		}
	}

	$scope.AddSongs = function(value) {
		var isExist = false;
		angular.forEach($scope.$parent.songListModel.songs, function(obj){
			if(obj.id == $scope.songsInfo[value.index].id) {
				isExist = true;
			}
		});
		if (!isExist) {
			for (var i = 0, size = $scope.songsInfo.length; i < size; i++) {
				if (i == value.index) {
					console.log($scope.$parent.songListModel);
					if ($scope.$parent.songListModel.songs.length == 1) {
						var id = $scope.$parent.songListModel.songs[0].id;
						if (id == null || id == "") {
							$scope.$parent.songListModel.songs.pop();
						}
					}
					$scope.$parent.songListModel.songs.push($scope.songsInfo[i]);
					$scope.$parent.songListModel.index = $scope.$parent.songListModel.songs.length - 1;
					i = size;
				}
			}
		}
	}

	$scope.delFavor = function(value) {
		$http.post(HANDLE_DEL_FAVOR_SONG, {'songId':value})
			.success(function(data){
				if (data.code == 200) {

				} else {
					alert(data.msg);
				}
			}).error(function(data){
				alert("ERROR searchCtrl delFavor");
			});
	}

	$scope.appendFavor = function(value) {
		$http.post(HANDLE_APPEND_FAVOR_SONG, {'songId':value})
			.success(function(data){
				if (data.code == 200) {

				} else {
					alert(data.msg);
				}
				console.log(data);
			}).error(function(data){
				alert("ERROR heartCtrl appendFavor");
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
			$scope.$parent.songListModel = $scope.songListModel;
		}
		$scope.$parent.songListModel.index = songList.index;
	}
}