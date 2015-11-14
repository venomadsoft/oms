'use strict';

angular.module('omsApp')
	.controller('CustomerGroupDeleteController', function($scope, $modalInstance, entity, CustomerGroup) {

        $scope.customerGroup = entity;
        $scope.clear = function() {
            $modalInstance.dismiss('cancel');
        };
        $scope.confirmDelete = function (id) {
            CustomerGroup.delete({id: id},
                function () {
                    $modalInstance.close(true);
                });
        };

    });