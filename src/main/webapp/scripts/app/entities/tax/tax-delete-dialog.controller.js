'use strict';

angular.module('omsApp')
	.controller('TaxDeleteController', function($scope, $uibModalInstance, entity, Tax) {

        $scope.tax = entity;
        $scope.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
        $scope.confirmDelete = function (id) {
            Tax.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        };

    });
