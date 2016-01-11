app.controller('ExperimentCreateController', ['$scope', '$timeout', function($scope, $timeout) {

      $scope.times = [];

      $scope.addRandomItem = function addRandomItem(time) {
        $scope.times.push(time);
      };

  $scope.displayedtimes = [].concat($scope.times);


       //   $scope.createExperiment = function () {
       //     var url = $scope.app.host + '/item/create2';
       //     var params = {};
       //     params["courseId"] = $stateParams.courseId;
       //     params["name"] = $scope.item.name;
       //     params["place"] = $scope.item.place;
       //     params["allowNumber"] = $scope.item.allowNumber;
       //     params["times"] = $scope.item.times;

       //     console.log(url + "?" + JSON.stringify(params));
       //     $http.post(url, params)
       //             .success(function (response) {
       //                 console.log(response);
       //                 $scope.pop("success","","创建实验项目成功");

       //                 $state.go("app.experiment.index");

       //             }).error(function (response) {
       //                 $scope.pop("error", "错误", response.error.message);
       //             });
       // };

  var firstnames = [];
  var lastnames = [];

  function generateRandomItem() {

      var firstname = firstnames[Math.floor(Math.random() * 3)];
      var lastname = lastnames[Math.floor(Math.random() * 3)];

      return {
          firstName: firstname,
          lastName: lastname,
      }
  }

  $scope.rowCollection = [];

  //copy the references (you could clone ie angular.copy but then have to go through a dirty checking for the matches)
  $scope.displayedCollection = [].concat($scope.rowCollection);

  //add to the real data holder
  $scope.addRandomItem = function addRandomItem() {
      $scope.rowCollection.push(generateRandomItem());
  };

  //remove to the real data holder
  $scope.removeItem = function(row) {
      var index = $scope.rowCollection.indexOf(row);
      if (index !== -1) {
          $scope.rowCollection.splice(index, 1);
      }
  }


}]);