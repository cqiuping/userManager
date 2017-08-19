'use strict';
define([], function () {
    var app = angular.module('mainApp', ['ngRoute']);

    function setRoute(url, ctrl, reqJs, label) {
        var routeDef = {};
        routeDef.templateUrl = "views" + url + ".html";
        routeDef.controller = ctrl;
        routeDef.resolve = {
            load: ['$q', '$rootScope',
                function ($q, $rootScope) {
                    var defer = $q.defer();
                    require(["assets/scripts" + reqJs],
                        function () {
                            defer.resolve();
                            $rootScope.$apply();
                        });
                    return defer.promise;
                }]
        };
        routeDef.label = label;
        return routeDef;
    }


    app.config(function ($routeProvider, $controllerProvider, $provide, $httpProvider) {
        $routeProvider
        /** 主页 */
            .when('/',setRoute('/main', 'mainCtrl', '/home.js','首页'))
                // {templateUrl:'views/main.html'})
            .when('/userInfo/add', setRoute('/user/addUser','allCtrl', '/user/addUser.js', '添加用户'))
                // ,{templateUrl:'/views/user/addUser.html', controller: 'allCtrl'})
            //        {templateUrl:'/views/user/addUser.html'}
            // .when('/userInfo', setRoute('/user/allUser', 'addCtrl', '/user/allUser.js', '所有用户信息'))
                // {templateUrl:'/views/user/allUser.html', controller:'addCtrl'})
            // .when('/userInfo/edit/:uid', {templateUrl: '/views/user/editUser.html', controller: 'editCtrl'})
//        {templateUrl:'/views/user/allUser.html'}
    });

});