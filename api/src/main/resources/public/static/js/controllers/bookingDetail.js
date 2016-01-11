   app.controller('BookingDetailController', ['$scope', '$http','$stateParams', 'FileUploader', function ($scope, $http,$stateParams,FileUploader) {
        $scope.pagination = {
            page: 1,
            pageSize: 3,
            totalItems: 0
        };

        $scope.pageChanged = function (newPage) {
            console.log(newPage);
            $scope.pagination.page = newPage;
            $scope.getDetail();
        };

        $scope.getDetail = function () {
            var url = $scope.app.host + '/batch/books/'+$stateParams.batchId;
            var params = {};
            params["size"] = $scope.pagination.pageSize;
            params["page"] = $scope.pagination.page-1;

           console.log(url + "?" + JSON.stringify(params));
            $http.post(url, params)
                .success(function (response){
                    $scope.books = response.pager.books;
                    if ($scope.books.length !=0) {
                        $scope.bookDetail = $scope.books[0];
                    };
                    $scope.pagination.totalItems = response.pager.totalItems;
                }).error(function (response){
                    $scope.pop("error","错误",response.error.message);
                });
        };

        $scope.getDetail();

       // $scope.upload = function () {
       //      var url = $scope.app.host + '/attendance/upload';
       //      var params = {};
       //      params["file"] = $scope.filestyle;
       //      params["batchId"] = 1;
       //     console.log(url + "?" + JSON.stringify(params));
       //     $http.post(url, params)
       //             .success(function (response) {
       //                 console.log(response);
       //                  $scope.courseteacherstudents = response.courseteacherstudents;
       //             }).error(function (response) {
       //                 $scope.pop("error", "错误", response.error.message);
       //             });

       // }

    var uploader = $scope.uploader = new FileUploader({
        formData: [{batchId: 1}],
        url: $scope.app.host + '/attendance/upload'
    });

    // FILTERS

    uploader.filters.push({
        name: 'customFilter',
        fn: function(item /*{File|FileLikeObject}*/, options) {
            return this.queue.length < 10;
        }
    });

    // CALLBACKS

    uploader.onWhenAddingFileFailed = function(item /*{File|FileLikeObject}*/, filter, options) {
        console.info('onWhenAddingFileFailed', item, filter, options);
    };
    uploader.onAfterAddingFile = function(fileItem) {
        console.info('onAfterAddingFile', fileItem);
    };
    uploader.onAfterAddingAll = function(addedFileItems) {
        console.info('onAfterAddingAll', addedFileItems);
    };
    uploader.onBeforeUploadItem = function(item) {
        console.info('onBeforeUploadItem', item);
    };
    uploader.onProgressItem = function(fileItem, progress) {
        console.info('onProgressItem', fileItem, progress);
    };
    uploader.onProgressAll = function(progress) {
        console.info('onProgressAll', progress);
    };
    uploader.onSuccessItem = function(fileItem, response, status, headers) {
        console.info('onSuccessItem', fileItem, response, status, headers);
    };
    uploader.onErrorItem = function(fileItem, response, status, headers) {
        console.info('onErrorItem', fileItem, response, status, headers);
    };
    uploader.onCancelItem = function(fileItem, response, status, headers) {
        console.info('onCancelItem', fileItem, response, status, headers);
    };
    uploader.onCompleteItem = function(fileItem, response, status, headers) {
        console.info('onCompleteItem', fileItem, response, status, headers);
    };
    uploader.onCompleteAll = function() {
        console.info('onCompleteAll');
    };

    console.info('uploader', uploader);
    }]);