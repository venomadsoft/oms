'use strict';

angular.module('omsApp')
    .controller('FormulaDetailController', function ($scope, $rootScope, $stateParams, entity, Formula, Formulae) {
        $scope.formula = entity;
        $scope.load = function (id) {
            Formula.get({id: id}, function(result) {
                $scope.formula = result;
            });
        };
        var unsubscribe = $rootScope.$on('omsApp:formulaUpdate', function(event, result) {
            $scope.formula = result;
        });
        $scope.$on('$destroy', unsubscribe);

    });
