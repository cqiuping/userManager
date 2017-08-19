var loginApp = angular.module('loginApp', []);


loginApp.controller('loginCtrl', function ($scope, $http,$location) {
    $scope.user = {};
    $scope.doLogin = function () {
        console.log($scope.user.username);

        // $http.post("login", $.param($scope.user),
        //     {headers:{'Content-Type':'application/x-www-form-urlencoded;charset=UTF-8'}}).then(function(data){
        //         console.log(data);
        //     if(data.status == 1){
        //         location.href = "./";
        //     } else {
        //        console.info("登陆失败")
        //     }
        // }).catch(console.error);
        $scope.formData = {
            username:$scope.user.username,
            password:$scope.user.password
        };
        console.info($scope.formData);
       var promise =  $http({
            method  : 'POST',
            url     : '/login',
            params    : $scope.formData,
            headers : { 'Content-Type': 'application/x-www-form-urlencoded' }
        });
       promise.success(function (req) {
           console.log(req.status);
           console.log(req.errMsg);
           if(req.status == 1){
               location.href="/views/main.html";
           }
           else{

                location.href="/login";
                alert(req.errMsg);
               // toastr.error(req.errMsg);
           }


       });
       // promise.error(function (req) {
       //     console.log(req.errMsg);
       //     // location.href="./";
       //     // console.log("err88888" + req);
       // })

    };
})


