'use strict';

angular.module('omsApp')
    .controller('PriceListDetailController', function ($scope, $rootScope, $stateParams, entity, PriceList, Price, Tax, CustomerGroup, DerivedGsmShade) {
        $scope.priceList = entity;
        $scope.load = function (id) {
            PriceList.get({id: id}, function(result) {
                $scope.priceList = result;
            });
        };
        var unsubscribe = $rootScope.$on('omsApp:priceListUpdate', function(event, result) {
            $scope.priceList = result;
        });
        $scope.$on('$destroy', unsubscribe);

    });
