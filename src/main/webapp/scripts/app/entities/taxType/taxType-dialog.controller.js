'use strict';

angular.module('omsApp').controller('TaxTypeDialogController',
    ['$scope', '$stateParams', '$uibModalInstance', 'entity', 'TaxType',
        function($scope, $stateParams, $uibModalInstance, entity, TaxType) {

        $scope.taxType = entity;
        $scope.load = function(id) {
            TaxType.get({id : id}, function(result) {
                $scope.taxType = result;
            });
        };

        var onSaveSuccess = function (result) {
            $scope.$emit('omsApp:taxTypeUpdate', result);
            $uibModalInstance.close(result);
            $scope.isSaving = false;
        };

        var onSaveError = function (result) {
            $scope.isSaving = false;
        };

        $scope.save = function () {
            $scope.isSaving = true;
            if ($scope.taxType.id != null) {
                TaxType.update($scope.taxType, onSaveSuccess, onSaveError);
            } else {
                TaxType.save($scope.taxType, onSaveSuccess, onSaveError);
            }
        };

        $scope.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
}]);
