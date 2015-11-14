'use strict';

angular.module('omsApp')
    .controller('LineController', function ($scope, $state, $modal, Line) {
      
        $scope.lines = [];
        $scope.loadAll = function() {
            Line.query(function(result) {
               $scope.lines = result;
            });
        };
        $scope.loadAll();


        $scope.refresh = function () {
            $scope.loadAll();
            $scope.clear();
        };

        $scope.clear = function () {
            $scope.line = {
                text: null,
                id: null
            };
        };
    });
