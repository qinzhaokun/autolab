'use strict';

/**
 * Config for the router
 */
angular.module('app')
  .run(
    [          '$rootScope', '$state', '$stateParams',
      function ($rootScope,   $state,   $stateParams) {
          $rootScope.$state = $state;
          $rootScope.$stateParams = $stateParams;        
      }
    ]
  )
  .config(
    [          '$stateProvider', '$urlRouterProvider', 'JQ_CONFIG',
      function ($stateProvider,  $urlRouterProvider, JQ_CONFIG) {
          $urlRouterProvider
              .otherwise('/app/blank');
          $stateProvider
              .state('app', {
                  abstract: true,
                  url: '/app',
                  templateUrl: '/static/tpl/app.html'
              })
              .state('app.blank', {
                  url: '/blank',
                  templateUrl: '/static/tpl/blank.html'
              })

                            //teacher route
              .state('app.experiment', {
                  abstract: true,
                  url: '/experiment',
                  template: '<div ui-view class="fade-in-up"></div>'
              })
              .state('app.experiment.index', {
                  url: '/index',
                  templateUrl: '/static/view/experiment/index.html'
              })
              .state('app.experiment.booking', {
                  url: '/booking/{itemId}',
                  templateUrl: '/static/view/experiment/booking.html'
              })
              // .state('app.experiment.create', {
              //     url: '/create/{courseId}',
              //     templateUrl: '/static/view/experiment/create.html'
              // })

              .state('app.experiment.create', {
                  url: '/create/{courseId}',
                  templateUrl: '/static/view/experiment/create.html',
                  controller: 'XeditableCtrl',
                  resolve: {
                      deps: ['$ocLazyLoad',
                        function( $ocLazyLoad ){
                          return $ocLazyLoad.load('xeditable').then(
                              function(){
                                  return $ocLazyLoad.load('/static/js/controllers/xeditable.js');
                              }
                          );
                      }]
                  }
              })

              .state('app.booking', {
                  abstract: true,
                  url: '/booking',
                  template: '<div ui-view class="fade-in-up"></div>'
              })
              .state('app.booking.index', {
                  url: '/index',
                  templateUrl: '/static/view/booking/index.html'
              })
              .state('app.booking.booking', {
                  url: '/booking/{itemId}',
                  templateUrl: '/static/view//booking/booking.html'
              })
              .state('app.booking.detail', {
                  url: '/detail/{batchId}',
                  templateUrl: '/static/view/booking/detail.html',
                  resolve: {
                      deps: ['$ocLazyLoad',
                        function( $ocLazyLoad){
                          return $ocLazyLoad.load('angularFileUpload').then(
                              function(){
                                 return $ocLazyLoad.load('/static/js/controllers/bookingDetail.js');
                              }
                          );
                      }]
                  }
              })

              .state('app.grade', {
                  abstract: true,
                  url: '/grade',
                  template: '<div ui-view class="fade-in-up"></div>'
              })
              .state('app.grade.index', {
                  url: '/index',
                  templateUrl: '/static/view/grade/index.html'
              })
              .state('app.grade.booking', {
                  url: '/booking/{itemId}',
                  templateUrl: '/static/view/grade/booking.html'
              })
              .state('app.grade.detail', {
                  url: '/detail/{batchId}',
                  templateUrl: '/static/view/grade/detail.html'
              })
              .state('app.grade.student', {
                  url: '/student/{courseId}/{teacherId}/{studentId}',
                  templateUrl: '/static/view/grade/student.html'
              })

              //student route
              .state('app.experiment_booking', {
                  abstract: true,
                  url: '/experiment_booking',
                  template: '<div ui-view class="fade-in-up"></div>'
              })
              .state('app.experiment_booking.index', {
                  url: '/index',
                  templateUrl: '/static/view/experiment_booking/index.html'
              })
              .state('app.experiment_booking.booking', {
                  url: '/booking/{itemId}',
                  templateUrl: '/static/view/experiment_booking/booking.html'
              })

              .state('app.my_booking', {
                  abstract: true,
                  url: '/my_booking',
                  template: '<div ui-view class="fade-in-up"></div>'
              })
              .state('app.my_booking.index', {
                  url: '/index',
                  templateUrl: '/static/view/my_booking/index.html'
              })

              .state('app.my_grade', {
                  abstract: true,
                  url: '/my_grade',
                  template: '<div ui-view class="fade-in-up"></div>'
              })
              .state('app.my_grade.index', {
                  url: '/index',
                  templateUrl: '/static/view/my_grade/index.html'
              })
              .state('app.my_grade.detail', {
                  url: '/detail/{courseId}',
                  templateUrl: '/static/view/my_grade/detail.html'
              })

              //个人信息
              .state('app.my_profile', {
                  abstract: true,
                  url: '/my_profile',
                  template: '<div ui-view class="fade-in-up"></div>'
              })
              .state('app.my_profile.index', {
                  url: '/index',
                  templateUrl: '/static/view/my_profile/index.html'
              })

              // //teacher route
              // .state('app.experiment', {
              //     abstract: true,
              //     url: '/experiment',
              //     template: '<div ui-view class="fade-in-up"></div>'
              // })
              // .state('app.experiment.index', {
              //     url: '/index',
              //     templateUrl: '/static/page/experiment/index.html'
              // })
              // .state('app.experiment.booking', {
              //     url: '/booking/{itemId}',
              //     templateUrl: '/static/page/experiment/booking.html'
              // })
              // .state('app.experiment.create', {
              //     url: '/create/{courseId}',
              //     templateUrl: '/static/page/experiment/create.html'
              // })

              // .state('app.booking', {
              //     abstract: true,
              //     url: '/booking',
              //     template: '<div ui-view class="fade-in-up"></div>'
              // })
              // .state('app.booking.index', {
              //     url: '/index',
              //     templateUrl: '/static/page/booking/index.html'
              // })
              // .state('app.booking.booking', {
              //     url: '/booking/{itemId}',
              //     templateUrl: '/static/page/booking/booking.html'
              // })
              // .state('app.booking.detail', {
              //     url: '/detail/{batchId}',
              //     templateUrl: '/static/page/booking/detail.html'
              // })

              // .state('app.grade', {
              //     abstract: true,
              //     url: '/grade',
              //     template: '<div ui-view class="fade-in-up"></div>'
              // })
              // .state('app.grade.index', {
              //     url: '/index',
              //     templateUrl: '/static/page/grade/index.html'
              // })
              // .state('app.grade.booking', {
              //     url: '/booking/{itemId}',
              //     templateUrl: '/static/page/grade/booking.html'
              // })
              // .state('app.grade.detail', {
              //     url: '/detail/{batchId}',
              //     templateUrl: '/static/page/grade/detail.html'
              // })

              // //student route
              // .state('app.experiment_booking', {
              //     abstract: true,
              //     url: '/experiment_booking',
              //     template: '<div ui-view class="fade-in-up"></div>'
              // })
              // .state('app.experiment_booking.index', {
              //     url: '/index',
              //     templateUrl: '/static/page/experiment_booking/index.html'
              // })
              // .state('app.experiment_booking.booking', {
              //     url: '/booking/{itemId}',
              //     templateUrl: '/static/page/experiment_booking/booking.html'
              // })

              // .state('app.my_booking', {
              //     abstract: true,
              //     url: '/my_booking',
              //     template: '<div ui-view class="fade-in-up"></div>'
              // })
              // .state('app.my_booking.index', {
              //     url: '/index',
              //     templateUrl: '/static/page/my_booking/index.html'
              // })

              // .state('app.my_grade', {
              //     abstract: true,
              //     url: '/my_grade',
              //     template: '<div ui-view class="fade-in-up"></div>'
              // })
              // .state('app.my_grade.index', {
              //     url: '/index',
              //     templateUrl: '/static/page/my_grade/index.html'
              // })

              // //个人信息
              // .state('app.my_profile', {
              //     abstract: true,
              //     url: '/my_profile',
              //     template: '<div ui-view class="fade-in-up"></div>'
              // })
              // .state('app.my_profile.index', {
              //     url: '/index',
              //     templateUrl: '/static/page/my_profile/index.html'
              // })


              .state('app.ui', {
                  url: '/ui',
                  template: '<div ui-view class="fade-in-up"></div>'
              })
              .state('app.ui.toaster', {
                  url: '/toaster',
                  templateUrl: '/static/tpl/ui_toaster.html',
                  resolve: {
                      deps: ['$ocLazyLoad',
                          function( $ocLazyLoad){
                              return $ocLazyLoad.load('toaster').then(
                                  function(){
                                      return $ocLazyLoad.load('/static/js/controllers/toaster.js');
                                  }
                              );
                          }]
                  }
              })

              .state('access', {
                  abstract: true,
                  url: '/access',
                  templateUrl: '/static/tpl/access.html'
              })
              .state('access.404', {
                  url: '/404',
                  templateUrl: '/static/tpl/404.html'
              })

      }
    ]
  );
