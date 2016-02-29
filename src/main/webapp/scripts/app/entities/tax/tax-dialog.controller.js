'use strict';

angular.module('omsApp').controller('TaxDialogController',
    ['$scope', '$stateParams', '$uibModalInstance', 'entity', 'Tax', 'TaxType', 'PriceList',
        function($scope, $stateParams, $uibModalInstance, entity, Tax, TaxType, PriceList) {

        $scope.tax = entity;
        $scope.taxtypes = TaxType.query();
        $scope.pricelists = PriceList.query();
        $scope.load = function(id) {
            Tax.get({id : id}, function(result) {
                $scope.tax = result;
            });
        };

        var onSaveSuccess = function (result) {
            $scope.$emit('omsApp:taxUpdate', result);
            $uibModalInstance.close(result);
            $scope.isSaving = false;
        };

        var onSaveError = function (result) {
            $scope.isSaving = false;
        };

        $scope.save = function () {
            $scope.isSaving = true;
            if ($scope.tax.id != null) {
                Tax.update($scope.tax, onSaveSuccess, onSaveError);
            } else {
                Tax.save($scope.tax, onSaveSuccess, onSaveError);
            }
        };

        $scope.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
}]);
