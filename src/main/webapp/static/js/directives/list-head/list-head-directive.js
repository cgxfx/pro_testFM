angular.module('FM.directive.listHead',[]).directive('listHead',function(){
	return{
		restirct:'EA',
		transclude : true,
		scope:{
			headInfo:'=',
			clickCallBack:'&',
		},
		template:'<div class="row">'+
					'<div class="col-md-2">'+
				 		'<img ng-src="{{headInfo.avatrImg}}" style="width:100%">'+
				 	'</div>'+
				 	'<div class="col-md-6" style="margin-left:-25px">'+
				 		'<table>'+
				 			'<tr style="text-align:left;">'+
				 				'<td style="font-size:50px;font-weight:bold;color:#DBB982;">{{headInfo.name | translate}}</td>'+ 
				 				'<td style="font-size:25px;font-weight:normal;color:#CBB28D;">{{headInfo.num}}</td>'+
				 			'</tr>'+
				 			'<tr ng-if="headInfo.isSinger==1?false:true">'+
				 				'<td>'+
				 					'<button style="cusor:hand;font-size:30px;padding-left:10px;" ng-click="playAllSongs()" class="icon-caret-right fa-3x btn btn-primary">{{"PLAYALLSONGS"|translate}}</button>'+
				 				'</td>'+
				 			'</tr>'+
				 			'<tr ng-if="headInfo.isSinger==1?true:false">' +
				 				'<td style="font-size:20px;">'+
				 					'<strong>{{"SONGS_NUMBER"|translate}}</strong>&nbsp&nbsp{{headInfo.songsNum}}' +
				 				'</td>'+
				 			'</tr>' +
				 			'<tr ng-if="headInfo.isSinger==1?true:false">' +
				 				'<td style="font-size:20px;">' +
				 					'<strong>{{"ALBUMS_NUMBER"|translate}}</strong>&nbsp&nbsp{{headInfo.albumsNum}}' +
				 				'</td>' +
				 			'</tr>' +
				 		'</table>'+
				 	'</div>'+
				 	'<div class="col-md-4" style="margin-top:20px;">' +
				 		'<div ng-if="headInfo.isMenu==1?(headInfo.valid?true:false):false">' + 
				 			'<label>{{"MENU_SHARE_TITLE" | translate}}</label>&nbsp&nbsp&nbsp&nbsp&nbsp{{headInfo.shareTitle}}' +
				 		'</div>' +
				 		'<div ng-if="headInfo.isMenu==1?(headInfo.valid?true:false):false">' + 
				 			'<label>{{"MENU_SHARE_TYPE" | translate}}</label>&nbsp&nbsp&nbsp&nbsp&nbsp{{headInfo.menuType | translate}}' +
				 		'</div>' +
				 		'<div><label>{{headInfo.createdAtStr}}</label></div>' +
				 	'</div>' +
				 '</div>'+
				 '<div class="row" ng-if="headInfo.isMenu==1?false:(headInfo.intro==null?false:true)" style="margin-top:5px;" >'+
				 	'<div style="padding-left:20px;">'+
				 		'<div style="color:#DBB982;font-weight:bold;font-size:18px;" ng-if="headInfo.isSinger==1?false:true">'+
				 			'{{"ALUM_INFORMATION"|translate}}'+
				 		'</div>'+
				 		'<div style="color:#DBB982;font-weight:bold;font-size:18px;" ng-if="headInfo.isSinger==1?true:false">'+
				 			'{{"SINGER_INFORMATION"|translate}}'+
				 		'</div>'+
				 		'<div style="color:#CBB28D;padding-left:35px;" class="col-md-11">'+
				 			'{{headInfo.intro}}'+
				 		'</div>'+
				 	'</div>'+
				 '</div>',
		controller:function($scope){
			$scope.playAllSongs=function(){
				$scope.clickCallBack({
					value : {
						isSinger : -1,
						index : 0,
					},
				});
			};
		}
	}
});