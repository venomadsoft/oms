'use strict';

angular.module('omsApp')
    .controller('TaxController', function ($scope, $state, Tax) {

        $scope.taxs = [];
        $scope.loadAll = function() {
            Tax.query(function(result) {
               $scope.taxs = result;
            });
        };
        $scope.loadAll();


        $scope.refresh = function () {
            $scope.loadAll();
            $scope.clear();
        };

        $scope.clear = function () {
            $scope.tax = {
                rate: null,
                id: null
            };
        };
    });
