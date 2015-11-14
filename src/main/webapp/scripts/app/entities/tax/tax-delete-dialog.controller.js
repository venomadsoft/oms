'use strict';

angular.module('omsApp')
	.controller('TaxDeleteController', function($scope, $modalInstance, entity, Tax) {

        $scope.tax = entity;
        $scope.clear = function() {
            $modalInstance.dismiss('cancel');
        };
        $scope.confirmDelete = function (id) {
            Tax.delete({id: id},
                function () {
                    $modalInstance.close(true);
                });
        };

    });