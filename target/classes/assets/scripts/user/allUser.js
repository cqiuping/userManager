define(['mainApp'], function (app) {
    var injectParams = ['$scope','$http'];
    var allCtrl = function ($scope, $http) {
        $http.get("allUser").then(function (req) {
            console.log(req.data);
        });
        
    }
    allCtrl().$inject = injectParams;
    app.register.controller('allCtrl', allCtrl);
});