'use strict';

angular.module('omsApp')
    .controller('AddressDetailController', function ($scope, $rootScope, $stateParams, entity, Address, Addresses, AddressLine) {
        $scope.address = entity;
        $scope.load = function (id) {
            Address.get({id: id}, function(result) {
                $scope.address = result;
            });
        };
        var unsubscribe = $rootScope.$on('omsApp:addressUpdate', function(event, result) {
            $scope.address = result;
        });
        $scope.$on('$destroy', unsubscribe);

    });
