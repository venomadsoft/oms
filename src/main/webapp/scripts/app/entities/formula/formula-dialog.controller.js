'use strict';

angular.module('omsApp').controller('FormulaDialogController',
    ['$scope', '$stateParams', '$uibModalInstance', 'entity', 'Formula', 'Formulae',
        function($scope, $stateParams, $uibModalInstance, entity, Formula, Formulae) {

        $scope.formula = entity;
        $scope.formulaes = Formulae.query();
        $scope.load = function(id) {
            Formula.get({id : id}, function(result) {
                $scope.formula = result;
            });
        };

        var onSaveSuccess = function (result) {
            $scope.$emit('omsApp:formulaUpdate', result);
            $uibModalInstance.close(result);
            $scope.isSaving = false;
        };

        var onSaveError = function (result) {
            $scope.isSaving = false;
        };

        $scope.save = function () {
            $scope.isSaving = true;
            if ($scope.formula.id != null) {
                Formula.update($scope.formula, onSaveSuccess, onSaveError);
            } else {
                Formula.save($scope.formula, onSaveSuccess, onSaveError);
            }
        };

        $scope.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
}]);
