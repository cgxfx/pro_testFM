function loginCtrl($rootScope, $scope, $http, $translate, $state,
	LOGIN_AS_USER, REGISTER_AS_USER, VERIFY_IDENTITY, DO_REGISTER_AS_USER) {

	$scope.registerOrLogin = "LOGIN";

	$scope.doRegister = -1;

	$scope.currentCount = 0;

	$scope.timeCount = 120;

	$scope.loginInfo = [{userEmail:'', userPwd:''}];

	$scope.registerInfo = [{userEmail:'', userPwd:'',userPwd2:'',nickName:'', identity:''}];

	$scope.switchTab = function(str) {
		if (str == 'loginTab') {
			$scope.registerOrLogin = "LOGIN";
			loginTab.style.borderBottom = "3px solid red";
	        registerTab.style.borderBottom = "";
		} else if (str == 'registerTab') {
			$scope.registerOrLogin = "REGISTER";
			registerTab.style.borderBottom = "3px solid red";
	        loginTab.style.borderBottom = "";
		}
	}


	$scope.loginAsUser = function() {
		if ($scope.loginInfo.userEmail == null || $scope.loginInfo.userPwd == null)
			return;
		$http.post(LOGIN_AS_USER,{'userEmail':$scope.loginInfo.userEmail, 'userPwd':$scope.loginInfo.userPwd})
			.success(function(data){
				if (data.code == 200) {
					$rootScope.Authenticated = 1;
					$state.go('home');
				} else {
					alert(data.msg);
				}
			}).error(function(data){
				alert("ERROR loginAsUser");
			});
	}

	$scope.registerAsUser = function() {
		if ($scope.registerInfo.userEmail == null)
			return;
		$http.post(REGISTER_AS_USER,{'userEmail':$scope.registerInfo.userEmail})
			.success(function(data){
				if (data.code == 200) {
					console.log("SUCCESS");
					$scope.currentCount = $scope.timeCount;
					$("#identityCode").attr("disabled", "true");
					$("#identityCode").val($scope.currentCount + "(s)");
					var InterValObj1 = window.setInterval(function(){
						$scope.currentCount--;
						$("#identityCode").val($scope.currentCount + "(s)");
						if ($scope.currentCount == 0) {
							console.log("0000000000000");
							$("#identityCode").removeAttr("disabled");
							$("#identityCode").val($translate.instant('VERIFICATION_ID'));
							window.clearInterval(InterValObj1);
						}
					}, 1000);
				} else {
					alert(data.msg);
				}
			}).error(function(data){
				alert("ERROR registerAsUser");
			});
	}

	$scope.verifyIdentity4Register = function() {
		if ($scope.registerInfo.userEmail == null || $scope.registerInfo.identity == null)
			return;
		$http.post(VERIFY_IDENTITY,{'userEmail':$scope.registerInfo.userEmail,'identity':$scope.registerInfo.identity, 'is4ReLogin':false})
			.success(function(data){
				if (data.code == 200) {
					$scope.doRegister = 1;
				} else {
					alert(data.msg);
				}
			}).error(function(data){
				alert("ERROR verifyIdentity4Register");
			});
	}

	$scope.verifyIdentity4ReLogin = function() {
		if ($scope.registerInfo.userEmail == null || $scope.registerInfo.identity == null)
			return;
		$http.post(VERIFY_IDENTITY,{'userEmail':$scope.registerInfo.userEmail,'identity':$scope.registerInfo.identity, 'is4ReLogin':true})
			.success(function(data){
				if (data.code == 200) {

				} else {
					alert(data.msg);
				}
			}).error(function(data){
				alert("ERROR verifyIdentity4ReLogin");
			});
	}

	$scope.doRegisterAsUser = function() {
		if ($scope.registerInfo.userEmail == null || $scope.registerInfo.userPwd == null || $scope.registerInfo.userPwd2 == null)
			return;
		if ($scope.registerInfo.userPwd != $scope.registerInfo.userPwd2) {
			alert($translate.instant('ERROR_DOUBLE_USERPWD'));
			return;
		}
		$http.post(DO_REGISTER_AS_USER, {'userEmail':$scope.registerInfo.userEmail,'userPwd':$scope.registerInfo.userPwd,'nickName':$scope.registerInfo.nickName})
			.success(function(data){
				if (data.code == 200) {
					$rootScope.Authenticated = 1;
					$state.go('home');
				} else {
					alert(data.msg);
				}
			}).error(function(data){
				alert("ERROR doRegisterAsUser");
			});
	}

}