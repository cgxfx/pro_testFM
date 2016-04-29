angular.module('FM.directive.contentImg',[]).directive('contentImg',function($timeout){
	return{
		restrict:'EA',
		transclude:true,
		$scope:{
			show:'=',
			//MainPageWidth:'@',
		},
		templateUrl:'js/directives/content-img/content-img.html',
		link:function($scope){
			$scope.show=1;
			/*分辨率问题*/
			if($("#MainPage").width()>1600){
				$("#contentPic").width($("#MainPage").width()*0.9);
				$("#contentPic ul li .img-content").width($("#contentPic").width()-280);
			}else if($("#MainPage").width()>1200){
				$("#contentPic").width($("#MainPage").width()*0.8);
				$("#contentPic ul li .img-content").width($("#contentPic").width()-200);
			}else{
				$("#contentPic").width($("#MainPage").width()-120);
				$("#contentPic ul li .img-content").width($("#contentPic").width()-160);
			}
			
			
			$("#contentPic").height($("#contentPic ul li .img-content").width()*0.38);
			$("#contentPic ul li .img-content").height($("#contentPic").height());
			
			$("#contentPic ul").width($("#contentPic ul li:first").width()*$("#contentPic ul li").length);
			$("#contentPic ul").height($("#contentPic ul li:first").height());
			
			$("#contentPic .content-left, #contentPic .content-right").width(100);
			$("#contentPic .content-left").css("left", 0);
			$("#contentPic .content-right").css("right", 0);

			$("#contentPic span.content-left").hover(function() {
				$(this).css("cursor", "pointer");
				$(this).siblings(".content-sl").stop().animate({
					opacity:0.5	
				});	
			}, function() {
				$(this).siblings(".content-sl").stop().animate({
					opacity:0
				});	
			}).click(function() {
				if($scope.show==1){
					$scope.show=$("#contentPic ul li").length;
				}else{
					$scope.show--;
				}
				$scope.$apply();
			});
			
			$("#contentPic span.content-right").hover(function() {
				$(this).css("cursor", "pointer");
				$(this).siblings(".content-sr").stop().animate({
					opacity:0.5
				});	
			}, function() {
				$(this).siblings(".content-sr").stop().animate({
					opacity:0
				});	
			}).click(function() {
				if($scope.show==$("#contentPic ul li").length){
					$scope.show=1;
				}else{
					$scope.show ++;
				}
				$scope.$apply();	
			});
			
			setInterval(function(){
				$scope.$apply(function(){
					if($scope.show!=$("#contentPic ul li").length){
						$scope.show+=1;
					}else{
						$scope.show=1;
					}
				});
			},8000);

			$scope.$watch('show',function(newValue){
				if(newValue){
					//console.log($scope.show);
					makeCenter($scope.show-1);
				}
			});
			
			function makeCenter(index) {
				var li = $("#contentPic ul li").css("opacity", 0.3).eq(index).css("opacity", 1);
				var temp=$("#contentPic").width()-$("#contentPic ul li").width();
				$("#contentPic ul").animate({
					left: (temp/2-$("#contentPic ul li").width()*index)
				});
			}
		}
	}
});

