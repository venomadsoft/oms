'use strict';

angular.module('omsApp')
    .controller('AddressesDetailController', function ($scope, $rootScope, $stateParams, entity, Addresses, Address) {
        $scope.addresses = entity;
        $scope.load = function (id) {
            Addresses.get({id: id}, function(result) {
                $scope.addresses = result;
            });
        };
        var unsubscribe = $rootScope.$on('omsApp:addressesUpdate', function(event, result) {
            $scope.addresses = result;
        });
        $scope.$on('$destroy', unsubscribe);

    });
