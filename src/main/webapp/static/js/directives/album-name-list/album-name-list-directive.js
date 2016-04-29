angular.module('FM.directive.albumNameList',[]).directive('albumNameList',function(){
	return{
		restirct:'EA',
		scope:{
			albumsInfo:'=',
			clickAlbumCallBack:'&',
			clickSingerCallBack:'&',
		},
		template:'<div style="margin-top:30px;height:auto">' +
					'<div class="row" ng-repeat="albums in albumsInfo" style="margin-bottom:10px;">' +
						'<div id="singerId{{$index}}" class="col-md-12" ng-mouseout="getRidOfStyle($index)" ng-mouseover="setStyle($index)">' +
							'<div class="col-md-1" ng-click="getSongsByAlbumId(albums.id)">' + 
								'<img ng-src="{{albums.avatrImg}}" width="100%" />' +
							'</div>' +
							'<div class="col-md-6" style="margin-left:-25px;margin-top:10px" ng-click="getSongsByAlbumId(albums.id)">{{albums.name}}</div>' +
							'<div class="col-md-5" style="margin-top:10px" ng-click="getSongsBySingerId(albums.singerId)">{{albums.singerName}}</div>' +
						'</div>' + 
					'</div>'+
				'</div>',
		controller:function($scope){
			$scope.getSongsByAlbumId = function(index) {
				$scope.clickAlbumCallBack({
					value : index
				});
			}
			$scope.getSongsBySingerId = function(index) {
				$scope.clickSingerCallBack({
					value : index
				});
			}
		},
		link:function($scope){
			$scope.setStyle=function(index){
				var str=document.getElementById("singerId"+index);
			    str.style.color="white";
				str.style.backgroundColor="#16BC5C"
			}
			$scope.getRidOfStyle=function(index){
				var str=document.getElementById("singerId"+index);
			    str.style.color="#333333";
				str.style.backgroundColor=""
			}
		}
	}
});