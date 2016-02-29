'use strict';

angular.module('omsApp')
	.controller('CustomerGroupDeleteController', function($scope, $uibModalInstance, entity, CustomerGroup) {

        $scope.customerGroup = entity;
        $scope.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
        $scope.confirmDelete = function (id) {
            CustomerGroup.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        };

    });
