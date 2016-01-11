'use strict';


angular.module('app', [
    'ngAnimate',
    'ngCookies',
    'ngResource',
    'ngSanitize',
    'ngTouch',
    'ngStorage',
    'ui.router',
    'ui.bootstrap',
    'ui.utils',
    'ui.load',
    'ui.jq',
    'oc.lazyLoad',
    'pascalprecht.translate',
    'toaster',
    'ui.bootstrap.datetimepicker',
    'angularUtils.directives.dirPagination'
], function ($httpProvider) {
    // 设置post请求格式。
    // Use x-www-form-urlencoded Content-Type
    $httpProvider.defaults.headers.post['Content-Type'] = 'application/x-www-form-urlencoded;charset=utf-8';
 
    /**
     * The workhorse; converts an object to x-www-form-urlencoded serialization.
     * @param {Object} obj
     * @return {String}
     */
    var param = function (obj) {
        var query = '', name, value, fullSubName, subName, subValue, innerObj, i;

        for (name in obj) {
            value = obj[name];

            if (value instanceof Array) {
                for (i = 0; i < value.length; ++i) {
                    subValue = value[i];
                    fullSubName = name + '[' + i + ']';
                    innerObj = {};
                    innerObj[fullSubName] = subValue;
                    query += param(innerObj) + '&';
                }
            }
            else if (value instanceof Object) {
                for (subName in value) {
                    subValue = value[subName];
                    fullSubName = name + '[' + subName + ']';
                    innerObj = {};
                    innerObj[fullSubName] = subValue;
                    query += param(innerObj) + '&';
                }
            }
            else if (value !== undefined && value !== null)
                query += encodeURIComponent(name) + '=' + encodeURIComponent(value) + '&';
        }

        return query.length ? query.substr(0, query.length - 1) : query;
    };

    // Override $http service's default transformRequest
    $httpProvider.defaults.transformRequest = [function (data) {
        return angular.isObject(data) && String(data) !== '[object File]' ? param(data) : data;
    }];


    //添加一个拦截器。
    $httpProvider.interceptors.push('httpRequestInterceptor');
});


//所有的http请求都会通过这个拦截器。
angular.module('app').factory('httpRequestInterceptor',
    ['$rootScope', function ($rootScope) {
        return {
            request: function ($config) {
                //添加Oauth权限认证。
                if ($rootScope.oauth2 && $rootScope.oauth2.accessToken) {
                    $config.headers['Authorization'] = "bearer " + $rootScope.oauth2.accessToken;
                };
                $config.headers['X-Request-With']="";
                if ($rootScope.isJSON === "JSON") {
                    $config.headers['Content-Type'] = "application/json;charset=utf-8";
                    $rootScope.isJSON = "";
                };
                return $config;
            }
        };
    }]);
