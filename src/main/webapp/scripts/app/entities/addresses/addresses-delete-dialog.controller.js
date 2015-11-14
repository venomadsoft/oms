'use strict';

angular.module('omsApp')
	.controller('AddressesDeleteController', function($scope, $modalInstance, entity, Addresses) {

        $scope.addresses = entity;
        $scope.clear = function() {
            $modalInstance.dismiss('cancel');
        };
        $scope.confirmDelete = function (id) {
            Addresses.delete({id: id},
                function () {
                    $modalInstance.close(true);
                });
        };

    });