'use strict';

angular.module('omsApp')
    .controller('AddressLineDetailController', function ($scope, $rootScope, $stateParams, entity, AddressLine, Address) {
        $scope.addressLine = entity;
        $scope.load = function (id) {
            AddressLine.get({id: id}, function(result) {
                $scope.addressLine = result;
            });
        };
        var unsubscribe = $rootScope.$on('omsApp:addressLineUpdate', function(event, result) {
            $scope.addressLine = result;
        });
        $scope.$on('$destroy', unsubscribe);

    });
