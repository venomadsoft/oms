'use strict';

angular.module('omsApp')
    .controller('FormulaeController', function ($scope, $state, Formulae) {

        $scope.formulaes = [];
        $scope.loadAll = function() {
            Formulae.query(function(result) {
               $scope.formulaes = result;
            });
        };
        $scope.loadAll();


        $scope.refresh = function () {
            $scope.loadAll();
            $scope.clear();
        };

        $scope.clear = function () {
            $scope.formulae = {
                id: null
            };
        };
    });
