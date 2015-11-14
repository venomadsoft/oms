'use strict';

angular.module('omsApp')
    .controller('FormulaController', function ($scope, $state, $modal, Formula) {
      
        $scope.formulas = [];
        $scope.loadAll = function() {
            Formula.query(function(result) {
               $scope.formulas = result;
            });
        };
        $scope.loadAll();


        $scope.refresh = function () {
            $scope.loadAll();
            $scope.clear();
        };

        $scope.clear = function () {
            $scope.formula = {
                operator: null,
                operand: null,
                id: null
            };
        };
    });
