'use strict';

/* Controllers */

angular.module('app')
    .controller('AppCtrl', ['$rootScope','$scope', '$http', '$translate', '$localStorage', '$window','$state','toaster',
        function ($rootScope,$scope, $http, $translate, $localStorage, $window,$state,toaster) {
            // add 'ie' classes to html
            var isIE = !!navigator.userAgent.match(/MSIE/i);
            isIE && angular.element($window.document.body).addClass('ie');
            isSmartDevice($window) && angular.element($window.document.body).addClass('smart');


            // config
            $scope.app = {
                name: '自动化系实验预约系统',
                title: '自动化系实验预约系统',
                organization:"上海交通大学自动化系",
                version: '1.0',
                //host: "http://localhost:8025",
                host: "http://202.120.39.249:8025",
                // for chart colors
                color: {
                    primary: '#7266ba',
                    info: '#23b7e5',
                    success: '#27c24c',
                    warning: '#fad733',
                    danger: '#f05050',
                    light: '#e8eff0',
                    dark: '#3a3f51',
                    black: '#1c2b36'
                },

                /*
                 默认主题
                 navbarHeaderColor: 'bg-black',
                 navbarCollapseColor: 'bg-white-only',
                 asideColor: 'bg-black',
                 */
                /*
                 蓝色主题
                 navbarHeaderColor: 'bg-info dker',
                 navbarCollapseColor: 'bg-info dk',
                 asideColor: 'bg-black',
                 */
                settings: {
                    themeID: 1,
                    navbarHeaderColor: 'bg-info dker',
                    navbarCollapseColor: 'bg-info dk',
                    asideColor: 'bg-black',
                    headerFixed: true,
                    asideFixed: false,
                    asideFolded: false,
                    asideDock: false,
                    container: false
                }
            };

            // save settings to local storage
            if (angular.isDefined($localStorage.settings)) {
                $scope.app.settings = $localStorage.settings;
            } else {
                $localStorage.settings = $scope.app.settings;
            }
            $scope.$watch('app.settings', function () {
                if ($scope.app.settings.asideDock && $scope.app.settings.asideFixed) {
                    // aside dock and fixed must set the header fixed.
                    $scope.app.settings.headerFixed = true;
                }
                // save to local storage
                $localStorage.settings = $scope.app.settings;
            }, true);

            function isSmartDevice($window) {
                // Adapted from http://www.detectmobilebrowsers.com
                var ua = $window['navigator']['userAgent'] || $window['navigator']['vendor'] || $window['opera'];
                // Checks for iOs, Android, Blackberry, Opera Mini, and Windows mobile devices
                return (/iPhone|iPod|iPad|Silk|Android|BlackBerry|Opera Mini|IEMobile/).test(ua);
            }


            //注册一个点击转跳的函数
            $scope.go=function($res,$params){
                console.log($res);
                if($params){
                    $state.go($res,$params);
                }else{
                    $state.go($res);
                }


            };

            //toaster.
            $scope.pop = function(type,title,message){
                console.log("Ready to pop:[Title]"+title+" [Message]"+message);
                toaster.pop(type,title,message);
            };


            //通过各种方法，终于能够获取到oauth信息了。
            $rootScope.oauth2={};
            $rootScope.isJSON = "";

            var autolabDebug= false;

            if(autolabDebug){

                //UI开发阶段HardCode.
                $rootScope.oauth2.accessToken="332e589d-eedf-439d-986f-cf6d56f35497";
                // $rootScope.oauth2.accessToken="8b20b4f1-1c1a-43ba-b7ea-b4537698a2c2";
                $rootScope.oauth2.tokenType="bearer";
                $rootScope.oauth2.refreshToken="3ecd724e-a02d-40c4-8a5f-496e981e9f9d";
                $rootScope.oauth2.expiresIn="86399";
                $rootScope.oauth2.scope="read write";
                $rootScope.oauth2.id = "1140329122";
                $rootScope.oauth2.uid = "zhaoguoqi";
                $rootScope.oauth2.student = "no";
                $rootScope.oauth2.chinesename = "公粮";
                $rootScope.oauth2.dept = "自动化系";

            }else{
                $rootScope.oauth2.accessToken=document.getElementById("oauth2_access_token").innerHTML;
                $rootScope.oauth2.tokenType=document.getElementById("oauth2_token_type").innerHTML;
                $rootScope.oauth2.refreshToken=document.getElementById("oauth2_refresh_token").innerHTML;
                $rootScope.oauth2.expiresIn=document.getElementById("oauth2_expires_in").innerHTML;
                $rootScope.oauth2.scope=document.getElementById("oauth2_scope").innerHTML;
                $rootScope.oauth2.id=document.getElementById("oauth2_id").innerHTML;
                $rootScope.oauth2.uid=document.getElementById("oauth2_uid").innerHTML;
                $rootScope.oauth2.student=document.getElementById("oauth2_student").innerHTML;
                $rootScope.oauth2.chinesename=document.getElementById("oauth2_chinesename").innerHTML;
                $rootScope.oauth2.dept=document.getElementById("oauth2_dept").innerHTML;

            }


            console.log(JSON.stringify($rootScope.oauth2));

        }]);
