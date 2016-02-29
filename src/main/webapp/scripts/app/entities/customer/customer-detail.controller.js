'use strict';

angular.module('omsApp')
    .controller('CustomerDetailController', function ($scope, $rootScope, $stateParams, entity, Customer, Addresses, NoteSet, Formulae, CustomerGroup) {
        $scope.customer = entity;
        $scope.load = function (id) {
            Customer.get({id: id}, function(result) {
                $scope.customer = result;
            });
        };
        var unsubscribe = $rootScope.$on('omsApp:customerUpdate', function(event, result) {
            $scope.customer = result;
        });
        $scope.$on('$destroy', unsubscribe);

    });
