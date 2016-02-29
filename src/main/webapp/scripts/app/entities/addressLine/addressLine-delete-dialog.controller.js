'use strict';

angular.module('omsApp')
	.controller('AddressLineDeleteController', function($scope, $uibModalInstance, entity, AddressLine) {

        $scope.addressLine = entity;
        $scope.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
        $scope.confirmDelete = function (id) {
            AddressLine.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        };

    });
