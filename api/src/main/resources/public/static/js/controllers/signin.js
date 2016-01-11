'use strict';

/* Controllers */
// signin controller
app.controller('SigninFormController', ['$scope', '$http', '$state','$localStorage', function ($scope, $http, $state,$localStorage) {

    console.log("Enter into SignForm Controller");
    //清除本地信息
    $scope.app.user=null;
    $localStorage.user = null;

    $scope.user = {};
    $scope.login = function () {
        var url=$scope.app.host + '/user/login';
        var params={username: $scope.user.username,
            password: $scope.user.password,
            auto_login:$scope.user.auto_login};
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
                $scope.pop("error","失败","登录失败！");
            });
    };
}])
;