var app = angular.module('mainApp', ['ngRoute']);

app.config(['$routeProvider', function ($routeProvider) {
    $routeProvider
                   // .when('/',{templateUrl:'/views/user/allUser.html', controller:'allCtrl'})
        .when('/userInfo/add',{templateUrl:'/views/user/addUser.html', controller: 'addCtrl'})
        //        {templateUrl:'/views/user/addUser.html'}
        .when('/userInfo', {templateUrl:'/views/user/allUser.html', controller:'allCtrl'})
        .when('/userInfo/edit/:uid', {templateUrl: '/views/user/editUser.html', controller: 'editCtrl'})
//        {templateUrl:'/views/user/allUser.html'}

}]);

// app.controller('mainCtrl', function ($scope, $location) {
//     $scope.user = sessionStorage.getItem("user");
//     console.log("username" + user.username);
//
// });
app.controller('allCtrl',function ($scope, $http) {
    $scope.sessionuser = sessionStorage.getItem("user");
    var allUsers = function () {
        $http.get("/allUsers").then(function (req) {
            $scope.users = req.data.data;
        });
    };

    allUsers();

    $scope.delUser = function (uid) {
        $scope.formData={
            uid: uid
        };
        $http({
            url: '/delUser',
            method: 'POST',
            params: $scope.formData,
            headers: {'Content-Type': 'application/x-www-form-urlencoded;charset=UTF-8'}
        }).then(function (req) {
            console.log(req.data.status);
            if(req.data.status = 1)
            {
                allUsers();
            }else {
                document.alert(req.data.errMsg);
            }

        });
    };
});
app.controller('addCtrl', function ($scope, $http, $location) {
    $http.get('/allRoles').then(function (req) {
        $scope.roles=req.data.data;
        console.log("roles");
        console.log(req.data.data);
    });

    $scope.addUser = function () {
        $scope.formData ={
            username: $scope.username,
            password: $scope.password,
            age: $scope.age,
            role: $scope.role
        }
        console.log("***role***" + $scope.role);
        $http({
            url     :  '/addUser',
            method  :  'POST',
            params  :  $scope.formData,
            headers :  {'Content-Type':'application/x-www-form-urlencoded;charset=UTF-8'}
        }).then(function (req) {
            console.log("req.data.status" + req.data.status);
            if(req.data.status == 1){
                alert("添加成功")
            }else{
                alert(req.data.errMsg);
            }
        });
    }

})

app.controller('editCtrl', function ($scope, $http,$routeParams) {
//            console.log("***uid***" + $routeParams.uid);
    $scope.formData={
        uid: $routeParams.uid
    };
    $http({
        url     :  '/getRealUserById',
        method  :  'POST',
        params  :  $scope.formData,
        headers :  {'Content-Type':'application/x-www-form-urlencoded;charset=UTF-8'}
    }).then(function (req) {
        console.log("用户信息" );
        console.log(req.data.data.user);
        var user = req.data.data.user;
        var role = req.data.data.role;
        var roles = req.data.data.roles;
        $scope.username = user.username;
        $scope.password = user.password;
        $scope.age = user.age;
        $scope.user = user;
        $scope.role = role;
        $scope.roles = roles;

    });

    $scope.editUser = function () {
        $scope.formData ={
            username: $scope.username,
            password: $scope.password,
            age: $scope.age,
            role: $scope.role.name,
            id : $scope.user.id
        }
        console.log("***role***" + $scope.role);
        $http({
            url     :  '/editUser',
            method  :  'POST',
            params  :  $scope.formData,
            headers :  {'Content-Type':'application/x-www-form-urlencoded;charset=UTF-8'}
        }).then(function (req) {
            console.log("req.data.status"+req.data.status);
            if(req.data.status == 1){
                alert("修改成功");
                console.log(req.data.errMsg);
            }else{
                alert(req.data.errMsg);
            }
        });
    }

})