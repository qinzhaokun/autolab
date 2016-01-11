'use strict';

// signup controller
app.controller('SignupFormController', ['$scope', '$http', '$state','$localStorage', function ($scope, $http, $state,$localStorage) {

    console.log("Enter into SignupFormController");
    //清除本地信息
    $scope.app.user=null;
    $localStorage.user = null;

    $scope.user = {};
    $scope.signup = function () {
        var url=$scope.app.host + '/user/register';
        if($scope.user.password!=$scope.user.repassword){
            $scope.pop("error","失败","两次密码输入不一致");
            return;
        }

        var params={
            username: $scope.user.username,
            password: $scope.user.password,
            name:$scope.user.name};
        console.log(url+"?"+JSON.stringify(params));
        $http.post(url, params)
            .success(function (response) {
                if(response.status=="OK"){

                    $scope.app.user=response.user;
                    //本地持久化
                    $localStorage.user = $scope.app.user;

                    $state.go('app.dashboard');
                }else{

                    console.log(response.message);
                    $scope.pop("error","失败",response.message);
                }


            }).error(function(){
                $scope.pop("error","失败","注册失败！");
            });
    };
}])
;