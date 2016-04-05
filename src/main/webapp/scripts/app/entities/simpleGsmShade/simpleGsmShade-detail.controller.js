'use strict';

angular.module('omsApp')
    .controller('SimpleGsmShadeDetailController', function ($scope, $rootScope, $stateParams, entity, SimpleGsmShade, Mill, Price) {
        $scope.simpleGsmShade = entity;
        $scope.millId = $stateParams['millId'];
        $scope.millCode = $stateParams['millCode'];
        
        $scope.load = function (id, millId, millCode) {
            SimpleGsmShade.get({id: id}, function(result) {
                $scope.simpleGsmShade = result;
            });
        };
        var unsubscribe = $rootScope.$on('omsApp:simpleGsmShadeUpdate', function(event, result) {
            $scope.simpleGsmShade = result;
        });
        $scope.$on('$destroy', unsubscribe);

    });
	