'use strict';

angular.module('omsApp').controller('FormulaeDialogController',
    ['$scope', '$stateParams', '$uibModalInstance', 'entity', 'Formulae', 'Formula',
        function($scope, $stateParams, $uibModalInstance, entity, Formulae, Formula) {

        $scope.formulae = entity;
        $scope.formulas = Formula.query();
        $scope.load = function(id) {
            Formulae.get({id : id}, function(result) {
                $scope.formulae = result;
            });
        };

        var onSaveSuccess = function (result) {
            $scope.$emit('omsApp:formulaeUpdate', result);
            $uibModalInstance.close(result);
            $scope.isSaving = false;
        };

        var onSaveError = function (result) {
            $scope.isSaving = false;
        };

        $scope.save = function () {
            $scope.isSaving = true;
            if ($scope.formulae.id != null) {
                Formulae.update($scope.formulae, onSaveSuccess, onSaveError);
            } else {
                Formulae.save($scope.formulae, onSaveSuccess, onSaveError);
            }
        };

        $scope.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
}]);
