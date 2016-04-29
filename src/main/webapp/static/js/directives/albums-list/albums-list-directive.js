angular.module('FM.directive.albumsList',[]).directive('albumsList',function(){
	return{
		restirct:'EA',
		scope:{
			albumSongs:'=',
			clickSongCallBack : '&',	
			clickAlbumCallBack : '&',	
			delFavorCallBack : '&',
			appendFavorCallBack : '&',
		},
		template:'<div class="row" style="margin-top:30px;margin-left:35px;"}">'+
					'<div class="col-md-2">' +
						'<div ng-click="playAlbumSongs()"><img ng-src="{{albumSongs.album.avatrImg}}" style="width:100%"></div>'+
						'<div style="text-align:right">{{albumSongs.album.createdAtStr}}</div>'+
					'</div>' +
					'<div class="col-md-9">'+
						'<div class="row" style="font-size:20px">'+
							'<div class="col-md-9" ng-click="playAlbumSongs()"><strong>{{albumSongs.album.name}}</strong></div>' +
							'<div class="col-md-1 icon-play-circle" ng-click="playAllSongs(-1)"></div>' +
						'</div>' +
						'<div class="row" style="margin-top:10px">'+
							'<div class="col-md-12">' +
								'<div class="row" id=songId{{$index}} ng-repeat="songs in albumSongs.songs" ng-mouseout="getRidOfStyle($index)"'+
									'ng-mouseover="setStyle($index)" style="color:{{songs.archiveColor}}" archiveColor="{{songs.archiveColor}}">'+
									'<div class="col-md-1" style="text-align:right">{{$index+1}}</div>'+
									'<div ng-if="songs.isFavor?true:false" ng-click="delFavor(songs.id)" style="margin-top:3px" class="col-md-1 icon-heart"></div>'+
									'<div ng-if="songs.isFavor?false:true" ng-click="appendFavor(songs.id)" style="margin-top:3px" class="col-md-1 icon-heart-empty"></div>'+
									'<div class="col-md-5">{{songs.name}}</div>'+
									'<div ng-click="playAllSongs($index)" style="margin-top:3px" class="col-md-1 icon-play"></div>' +
									'<div class="col-md-offset-1 col-md-1">{{songs.duration}}</div>'+
								'</div>' +		
							'</div>' +
						'</div>'+
					'</div>'+
				 '</div>',
		controller:function($scope, $translate){
			$scope.playAllSongs=function(indexId){
				if (indexId != "-1") {
					var str=document.getElementById("songId"+indexId);
					if (str.getAttribute("archiveColor") == '#999999') {
						alert($translate.instant("WITHOUT_COPYRIGHT"));
					}
				}
				if (indexId == "-1") indexId = 0;
				$scope.clickSongCallBack({
					value : {
						isSinger : 1,
						index : indexId,
						albumId : $scope.albumSongs.album.id,
					},
				});
			}
			$scope.playAlbumSongs = function() {
				$scope.clickAlbumCallBack({
					value : $scope.albumSongs.album.id
				});
			}
			$scope.delFavor = function(index) {
				angular.forEach($scope.albumSongs.songs,function(obj){
					if (obj.id == index) {
						obj.isFavor = false;
					}
				});
				$scope.delFavorCallBack({
					value : index
				});
			}
			$scope.appendFavor = function(index) {
				angular.forEach($scope.albumSongs.songs,function(obj){
					if (obj.id == index) {
						obj.isFavor = true;
					}
				});
				$scope.appendFavorCallBack({
					value : index
				});
			}
		},
		link:function($scope){
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
		}
	}
});