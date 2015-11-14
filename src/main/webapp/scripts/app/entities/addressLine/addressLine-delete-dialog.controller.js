'use strict';

angular.module('omsApp')
	.controller('AddressLineDeleteController', function($scope, $modalInstance, entity, AddressLine) {

        $scope.addressLine = entity;
        $scope.clear = function() {
            $modalInstance.dismiss('cancel');
        };
        $scope.confirmDelete = function (id) {
            AddressLine.delete({id: id},
                function () {
                    $modalInstance.close(true);
                });
        };

    });