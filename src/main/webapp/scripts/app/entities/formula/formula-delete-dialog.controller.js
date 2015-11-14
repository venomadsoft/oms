'use strict';

angular.module('omsApp')
	.controller('FormulaDeleteController', function($scope, $modalInstance, entity, Formula) {

        $scope.formula = entity;
        $scope.clear = function() {
            $modalInstance.dismiss('cancel');
        };
        $scope.confirmDelete = function (id) {
            Formula.delete({id: id},
                function () {
                    $modalInstance.close(true);
                });
        };

    });