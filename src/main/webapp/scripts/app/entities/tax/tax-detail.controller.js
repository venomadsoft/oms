'use strict';

angular.module('omsApp')
    .controller('TaxDetailController', function ($scope, $rootScope, $stateParams, entity, Tax, TaxType, PriceList) {
        $scope.tax = entity;
        $scope.load = function (id) {
            Tax.get({id: id}, function(result) {
                $scope.tax = result;
            });
        };
        var unsubscribe = $rootScope.$on('omsApp:taxUpdate', function(event, result) {
            $scope.tax = result;
        });
        $scope.$on('$destroy', unsubscribe);

    });
