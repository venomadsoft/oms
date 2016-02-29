'use strict';

angular.module('omsApp')
	.controller('AddressesDeleteController', function($scope, $uibModalInstance, entity, Addresses) {

        $scope.addresses = entity;
        $scope.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
        $scope.confirmDelete = function (id) {
            Addresses.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        };

    });
