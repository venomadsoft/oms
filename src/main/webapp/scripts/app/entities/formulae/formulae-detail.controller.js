'use strict';

angular.module('omsApp')
    .controller('FormulaeDetailController', function ($scope, $rootScope, $stateParams, entity, Formulae, Formula) {
        $scope.formulae = entity;
        $scope.load = function (id) {
            Formulae.get({id: id}, function(result) {
                $scope.formulae = result;
            });
        };
        var unsubscribe = $rootScope.$on('omsApp:formulaeUpdate', function(event, result) {
            $scope.formulae = result;
        });
        $scope.$on('$destroy', unsubscribe);

    });
