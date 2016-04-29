angular.module('FM.directive.singerNameList',[]).directive('singerNameList',function(){
	return{
		restirct:'EA',
		scope:{
			singersInfo:'=',
			clickCallBack:'&',
		},
		template:'<div style="margin-top:30px;height:auto">' +
					'<div class="row" ng-repeat="singers in singersInfo" style="margin-bottom:10px;">' +
						'<div id="singerId{{$index}}" class="col-md-12" ng-mouseout="getRidOfStyle($index)" ng-mouseover="setStyle($index)"' +
							'ng-click="getSongsBySingerId(singers.id)">' +
							'<div class="col-md-1">' + 
								'<img ng-src="{{singers.avatrImg}}" width="100%" />' +
							'</div>' +
							'<div class="col-md-11" style="margin-left:-25px;margin-top:10px">{{singers.name}}</div>' +
						'</div>' + 
					'</div>'+
				'</div>',
		controller:function($scope){
			$scope.getSongsBySingerId = function(index) {
				$scope.clickCallBack({
					value : index,
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