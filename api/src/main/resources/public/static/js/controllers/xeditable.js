app.controller('XeditableCtrl', ['$scope', '$filter', '$http', 'editableOptions', 'editableThemes', '$stateParams','$state','$rootScope',
  function($scope, $filter, $http, editableOptions, editableThemes,$stateParams,$state,$rootScope){
    editableThemes.bs3.inputClass = 'input-sm';
    editableThemes.bs3.buttonsClass = 'btn-sm';
    editableOptions.theme = 'bs3';

    $scope.times = [];

    $scope.savetime = function(data) {
      console.log(JSON.stringify($scope.times));
    };

    $scope.removetime = function(index) {
      $scope.times.splice(index, 1);
      console.log(JSON.stringify($scope.times));

    };

    $scope.addtime = function() {
      $scope.inserted = {
        startTime: '',
        endTime: ''
      };
      $scope.times.push($scope.inserted);
      console.log(JSON.stringify($scope.times));

    };

           $scope.createExperiment = function () {
           var url = $scope.app.host + '/item/create2';
           var params = {};
           params["courseId"] = $stateParams.courseId;
           params["name"] = $scope.item.name;
           params["place"] = $scope.item.place;
           params["openTime"] = $scope.item.openTime;
           params["allowNumber"] = $scope.item.allowNumber;
           params["times"] = $scope.times;

           console.log(url + "?" + JSON.stringify(params));


           $rootScope.isJSON = "JSON";
           $http.post(url, JSON.stringify(params))
                   .success(function (response) {
                       console.log(response);
                       $scope.pop("success","","创建实验项目成功");

                       $state.go("app.experiment.index");

                   }).error(function (response) {
                       $scope.pop("error", "错误", response.error.message);
                   });
       };

}]);
