'use strict';

angular.module('omsApp')
    .controller('AddressLineController', function ($scope, $state, AddressLine) {

        $scope.addressLines = [];
        $scope.loadAll = function() {
            AddressLine.query(function(result) {
               $scope.addressLines = result;
            });
        };
        $scope.loadAll();


        $scope.refresh = function () {
            $scope.loadAll();
            $scope.clear();
        };

        $scope.clear = function () {
            $scope.addressLine = {
                text: null,
                id: null
            };
        };
    });
