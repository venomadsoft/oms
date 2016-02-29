'use strict';

angular.module('omsApp')
	.controller('FormulaDeleteController', function($scope, $uibModalInstance, entity, Formula) {

        $scope.formula = entity;
        $scope.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
        $scope.confirmDelete = function (id) {
            Formula.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        };

    });
