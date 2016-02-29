'use strict';

angular.module('omsApp')
    .controller('DerivedGsmShadeController', function ($scope, $state, DerivedGsmShade) {

        $scope.derivedGsmShades = [];
        $scope.loadAll = function() {
            DerivedGsmShade.query(function(result) {
               $scope.derivedGsmShades = result;
            });
        };
        $scope.loadAll();


        $scope.refresh = function () {
            $scope.loadAll();
            $scope.clear();
        };

        $scope.clear = function () {
            $scope.derivedGsmShade = {
                minGsm: null,
                maxGsm: null,
                shade: null,
                id: null
            };
        };
    });
