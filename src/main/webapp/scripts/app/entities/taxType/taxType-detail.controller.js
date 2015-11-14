'use strict';

angular.module('omsApp')
    .controller('TaxTypeDetailController', function ($scope, $rootScope, $stateParams, entity, TaxType) {
        $scope.taxType = entity;
        $scope.load = function (id) {
            TaxType.get({id: id}, function(result) {
                $scope.taxType = result;
            });
        };
        var unsubscribe = $rootScope.$on('omsApp:taxTypeUpdate', function(event, result) {
            $scope.taxType = result;
        });
        $scope.$on('$destroy', unsubscribe);

    });
