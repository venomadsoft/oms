'use strict';

angular.module('omsApp')
    .controller('TaxTypeController', function ($scope, $state, $modal, TaxType) {
      
        $scope.taxTypes = [];
        $scope.loadAll = function() {
            TaxType.query(function(result) {
               $scope.taxTypes = result;
            });
        };
        $scope.loadAll();


        $scope.refresh = function () {
            $scope.loadAll();
            $scope.clear();
        };

        $scope.clear = function () {
            $scope.taxType = {
                label: null,
                id: null
            };
        };
    });
