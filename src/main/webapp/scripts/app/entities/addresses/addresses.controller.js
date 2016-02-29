'use strict';

angular.module('omsApp')
    .controller('AddressesController', function ($scope, $state, Addresses) {

        $scope.addressess = [];
        $scope.loadAll = function() {
            Addresses.query(function(result) {
               $scope.addressess = result;
            });
        };
        $scope.loadAll();


        $scope.refresh = function () {
            $scope.loadAll();
            $scope.clear();
        };

        $scope.clear = function () {
            $scope.addresses = {
                id: null
            };
        };
    });
