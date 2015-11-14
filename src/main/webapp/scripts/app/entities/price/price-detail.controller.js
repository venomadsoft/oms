'use strict';

angular.module('omsApp')
    .controller('PriceDetailController', function ($scope, $rootScope, $stateParams, entity, Price, Quality, SimpleGsmShade, Mill, PriceList) {
        $scope.price = entity;
        $scope.load = function (id) {
            Price.get({id: id}, function(result) {
                $scope.price = result;
            });
        };
        var unsubscribe = $rootScope.$on('omsApp:priceUpdate', function(event, result) {
            $scope.price = result;
        });
        $scope.$on('$destroy', unsubscribe);

    });
