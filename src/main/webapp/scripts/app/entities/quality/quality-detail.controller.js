'use strict';

angular.module('omsApp')
    .controller('QualityDetailController', function ($scope, $rootScope, $stateParams, entity, Quality, Mill, Price) {
        $scope.quality = entity;
        $scope.load = function (id) {
            Quality.get({id: id}, function(result) {
                $scope.quality = result;
            });
        };
        var unsubscribe = $rootScope.$on('omsApp:qualityUpdate', function(event, result) {
            $scope.quality = result;
        });
        $scope.$on('$destroy', unsubscribe);

    });
