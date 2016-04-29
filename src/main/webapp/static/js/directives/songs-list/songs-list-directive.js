angular.module('FM.directive.songsList',[]).directive('songsList',function(){
	return{
		restirct:'EA',
		scope:{
			songsInfo:'=',
			clickSongCallBack : '&',	
			clickSingerCallBack : '&',	
			clickAlbumCallBack : '&',	
			delFavorCallBack : '&',
			appendFavorCallBack : '&',
		},
		template:'<div class="row" style="margin-top:30px;overflow:auto;"}">'+
					'<div class="col-md-12">'+
						'<table class="table" style="font-weight:bold;overflow-y:scroll;background-color:white;" frame=void>'+
							'<tr style="font-size:35px;color:#FFC839">'+
								'<th></th>'+
								'<th>{{"SONG"|translate}}</th>'+
								'<th>{{"SINGER"|translate}}</th>'+
								'<th>{{"ALBUM"|translate}}</th>'+
							'</tr>'+
							'<tr id="songId{{$index}}" ng-repeat="songs in songsInfo" ng-mouseout="getRidOfStyle($index)" ng-mouseover="setStyle($index)"'+
								'style="color:{{songs.archiveColor}}" archiveColor="{{songs.archiveColor}}">'+
								'<td style="text-align:right">'+
									'<div class="row">'+
										'<div class="col-md-offset-4 col-md-1">{{$index+1}}</div>'+
										'<div ng-if="songs.isFavor?true:false" style="margin-top:3px;" ng-click="delFavor(songs.id)" class="col-md-1 icon-heart"></div>'+
										'<div ng-if="songs.isFavor?false:true" style="margin-top:3px;" ng-click="appendFavor(songs.id)" class="col-md-1 icon-heart-empty"></div>'+
									'</div>'+
								'</td>'+
								'<td>'+
									'<div class="row">' +
									'<div class="col-md-9">{{songs.name}}</div>'+
									'<div ng-click="playAllSongs($index)" style="margin-top:3px;" class="col-md-1 icon-play"></div>'+
									'</div>' +
								'</td>'+
								'<td><div ng-click="getSongsBySingerId(songs.singerId)">{{songs.singerName}}</div></td>'+
								'<td><div ng-click="getSongsByAlbumId(songs.albumId)">{{songs.albumName}}</div></td>'+
							'</tr>'+
						'</table>'+
					'</div>'+
				 '</div>' + 
				 '<div class="row">' +
				 	// '<div id="contexMenuTest" style="display:none;position:absolute" conext-menu-test user-menus="userMenus"></div>' +
				 '</div>',
		controller:function($scope, $translate, $http, GET_USER_MENUS){
			$scope.playAllSongs=function(indexId){
				var str=document.getElementById("songId"+indexId);
				if (str.getAttribute("archiveColor") == '#999999') {
					alert($translate.instant("WITHOUT_COPYRIGHT"));
				}
				$scope.clickSongCallBack({
					value : {
						index : indexId
					},
				});
			}
			
			$scope.getSongsBySingerId=function(index){
				$scope.clickSingerCallBack({
					value : index
				});
			}
			
			$scope.getSongsByAlbumId=function(index){
				$scope.clickAlbumCallBack({
					value : index
				});
			}

			$scope.delFavor = function(index) {
				angular.forEach($scope.songsInfo,function(obj){
					if (obj.id == index) {
						obj.isFavor = false;
					}
				});
				$scope.delFavorCallBack({
					value : index
				});
			}
			$scope.appendFavor = function(index) {
				angular.forEach($scope.songsInfo,function(obj){
					if (obj.id == index) {
						obj.isFavor = true;
					}
				});
				$scope.appendFavorCallBack({
					value : index
				});
			}

			// $scope.getUserMenus = function() {
			// 	$http.post(GET_USER_MENUS ,{})
			// 		.success(function(data){
			// 			if (data.code == 200) {
			// 				$scope.userMenus = data.clazz;
			// 			} else {
			// 				alert(data.msg);
			// 			}
			// 		}).error(function(data){
			// 			alert("ERROR contextMenu init");
			// 		});
			// }
		},
		link:function($scope, element, $http, GET_USER_MENUS){
			$scope.setStyle=function(index){
				var str=document.getElementById("songId"+index);
			    str.style.color="white";
				str.style.backgroundColor="#16BC5C"
			}
			$scope.getRidOfStyle=function(index){
				var str=document.getElementById("songId"+index);
			    str.style.color=str.getAttribute("archiveColor");
				str.style.backgroundColor=""
			}

			// element.bind("contextmenu", function(event){
			// 	var obj = event.srcElement ? event.srcElement : event.target;
			// 	var obk = document.getElementById("contexMenuTest");
			// 	var indexValue;
			// 	do{
			// 		indexValue = $(obj).attr("id");
			// 		obj = $(obj).parent();
			// 	}while(indexValue == null || indexValue == undefined);
			// 	$scope.getUserMenus();
			// 	obk.style.display='block';
			// 	obk.style.left=event.clientX+'px';
			// 	obk.style.top=event.clientY+'px';
			// 	console.log(obk.style.left + ":" + obk.style.top);
			// });

			// element.bind("click", function(event){
			// 	var obk = document.getElementById("contexMenuTest");
			// 	console.log(obk.style.left + ":" + obk.style.top);
			// 	obk.style.display = 'none';
			// });
		}
	}
});