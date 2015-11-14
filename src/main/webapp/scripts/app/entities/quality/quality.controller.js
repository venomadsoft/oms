'use strict';

angular.module('omsApp')
    .controller('QualityController', function ($scope, $state, $modal, Quality) {
      
        $scope.qualitys = [];
        $scope.loadAll = function() {
            Quality.query(function(result) {
               $scope.qualitys = result;
            });
        };
        $scope.loadAll();


        $scope.refresh = function () {
            $scope.loadAll();
            $scope.clear();
        };

        $scope.clear = function () {
            $scope.quality = {
                label: null,
                id: null
            };
        };
    });
