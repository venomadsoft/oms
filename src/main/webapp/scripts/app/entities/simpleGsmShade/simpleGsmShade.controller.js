'use strict';

angular.module('omsApp')
    .controller('SimpleGsmShadeController', function ($scope, $state, SimpleGsmShade) {

        $scope.simpleGsmShades = [];
        $scope.loadAll = function() {
            SimpleGsmShade.query(function(result) {
               $scope.simpleGsmShades = result;
            });
        };
        $scope.loadAll();


        $scope.refresh = function () {
            $scope.loadAll();
            $scope.clear();
        };

        $scope.clear = function () {
            $scope.simpleGsmShade = {
                minGsm: null,
                maxGsm: null,
                shade: null,
                id: null
            };
        };
    });
