'use strict';

angular.module('omsApp')
    .controller('QualityDetailController', function ($scope, $rootScope, $stateParams, entity, Quality, Mill, Price) {
        $scope.quality = entity;
        $scope.millId = $stateParams['millId'];
        $scope.millCode = $stateParams['millCode'];

        $scope.load = function (id, millId, millCode) {
            Quality.get({id: id}, function(result) {
                $scope.quality = result;
            });
        };
        var unsubscribe = $rootScope.$on('omsApp:qualityUpdate', function(event, result) {
            $scope.quality = result;
        });
        $scope.$on('$destroy', unsubscribe);

    });
