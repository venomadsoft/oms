'use strict';

angular.module('omsApp')
    .controller('SimpleGsmShadeDetailController', function ($scope, $rootScope, $stateParams, entity, SimpleGsmShade, Mill, Price) {
        $scope.simpleGsmShade = entity;
        $scope.load = function (id) {
            SimpleGsmShade.get({id: id}, function(result) {
                $scope.simpleGsmShade = result;
            });
        };
        var unsubscribe = $rootScope.$on('omsApp:simpleGsmShadeUpdate', function(event, result) {
            $scope.simpleGsmShade = result;
        });
        $scope.$on('$destroy', unsubscribe);

    });
