'use strict';

angular.module('omsApp')
    .controller('DerivedGsmShadeDetailController', function ($scope, $rootScope, $stateParams, entity, DerivedGsmShade, Formulae, SimpleGsmShade, Mill, PriceList) {
        $scope.derivedGsmShade = entity;
        $scope.load = function (id) {
            DerivedGsmShade.get({id: id}, function(result) {
                $scope.derivedGsmShade = result;
            });
        };
        var unsubscribe = $rootScope.$on('omsApp:derivedGsmShadeUpdate', function(event, result) {
            $scope.derivedGsmShade = result;
        });
        $scope.$on('$destroy', unsubscribe);

    });
