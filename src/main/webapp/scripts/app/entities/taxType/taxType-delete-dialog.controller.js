'use strict';

angular.module('omsApp')
	.controller('TaxTypeDeleteController', function($scope, $modalInstance, entity, TaxType) {

        $scope.taxType = entity;
        $scope.clear = function() {
            $modalInstance.dismiss('cancel');
        };
        $scope.confirmDelete = function (id) {
            TaxType.delete({id: id},
                function () {
                    $modalInstance.close(true);
                });
        };

    });