angular.module('FM.directive.musicTransmit',[]).directive('musicTransmit',function($timeout){
	return{
		restrict:'EA',
		scope:{
			songListModel:'=',
		},
		template:'<div>'+
					'<div class="row">'+
						'<div class="col-md-2" >'+
							'<img ng-src="{{songsInfo.avatrImg}}" id="musicImg" style="border-radius:50%;width:100%">'+
						'</div>'+
						'<div class="col-md-4">' +
							'<div style="margin-top:20%">' + 
								'<audio id="audioId" autoplay="true" controls="true" style="width:100%" ng-src="{{songsInfo.linkUrl}}"></audio>' +
							'</div>'+
						'</div>'+
						'<div class="col-md-2">' +
							'<div style="margin-top:43%">' + 
								'<table width="100%">'+
									'<th>' + 
										'<acronym title={{"SINGLEMODE"|translate}}><img id="singleMode" ng-click="transmitMode(1)" src="img/singleMode.png"></acronym>'+
									'</th>' +
									'<th>' + 
										'<acronym title={{"CYCLEMODE"|translate}}><img id="cycleMode" style="border-left:5px #00FF7C solid;" ng-click="transmitMode(2)" src="img/cycleMode.png"></acronym>'+
									'</th>' +
									'<th>' + 
										'<acronym title={{"RANDOMMODE"|translate}}><img id="randomMode" ng-click="transmitMode(3)" src="img/randomMode.png"></acronym>'+
									'</th>' +
								'</table>' +
								
							'</div>'+
						'</div>' +
					'</div>'+
					'<div class="row">'+
						'<div style="margin-top:-60px">' + 
							'<acronym title={{"BACKSONG"|translate}}><div ng-click="backward()" class="col-md-offset-3 col-md-1 fa-2x icon-backward"></div></acronym>'+
							'<acronym title={{"FORSONG"|translate}}><div ng-click="forward()" class="col-md-offset-1 col-md-1 fa-2x icon-forward"></div></acronym>'+
						'</div>'+
					'</div>'+
				'</div>',

		controller:function($scope){
			$scope.transmitModes = 2;
			$scope.songsInfo={avatrImg:'img/login.png',linkUrl:''};
			$scope.backward=function(){
				 $scope.songListModel.index--;
			}
			
			$scope.forward=function(){
				$scope.songListModel.index++;
			}
			
			$scope.transmitMode=function(index){
				$scope.transmitModes = index;
				if(index == 1){
					singleMode.style.borderLeft="5px #00FF7C solid";
					cycleMode.style.borderLeft="";
					randomMode.style.borderLeft="";
				}else if(index == 2){
					singleMode.style.borderLeft="";
					cycleMode.style.borderLeft="5px #00FF7C solid";
					randomMode.style.borderLeft="";
				}else if (index == 3){
					singleMode.style.borderLeft="";
					cycleMode.style.borderLeft="";
					randomMode.style.borderLeft="5px #00FF7C solid";
				}
			}
		},
		link:function($scope){
			$('#audioId').bind('ended', function(){
				console.log($scope.transmitModes);
				if ($scope.transmitModes == 1) {
					
				} else if ($scope.transmitModes == 2) {
					$scope.songListModel.index++;
				} else if ($scope.transmitModes == 3) {
					$scope.songLisModel.index = Math.floor(Math.random() * $scope.songListMode.songs.length);
				}
				console.log($scope.songListModel);
				$timeout(function(){
					var x=document.getElementById('audioId');
					x.play();
				},0)
			});
			$scope.$watch('songsInfo.avatrImg',function(newVal){
				if(newVal!="img/login.png"){
					musicImg.style.border="5px outset #10A64F";
				}else{
					musicImg.style.border="";
				}
			});
			$scope.$watch('songListModel', function(newVal){
				if (newVal.index == null || newVal.index == "" || newVal.index >= newVal.songs.length) {
					newVal.index = 0;
				}
				if (newVal.index < 0) {
					newVal.index = newVal.songs.length - 1;
				}
				for (var i = 0, size = newVal.songs.length; i < size; i++) {
					if (newVal.songs[newVal.index].archive) {
						newVal.index++;
						if (newVal.index == size) 
							newVal.index = 0;
					} else {
						if (newVal.songs[newVal.index].id != null && newVal.songs[newVal.index].id != "") {
							$scope.songsInfo.avatrImg = newVal.songs[newVal.index].avatrImg;
							$scope.songsInfo.linkUrl = newVal.songs[newVal.index].linkUrl;
							i = size;
						}
					}
				}
			}, true);
		}
	}
});