'use strict';

angular.module('omsApp')
	.controller('TaxTypeDeleteController', function($scope, $uibModalInstance, entity, TaxType) {

        $scope.taxType = entity;
        $scope.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
        $scope.confirmDelete = function (id) {
            TaxType.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        };

    });
