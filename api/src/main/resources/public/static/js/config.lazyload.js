// lazyload config

angular.module('app')
    /**
   * jQuery plugin config use ui-jq directive , config the js and css files that required
   * key: function name of the jQuery plugin
   * value: array of the css js file located
   */
  .constant('JQ_CONFIG', {
      easyPieChart:   [   '/static/fork/angulr-2.0.1/bower_components/jquery.easy-pie-chart/dist/jquery.easypiechart.fill.js'],
      sparkline:      [   '/static/fork/angulr-2.0.1/bower_components/jquery.sparkline/dist/jquery.sparkline.retina.js'],
      plot:           [   '/static/fork/angulr-2.0.1/bower_components/flot/jquery.flot.js',
                          '/static/fork/angulr-2.0.1/bower_components/flot/jquery.flot.pie.js', 
                          '/static/fork/angulr-2.0.1/bower_components/flot/jquery.flot.resize.js',
                          '/static/fork/angulr-2.0.1/bower_components/flot.tooltip/js/jquery.flot.tooltip.js',
                          '/static/fork/angulr-2.0.1/bower_components/flot.orderbars/js/jquery.flot.orderBars.js',
                          '/static/fork/angulr-2.0.1/bower_components/flot-spline/js/jquery.flot.spline.js'],
      moment:         [   '/static/fork/angulr-2.0.1/bower_components/moment/moment.js'],
      screenfull:     [   '/static/fork/angulr-2.0.1/bower_components/screenfull/dist/screenfull.min.js'],
      slimScroll:     [   '/static/fork/angulr-2.0.1/bower_components/slimscroll/jquery.slimscroll.min.js'],
      sortable:       [   '/static/fork/angulr-2.0.1/bower_components/html5sortable/jquery.sortable.js'],
      nestable:       [   '/static/fork/angulr-2.0.1/bower_components/nestable/jquery.nestable.js',
                          '/static/fork/angulr-2.0.1/bower_components/nestable/jquery.nestable.css'],
      filestyle:      [   '/static/fork/angulr-2.0.1/bower_components/bootstrap-filestyle/src/bootstrap-filestyle.js'],
      slider:         [   '/static/fork/angulr-2.0.1/bower_components/bootstrap-slider/bootstrap-slider.js',
                          '/static/fork/angulr-2.0.1/bower_components/bootstrap-slider/bootstrap-slider.css'],
      chosen:         [   '/static/fork/angulr-2.0.1/bower_components/chosen/chosen.jquery.min.js',
                          '/static/fork/angulr-2.0.1/bower_components/bootstrap-chosen/bootstrap-chosen.css'],
      TouchSpin:      [   '/static/fork/angulr-2.0.1/bower_components/bootstrap-touchspin/dist/jquery.bootstrap-touchspin.min.js',
                          '/static/fork/angulr-2.0.1/bower_components/bootstrap-touchspin/dist/jquery.bootstrap-touchspin.min.css'],
      wysiwyg:        [   '/static/fork/angulr-2.0.1/bower_components/bootstrap-wysiwyg/bootstrap-wysiwyg.js',
                          '/static/fork/angulr-2.0.1/bower_components/bootstrap-wysiwyg/external/jquery.hotkeys.js'],
      dataTable:      [   '/static/fork/angulr-2.0.1/bower_components/datatables/media/js/jquery.dataTables.min.js',
                          '/static/fork/angulr-2.0.1/bower_components/plugins/integration/bootstrap/3/dataTables.bootstrap.js',
                          '/static/fork/angulr-2.0.1/bower_components/plugins/integration/bootstrap/3/dataTables.bootstrap.css'],
      vectorMap:      [   '/static/fork/angulr-2.0.1/bower_components/bower-jvectormap/jquery-jvectormap-1.2.2.min.js', 
                          '/static/fork/angulr-2.0.1/bower_components/bower-jvectormap/jquery-jvectormap-world-mill-en.js',
                          '/static/fork/angulr-2.0.1/bower_components/bower-jvectormap/jquery-jvectormap-us-aea-en.js',
                          '/static/fork/angulr-2.0.1/bower_components/bower-jvectormap/jquery-jvectormap-1.2.2.css'],
      footable:       [   '/static/fork/angulr-2.0.1/bower_components/footable/dist/footable.all.min.js',
                          '/static/fork/angulr-2.0.1/bower_components/footable/css/footable.core.css'],
      fullcalendar:   [   '/static/fork/angulr-2.0.1/bower_components/moment/moment.js',
                          '/static/fork/angulr-2.0.1/bower_components/fullcalendar/dist/fullcalendar.min.js',
                          '/static/fork/angulr-2.0.1/bower_components/fullcalendar/dist/fullcalendar.css',
                          '/static/fork/angulr-2.0.1/bower_components/fullcalendar/dist/fullcalendar.theme.css'],
      daterangepicker:[   '/static/fork/angulr-2.0.1/bower_components/moment/moment.js',
                          '/static/fork/angulr-2.0.1/bower_components/bootstrap-daterangepicker/daterangepicker.js',
                          '/static/fork/angulr-2.0.1/bower_components/bootstrap-daterangepicker/daterangepicker-bs3.css'],
      tagsinput:      [   '/static/fork/angulr-2.0.1/bower_components/bootstrap-tagsinput/dist/bootstrap-tagsinput.js',
                          '/static/fork/angulr-2.0.1/bower_components/bootstrap-tagsinput/dist/bootstrap-tagsinput.css']
                      
    }
  )
  // oclazyload config
  .config(['$ocLazyLoadProvider', function($ocLazyLoadProvider) {
      // We configure ocLazyLoad to use the lib script.js as the async loader
      $ocLazyLoadProvider.config({
          debug:  true,
          events: true,
          modules: [
              {
                  name: 'ngGrid',
                  files: [
                      '/static/fork/angulr-2.0.1/bower_components/ng-grid/build/ng-grid.min.js',
                      '/static/fork/angulr-2.0.1/bower_components/ng-grid/ng-grid.min.css',
                      '/static/fork/angulr-2.0.1/bower_components/ng-grid/ng-grid.bootstrap.css'
                  ]
              },
              {
                  name: 'ui.grid',
                  files: [
                      '/static/fork/angulr-2.0.1/bower_components/angular-ui-grid/ui-grid.min.js',
                      '/static/fork/angulr-2.0.1/bower_components/angular-ui-grid/ui-grid.min.css',
                      '/static/fork/angulr-2.0.1/bower_components/angular-ui-grid/ui-grid.bootstrap.css'
                  ]
              },
              {
                  name: 'ui.select',
                  files: [
                      '/static/fork/angulr-2.0.1/bower_components/angular-ui-select/dist/select.min.js',
                      '/static/fork/angulr-2.0.1/bower_components/angular-ui-select/dist/select.min.css'
                  ]
              },
              {
                  name:'angularFileUpload',
                  files: [
                    '/static/fork/angulr-2.0.1/bower_components/angular-file-upload/angular-file-upload.min.js'
                  ]
              },
              {
                  name:'ui.calendar',
                  files: ['/static/fork/angulr-2.0.1/bower_components/angular-ui-calendar/src/calendar.js']
              },
              {
                  name: 'ngImgCrop',
                  files: [
                      '/static/fork/angulr-2.0.1/bower_components/ngImgCrop/compile/minified/ng-img-crop.js',
                      '/static/fork/angulr-2.0.1/bower_components/ngImgCrop/compile/minified/ng-img-crop.css'
                  ]
              },
              {
                  name: 'angularBootstrapNavTree',
                  files: [
                      '/static/fork/angulr-2.0.1/bower_components/angular-bootstrap-nav-tree/dist/abn_tree_directive.js',
                      '/static/fork/angulr-2.0.1/bower_components/angular-bootstrap-nav-tree/dist/abn_tree.css'
                  ]
              },
              {
                  name: 'toaster',
                  files: [
                      '/static/fork/angulr-2.0.1/bower_components/angularjs-toaster/toaster.js',
                      '/static/fork/angulr-2.0.1/bower_components/angularjs-toaster/toaster.css'
                  ]
              },
              {
                  name: 'textAngular',
                  files: [
                      '/static/fork/angulr-2.0.1/bower_components/textAngular/dist/textAngular-sanitize.min.js',
                      '/static/fork/angulr-2.0.1/bower_components/textAngular/dist/textAngular.min.js'
                  ]
              },
              {
                  name: 'vr.directives.slider',
                  files: [
                      '/static/fork/angulr-2.0.1/bower_components/venturocket-angular-slider/build/angular-slider.min.js',
                      '/static/fork/angulr-2.0.1/bower_components/venturocket-angular-slider/build/angular-slider.css'
                  ]
              },
              {
                  name: 'com.2fdevs.videogular',
                  files: [
                      '/static/fork/angulr-2.0.1/bower_components/videogular/videogular.min.js'
                  ]
              },
              {
                  name: 'com.2fdevs.videogular.plugins.controls',
                  files: [
                      '/static/fork/angulr-2.0.1/bower_components/videogular-controls/controls.min.js'
                  ]
              },
              {
                  name: 'com.2fdevs.videogular.plugins.buffering',
                  files: [
                      '/static/fork/angulr-2.0.1/bower_components/videogular-buffering/buffering.min.js'
                  ]
              },
              {
                  name: 'com.2fdevs.videogular.plugins.overlayplay',
                  files: [
                      '/static/fork/angulr-2.0.1/bower_components/videogular-overlay-play/overlay-play.min.js'
                  ]
              },
              {
                  name: 'com.2fdevs.videogular.plugins.poster',
                  files: [
                      '/static/fork/angulr-2.0.1/bower_components/videogular-poster/poster.min.js'
                  ]
              },
              {
                  name: 'com.2fdevs.videogular.plugins.imaads',
                  files: [
                      '/static/fork/angulr-2.0.1/bower_components/videogular-ima-ads/ima-ads.min.js'
                  ]
              },
              {
                  name: 'xeditable',
                  files: [
                      '/static/fork/angulr-2.0.1/bower_components/angular-xeditable/dist/js/xeditable.min.js',
                      '/static/fork/angulr-2.0.1/bower_components/angular-xeditable/dist/css/xeditable.css'
                  ]
              },
              {
                  name: 'smart-table',
                  files: [
                      '/static/fork/angulr-2.0.1/bower_components/angular-smart-table/dist/smart-table.min.js'
                  ]
              }
          ]
      });
  }])
;
