'use strict';

var baseUrl ="assets/scripts";
require.config({
    baseUrl: baseUrl,
    paths:{
        "app": "app",
    }

});
require([
    "app"],function () {
    angular.bootstrap(document, ["mainApp"]);
});



