'use strict';

angular.module('omsApp')
    .controller('MillController', function ($scope, $state, $modal, Mill) {
      
        $scope.mills = [];
        $scope.loadAll = function() {
            Mill.query(function(result) {
               $scope.mills = result;
            });
        };
        $scope.loadAll();


        $scope.refresh = function () {
            $scope.loadAll();
            $scope.clear();
        };

        $scope.clear = function () {
            $scope.mill = {
                code: null,
                name: null,
                id: null
            };
        };
    });
