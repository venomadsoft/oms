'use strict';

angular.module('omsApp')
    .controller('CustomerGroupDetailController', function ($scope, $rootScope, $stateParams, entity, CustomerGroup, PriceList, Mill, Customer, NoteSet, Formulae) {
        $scope.customerGroup = entity;
        $scope.load = function (id) {
            CustomerGroup.get({id: id}, function(result) {
                $scope.customerGroup = result;
            });
        };
        var unsubscribe = $rootScope.$on('omsApp:customerGroupUpdate', function(event, result) {
            $scope.customerGroup = result;
        });
        $scope.$on('$destroy', unsubscribe);

    });
